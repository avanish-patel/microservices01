package com.solstice.quoteservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "Quote.getDataBySymbolAndDay",
                query = "select *" +
                        " from (" +
                        " select max(price), min(price),sum(volume) " +
                        "        from quote " +
                        "        where symbol=:symbol and day(date)=day(:date)) as x," +
                        " ( " +
                        "        select price " +
                        "        from quote " +
                        "        where symbol=:symbol and day(date)=day(:date) ORDER BY date DESC LIMIT 1) as y", resultClass = AggregatedData.class, resultSetMapping = "aggregatedData"),

        @NamedNativeQuery(name = "Quote.getDataBySymbolAndMonth",
                query = "select *" +
                        " from (" +
                        " select max(price), min(price),sum(volume) " +
                        "        from quote " +
                        "        where symbol=:symbol and month(date)=month(:month)) as x," +
                        " ( " +
                        "        select price " +
                        "        from quote " +
                        "        where symbol=:symbol and month(date)=month(:month) ORDER BY date DESC LIMIT 1) as y", resultClass = AggregatedData.class, resultSetMapping = "aggregatedData")
})

@SqlResultSetMapping(
        name = "aggregatedData",
        classes = @ConstructorResult(targetClass = AggregatedData.class,
                columns = {
                        @ColumnResult(name = "max(price)", type = Double.class),
                        @ColumnResult(name = "min(price)", type = Double.class),
                        @ColumnResult(name = "sum(volume)", type = Long.class),
                        @ColumnResult(name = "price", type = Double.class)
                })
)
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int symbol;
    private Double price;
    private int volume;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date date;

    public Quote() {
    }

    public Quote(int symbol, Double price, int volume, Date date) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.date = date;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
