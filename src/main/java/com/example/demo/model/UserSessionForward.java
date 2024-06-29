package com.example.demo.model;

import org.springframework.web.socket.WebSocketSession;

public class UserSessionForward {

    private WebSocketSession session;
    private String pairType;
    private String ccyPair;
    private int noOfDays;
    private int amount;

    
    public UserSessionForward(WebSocketSession session, String pairType, String ccyPair, int noOfDays, int amount) {
        this.session = session;
        this.pairType = pairType;
        this.ccyPair = ccyPair;
        this.noOfDays = noOfDays;
        this.amount = amount;
    }

    public WebSocketSession getSession() {
        return session;
    }
    public void setSession(WebSocketSession session) {
        this.session = session;
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
    public int getNoOfDays() {
        return noOfDays;
    }
    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    

}
