package com.example.demo.websockerhandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.demo.model.CurrencyPairMessage;
import com.example.demo.model.UserSession;
import com.example.demo.service.DataStreamService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CCYPairHandler extends TextWebSocketHandler {

    @Autowired
    private ObjectMapper objectMapper;
    
    private DataStreamService dataStreamService;
    @Autowired
    public void setDataStreamService(DataStreamService dataStreamService) {
        this.dataStreamService = dataStreamService;
    }
    private final List<UserSession> userSessions = new ArrayList<>();
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        userSessions.add(new UserSession(session, null, null));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        userSessions.removeIf(userSession -> userSession.getSession().equals(session));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            CurrencyPairMessage currencyPairMessage = objectMapper.readValue(message.getPayload(), CurrencyPairMessage.class);
            for (UserSession userSession : userSessions) {
                if (userSession.getSession().equals(session)) {
                    userSession.setPairType(currencyPairMessage.getPairType());
                    userSession.setCcyPair(currencyPairMessage.getCcyPair());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.sendMessage(new TextMessage("Error: Invalid message format"));
        }
        sessions.forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Set<WebSocketSession> getSessions() {
        return Collections.unmodifiableSet(sessions);
    }

    public List<UserSession> getUserSessions() {
        return userSessions;
    }
}

