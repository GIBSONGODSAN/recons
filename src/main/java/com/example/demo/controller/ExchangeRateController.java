package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import com.example.demo.model.ExchangeRateData;

import java.time.Duration;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/fxrates")
public class ExchangeRateController {

    private static final Random RANDOM = new Random();

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<Map<String, ExchangeRateData>>> streamData(@RequestParam String base, @RequestParam String quote) {
        String exchangeRateKey = base + quote;
        int integerPart = RANDOM.nextInt(10) + 1;  
        double fixedPart = RANDOM.nextDouble() * 10000;
        return Flux.interval(Duration.ofSeconds(2))
                   .map(tick -> generateExchangeRate(exchangeRateKey, integerPart, fixedPart))
                   .map(rate -> ResponseEntity.ok(Map.of(exchangeRateKey, rate)));
    }

    private static ExchangeRateData generateExchangeRate(String exchangeRateKey, int integerPart, double fixedPart) {
        List<Double> rates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            double varyingPart = RANDOM.nextDouble() * 10000; // Varying part with last 4 decimal places
            double rate = integerPart + fixedPart / 10000.0 + varyingPart / 10000000.0;
            double roundedRate = Math.round(rate * 10000000.0) / 10000000.0; // Round to 7 decimal places
            rates.add(roundedRate);
        }
        List<Double> pips = calculatePips(rates);
        return new ExchangeRateData(rates, pips);
    }

    private static List<Double> calculatePips(List<Double> rates) {
        List<Double> pips = new ArrayList<>();
        for (int i = 0; i < rates.size() - 1; i++) {
            double pipsValue = (rates.get(i + 1) - rates.get(i)) * 100; // Pips for JPY pairs
            pips.add(pipsValue);
        }
        return pips;
    }
}
