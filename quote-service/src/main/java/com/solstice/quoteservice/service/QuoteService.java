package com.solstice.quoteservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.solstice.quoteservice.model.AggregatedData;
import com.solstice.quoteservice.model.Quote;
import com.solstice.quoteservice.repository.QuoteRepository;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

@Service
public class QuoteService {

    private QuoteRepository quoteRepository;


    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Inject
    private RestTemplate restTemplate;

    @Value("${stock.data.url}")
    private String url;


    public Quote[] extractQuotesFromJsonFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper().setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        URL urlParsed = new URL(url);
        return mapper.readValue(urlParsed, Quote[].class);
    }

    public Iterable<Quote> save(Quote[] quotesArray) {
        return quoteRepository.saveAll(Arrays.asList(quotesArray));
    }

    public Iterable<Quote> saveOneByOne(Quote[] quotes) {

        for (int i = 0; i < 50; i++) {

            quoteRepository.save(quotes[i]);

        }
        return Arrays.asList(quotes);
    }

    public Iterable<Quote> insertDataToDatabase() {

        ObjectMapper mapper = new ObjectMapper().setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        URL urlParsed = null;
        try {
            urlParsed = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Quote[] quotes = new Quote[0];
        try {
            quotes = mapper.readValue(urlParsed, Quote[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            quotes = this.extractQuotesFromJsonFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return this.saveOneByOne(quotes);
    }

    public AggregatedData findByNameAndDate(String symbol, String date) {


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(restTemplate.getForEntity("http://symbol-service/" + symbol, String.class).getBody());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int value = 0;
        try {
            value = jsonObject.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(parsedDate.getTime());

        return quoteRepository.getDataBySymbolAndDay(value, timestamp);
    }

    public AggregatedData findByNameAndMonth(String symbol, int month, int year) {

        JSONObject jsonObject = null;
        try {

            jsonObject = new JSONObject(restTemplate.getForEntity("http://symbol-service/" + symbol, String.class).getBody());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int value = 0;
        try {
            value = jsonObject.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String date = year + "-" + month + "-" + 01;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(parsedDate.getTime());

        return quoteRepository.getDataBySymbolAndMonth(value, timestamp);
    }
}
