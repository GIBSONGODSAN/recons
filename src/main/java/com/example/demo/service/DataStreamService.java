package com.example.demo.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.demo.fxrates.FxRateGenerator;
import com.example.demo.fxrates.SpreadPipCalculator;
import com.example.demo.model.SpotBanks;
import com.example.demo.model.UserSession;
import com.example.demo.spot.SpotRateAnalysis;
import com.example.demo.spot.SpotRateService;
import com.example.demo.spot.SpotRateService.SpotValues;
import com.example.demo.websockerhandler.BSLotsHandler;
import com.example.demo.websockerhandler.CCYPairBidAskHandler;
import com.example.demo.websockerhandler.CCYPairHandler;
import com.example.demo.handler.ResponseHandler;

@Service
public class DataStreamService {

    private static final Logger log = LoggerFactory.getLogger(DataStreamService.class);

    @Autowired
    FxRateGenerator fxRateGenerator;
    @Autowired
    SpotRateService spotRateService;
    @Autowired
    SpreadPipCalculator spreadPipCalculator;
    private final List<Map<String, Object>> currencyPairs;
    
    @Autowired
    CCYPairBidAskHandler webSocketHandler;
    
    private BSLotsHandler customWSBankSpot;
    @Autowired  
    public void setCustomWSBankSpot(BSLotsHandler customWSBankSpot) {
        this.customWSBankSpot = customWSBankSpot;
    }

    private CCYPairHandler ccyPairHandler;
    @Autowired
    public void setCCYPairHandler(CCYPairHandler ccyPairHandler) {
        this.ccyPairHandler = ccyPairHandler;
    }

    private final ObjectMapper objectMapper;

    private List<Map<String, Object>> currencyData = new CopyOnWriteArrayList<>();

    public DataStreamService(@Autowired FxRateGenerator fxRateGenerator,
                             @Autowired SpreadPipCalculator spreadPipCalculator,
                             @Autowired CCYPairBidAskHandler webSocketHandler,
                             ObjectMapper objectMapper) {
        this.fxRateGenerator = fxRateGenerator;
        this.spreadPipCalculator = spreadPipCalculator;
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = objectMapper;
        this.currencyPairs = fxRateGenerator.generateCurrencyPairList();
    }

    @Scheduled(fixedRate = 1)
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
        sendFilteredMessages();
    }

    private void sendFilteredMessages() {
        try {
            List<UserSession> userSessions = ccyPairHandler.getUserSessions();
            for (UserSession userSession : userSessions) {
                String pairType = userSession.getPairType();
                String ccyPair = userSession.getCcyPair();
                WebSocketSession session = userSession.getSession();
    
                if (pairType != null && ccyPair != null) {
                    Map<String, Object> filteredData = getCurrencyData(pairType, ccyPair);
                    if (filteredData != null) {
                        try {
                            String filteredMessage = objectMapper.writeValueAsString(filteredData);
                            session.sendMessage(new TextMessage(filteredMessage));
                        } catch (IOException e) {
                            // Handle specific session send failure
                            log.error("Failed to send filtered message to session: " + session.getId(), e);
                        }
                    }
                } else {
                    log.warn("Session " + session.getId() + " has no filter criteria set.");
                }
            }
        } catch (Exception e) {
            // Handle general processing failure
            log.error("Error processing filtered messages", e);
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

    public Object processSpotPriceRequest(SpotBanks spotBanks) {
        String pairType = spotBanks.getPairType();
        String ccyPair = spotBanks.getCcyPair();
        int lots = spotBanks.getLots();
        Map<String, Object> currencyData = getCurrencyData(pairType, ccyPair);

        if (currencyData != null) {
            double bid = (double) currencyData.get("bid");
            double ask = (double) currencyData.get("ask");
            Object spotValues = calculateSpotValues(bid, ask, lots);
            broadcastSpotValues(spotValues); // Broadcast the spot values
            return ResponseHandler.generateResponse("Spot values calculated", HttpStatus.OK, spotValues);
        } else {
            return ResponseEntity.status(404).body("Currency data not found");
        }
    }

    public Object calculateSpotValues(double bid, double ask, int lots) {
        Map<String, SpotValues> spotValues = spotRateService.getSpotValues(bid, ask, lots);
        Map<String, Map<String, Object>> displayValues = SpotRateAnalysis.findBestBanks(spotValues);
        Map<String, Object> combinedValues = new HashMap<>();
        combinedValues.put("SpotValues", spotValues);
        combinedValues.put("DisplayValues", displayValues);
        return combinedValues;
    }

    private void broadcastSpotValues(Object spotValues) {
        try {
            String message = objectMapper.writeValueAsString(spotValues);
            broadcastSpotBank(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastSpotBank(String message) {
        for (WebSocketSession session : customWSBankSpot.getSessions()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
