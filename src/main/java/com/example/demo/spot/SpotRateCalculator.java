package com.example.demo.spot;

public class SpotRateCalculator {

    private final double bid;
    private final double ask;
    
    public SpotRateCalculator(double bid, double ask, int lots) {
        this.bid = bid;
        this.ask = ask;
    }

    public double calculateBuySpotValue(double rate) {
        return bid + rate;
    }

    public double calculateSellSpotValue(double rate) {
        return ask - rate;
    }

    public double calculateBuySpotLotValue(double rate, int lots) {
        return (bid + rate) * lots;
    }

    public double calculateSellSpotLotValue(double rate, int lots) {
        return (ask - rate) * lots;
    }
}

