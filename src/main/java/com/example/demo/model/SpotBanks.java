package com.example.demo.model;

public class SpotBanks {

    private String pairType;
    private String ccyPair;
    private int lots;

    public SpotBanks(String pairType, String ccyPair, int lots) {
        this.pairType = pairType;
        this.ccyPair = ccyPair;
        this.lots = lots;
    }

    public String getPairType() {
        return pairType;
    }

    public void setPairType(String pairType) {
        this.pairType = pairType;
    }

    public String getCcyPair() {
        return ccyPair;
    }

    public void setCcyPair(String ccyPair) {
        this.ccyPair = ccyPair;
    }

    public int getLots() {
        return lots;
    }

    public void setLots(int lots) {
        this.lots = lots;
    }

}
