package com.example.demo.controller;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.demo.handler.CustomWebSocketHandler;
import com.example.demo.handler.CustomWSBankSpot;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSocket
public class WebSocketController implements WebSocketConfigurer {

    @Autowired
    CustomWebSocketHandler customWebSocketHandler;

    @Autowired
    CustomWSBankSpot customWSBankSpot;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customWebSocketHandler, "/ws");
        registry.addHandler(customWSBankSpot, "/wsbankspot");
    }

}
