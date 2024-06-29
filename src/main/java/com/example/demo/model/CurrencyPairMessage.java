package com.example.demo.model;

import lombok.Data;

@Data
public class CurrencyPairMessage {
    private String pairType;
    private String ccyPair;

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
}
