package com.solstice.quoteservice.controller;

import com.solstice.quoteservice.model.AggregatedData;
import com.solstice.quoteservice.model.Quote;
import com.solstice.quoteservice.service.QuoteService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

//    @GetMapping("/test/{symbol}")
//    public Integer test(@PathVariable("symbol") String symbol) throws JSONException {
//
//        JSONObject jsonObject = new JSONObject(restTemplate.getForEntity("http://symbol-service/"+symbol,String.class).getBody());
//        int id = jsonObject.getInt("id");
//
//        return id;
//    }

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public Iterable<Quote> saveAllToDatabase() {

        return quoteService.insertDataToDatabase();
    }

    @RequestMapping(value = "/{symbol}/{date}", method = RequestMethod.GET)
    public AggregatedData getAggregatedDataByDay(@PathVariable("symbol") String symbol, @PathVariable("date") String date) {


        return quoteService.findByNameAndDate(symbol, date);
    }

    @RequestMapping(value = "/{symbol}/{month}/{year}")
    public AggregatedData getAggregatedDataByMonth(@PathVariable("symbol") String symbol, @PathVariable("month") int month,@PathVariable("year") int year) {

        return quoteService.findByNameAndMonth(symbol, month,year);
    }


}
