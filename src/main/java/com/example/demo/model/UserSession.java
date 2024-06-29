package com.example.demo.model;

import org.springframework.web.socket.WebSocketSession;

public class UserSession {
    private WebSocketSession session;
    private String pairType;
    private String ccyPair;

    public UserSession(WebSocketSession session, String pairType, String ccyPair) {
        this.session = session;
        this.pairType = pairType;
        this.ccyPair = ccyPair;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public String getPairType() {
        return pairType;
    }

    public String getCcyPair() {
        return ccyPair;
    }

    public void setPairType(String pairType) {
        this.pairType = pairType;
    }

    public void setCcyPair(String ccyPair) {
        this.ccyPair = ccyPair;
    }
    
}
