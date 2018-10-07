package com.solstice.quoteservice.model;

public class AggregatedData {

    private double highestPrice;
    private double lowestPrice;
    private long totalTrade;
    private double closingPrice;

    public AggregatedData() {
    }

    public AggregatedData(double highestPrice, double lowestPrice, long totalTrade, double closingPrice) {
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.totalTrade = totalTrade;
        this.closingPrice = closingPrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public long getTotalTrade() {
        return totalTrade;
    }

    public void setTotalTrade(long totalTrade) {
        this.totalTrade = totalTrade;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(double closingPrice) {
        this.closingPrice = closingPrice;
    }
}
