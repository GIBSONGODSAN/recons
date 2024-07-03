package com.example.demo.model;

public class CurrencyPairTypeRequest {

    private String type;
    // private String accessToken;
    private int accessNumber;

    public CurrencyPairTypeRequest(String type, int accessNumber) {
        this.type = type;
        // this.accessToken = accessToken;
        this.accessNumber = accessNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // public String getAccessToken() {
    //     return accessToken;
    // }

    // public void setAccessToken(String accessToken) {
    //     this.accessToken = accessToken;
    // }

    public int getAccessNumber() {
        return accessNumber;
    }

    public void setAccessNumber(int accessNumber) {
        this.accessNumber = accessNumber;
    }

    
}
