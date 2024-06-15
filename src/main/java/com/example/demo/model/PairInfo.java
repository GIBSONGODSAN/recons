package com.example.demo.model;

import lombok.Data;

@Data
public class PairInfo {
    private String type;
    private String pair;
    private double bid;
    private double ask;

    public PairInfo(String pair, String type, double bid, double ask) {
        this.pair = pair;
        this.type = type;
        this.bid = bid;
        this.ask = ask;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }
}
