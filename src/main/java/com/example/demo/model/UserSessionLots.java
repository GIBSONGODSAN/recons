package com.example.demo.model;

import org.springframework.web.socket.WebSocketSession;

public class UserSessionLots {

    private WebSocketSession session;
    private String pairType;
    private String ccyPair;
   private int lots;
    
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
    public int getLots() {
        return lots;
    }
    public void setLots(int lots) {
        this.lots = lots;
    }
    public UserSessionLots(WebSocketSession session, String pairType, String ccyPair, int lots) {
        this.session = session;
        this.pairType = pairType;
        this.ccyPair = ccyPair;
        this.lots = lots;
    }   

}
