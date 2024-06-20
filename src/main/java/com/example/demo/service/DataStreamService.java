package com.example.demo.service;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.demo.fxrates.FxRateGenerator;
import com.example.demo.fxrates.SpreadPipCalculator;
import com.example.demo.handler.CustomWebSocketHandler;

@Service
public class DataStreamService {

    @Autowired
    FxRateGenerator fxRateGenerator;
    @Autowired
    SpreadPipCalculator spreadPipCalculator;
    private final List<Map<String, Object>> currencyPairs;
    @Autowired
    CustomWebSocketHandler webSocketHandler;
    private final ObjectMapper objectMapper;

    public DataStreamService(@Autowired FxRateGenerator fxRateGenerator,
                             @Autowired SpreadPipCalculator spreadPipCalculator,
                             @Autowired CustomWebSocketHandler webSocketHandler,
                             ObjectMapper objectMapper) {
        this.fxRateGenerator = fxRateGenerator;
        this.spreadPipCalculator = spreadPipCalculator;
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = objectMapper;
        this.currencyPairs = fxRateGenerator.generateCurrencyPairList();
    }

    @Scheduled(fixedRate = 5)
    public void streamData() {
        Object data = spreadPipCalculator.changeSpread(currencyPairs);
        try {
            String message = objectMapper.writeValueAsString(data);
            broadcastMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(String message) {
        for (WebSocketSession session : webSocketHandler.getSessions()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
