package com.example.demo.handler;

import java.io.IOException;
import java.util.Collections;
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
import com.example.demo.service.DataStreamService;

@Component
public class CustomWSBankSpot extends TextWebSocketHandler {

    @Autowired
    private ObjectMapper objectMapper;
    
    private DataStreamService dataStreamService;

    @Autowired
    public void setDataStreamService(DataStreamService dataStreamService) {
        this.dataStreamService = dataStreamService;
    }

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            SpotBanks spotBanks = objectMapper.readValue(message.getPayload(), SpotBanks.class);
            Object spotValues = dataStreamService.processSpotPriceRequest(spotBanks);
            String response = objectMapper.writeValueAsString(spotValues);
            session.sendMessage(new TextMessage(response));
        } catch (IOException e) {
            e.printStackTrace();
            session.sendMessage(new TextMessage("Error processing request"));
        }
    }

    public Set<WebSocketSession> getSessions() {
        return Collections.unmodifiableSet(sessions);
    }
}
