package com.example.demo.controller;

import com.example.demo.fxrates.FxRateGenerator;
import com.example.demo.handler.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import java.time.Duration;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BidAskController {

    @Autowired
    FxRateGenerator fxRateGenerator;

    @GetMapping(value = "/currencypairs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<Object>> getBidAsk() {
        return Flux.interval(Duration.ofSeconds(2))
        .map(tick -> {
            List<Map<String, Object>> currencyPairs = fxRateGenerator.generateCurrencyPairList();
            return ResponseHandler.generateResponse("Currency pairs with bid and ask prices", HttpStatus.OK, currencyPairs);
        });
    }
}
