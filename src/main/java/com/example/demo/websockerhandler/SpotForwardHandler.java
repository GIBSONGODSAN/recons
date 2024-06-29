package com.example.demo.websockerhandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;

import com.example.demo.model.SpotForwardMessage;
import com.example.demo.model.UserSessionForward;
import com.example.demo.service.DataStreamService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SpotForwardHandler extends TextWebSocketHandler {

    @Autowired
    private ObjectMapper objectMapper;
    
    private DataStreamService dataStreamService;
    @Autowired
    public void setDataStreamService(DataStreamService dataStreamService) {
        this.dataStreamService = dataStreamService;
    }
    private final List<UserSessionForward> userSessionsForward = new ArrayList<>();
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        userSessionsForward.add(new UserSessionForward(session, null, null, 0, 0));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        userSessionsForward.removeIf(userSession -> userSession.getSession().equals(session));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            SpotForwardMessage spotForwardMessage = objectMapper.readValue(message.getPayload(), SpotForwardMessage.class);
            for (UserSessionForward userSession : userSessionsForward) {
                if (userSession.getSession().equals(session)) {
                    userSession.setPairType(spotForwardMessage.getPairType());
                    userSession.setCcyPair(spotForwardMessage.getCcyPair());
                    userSession.setNoOfDays(spotForwardMessage.getNoOfDays());
                    userSession.setAmount(spotForwardMessage.getAmount());
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

    public List<UserSessionForward> getUserSessions() {
        return userSessionsForward;
    }
}


