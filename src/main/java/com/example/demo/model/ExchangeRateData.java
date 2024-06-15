package com.example.demo.model;

import java.util.List;

public class ExchangeRateData {
    private List<Double> rates;
    private List<Double> pips;

    public ExchangeRateData(List<Double> rates, List<Double> pips) {
        this.rates = rates;
        this.pips = pips;
    }

    public List<Double> getRates() {
        return rates;
    }

    public void setRates(List<Double> rates) {
        this.rates = rates;
    }

    public List<Double> getPips() {
        return pips;
    }

    public void setPips(List<Double> pips) {
        this.pips = pips;
    }

}
