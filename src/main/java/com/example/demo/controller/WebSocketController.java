package com.example.demo.controller;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.demo.websockerhandler.SpotForwardHandler;
import com.example.demo.websockerhandler.BSLotsHandler;
import com.example.demo.websockerhandler.CCYPairBidAskHandler;
import com.example.demo.websockerhandler.CCYPairHandler;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSocket
public class WebSocketController implements WebSocketConfigurer {

    @Autowired
    CCYPairBidAskHandler CCYPairBidAskGenerator;

    @Autowired
    BSLotsHandler BSLotsGenerator;

    @Autowired
    CCYPairHandler CCYPairGenerator;

    @Autowired
    SpotForwardHandler spotForwardGenerator;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(CCYPairBidAskGenerator, "/get/fxrates");
        registry.addHandler(CCYPairGenerator, "/get/ccypair");
        registry.addHandler(BSLotsGenerator, "/spot/lots");
        registry.addHandler(spotForwardGenerator, "/forward/lots");
    }

}
