package com.example.demo.websockerhandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.demo.model.SpotBanks;
import com.example.demo.model.UserSession;
import com.example.demo.model.UserSessionForward;
import com.example.demo.model.UserSessionLots;
import com.example.demo.service.DataStreamService;

@Component
public class BSLotsHandler extends TextWebSocketHandler {

    @Autowired
    private ObjectMapper objectMapper;
    
    private DataStreamService dataStreamService;

    @Autowired
    public void setDataStreamService(DataStreamService dataStreamService) {
        this.dataStreamService = dataStreamService;
    }
    
    private final List<UserSessionLots> userSessionsLots = new ArrayList<>();
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        userSessionsLots.add(new UserSessionLots(session, null, null, 0));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        userSessionsLots.removeIf(userSession -> userSession.getSession().equals(session));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            SpotBanks spotBanks = objectMapper.readValue(message.getPayload(), SpotBanks.class);
            for (UserSessionLots userSession : userSessionsLots) {
                if (userSession.getSession().equals(session)) {
                    userSession.setPairType(spotBanks.getPairType());
                    userSession.setCcyPair(spotBanks.getCcyPair());
                    userSession.setLots(spotBanks.getLots());
                    break;
                }
            }
           
            // Object spotValues = dataStreamService.processSpotPriceRequest(spotBanks);
            // String response = objectMapper.writeValueAsString(spotValues);
            // session.sendMessage(new TextMessage(response));
        } catch (IOException e) {
            e.printStackTrace();
            session.sendMessage(new TextMessage("Error processing request"));
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

    public List<UserSessionLots> getUserSessions() {
        return userSessionsLots;
    }
}
