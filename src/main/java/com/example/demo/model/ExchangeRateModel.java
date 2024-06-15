package com.example.demo.model;

public class ExchangeRateModel {

    private String base;
    private String quote;

    public ExchangeRateModel(String base, String quote) {
        this.base = base;
        this.quote = quote;
    }

    public String getBase() {
        return base;
    }

    public String getQuote() {
        return quote;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
