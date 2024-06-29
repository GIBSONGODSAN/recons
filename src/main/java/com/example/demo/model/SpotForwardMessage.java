package com.example.demo.model;

public class SpotForwardMessage {

    private String pairType;
    private String ccyPair;
    private int noOfDays;
    private int amount;

    public void setPairType(String pairType) {
        this.pairType = pairType;
    }

    public void setCcyPair(String ccyPair) {
        this.ccyPair = ccyPair;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public SpotForwardMessage(String pairType, String ccyPair, int noOfDays, int amount) {
        this.pairType = pairType;
        this.ccyPair = ccyPair;
        this.noOfDays = noOfDays;
        this.amount = amount;
    }

    public String getPairType() {
        return pairType;
    }   

    public String getCcyPair() {
        return ccyPair;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public int getAmount() {
        return amount;
    }
}
