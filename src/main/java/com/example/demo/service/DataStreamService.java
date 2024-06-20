package com.example.demo.service;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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

    private List<Map<String, Object>> currencyData = new CopyOnWriteArrayList<>();

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
        List<Map<String, Object>> data = spreadPipCalculator.changeSpread(currencyPairs);
        try {
            currencyData = data;
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

    public Map<String, Object> getCurrencyData(String pairType, String ccyPair) {
        for (Map<String, Object> currency : currencyData) {
            if (currency.get("pairType").equals(pairType) && currency.get("ccyPair").equals(ccyPair)) {
                return currency;
            }
        }
        return null; 
    }
}
