package com.example.demo.controller;

import com.example.demo.fxrates.FxRateGenerator;
import com.example.demo.handler.ResponseHandler;
import com.example.demo.model.ApiUser;
import com.example.demo.fxrates.SpreadPipCalculator;
import com.example.demo.repository.APIUserRepository;
import com.example.demo.util.APIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.time.Duration;
import org.springframework.http.MediaType;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/openapi")
public class BidAskController {

    @Autowired
    FxRateGenerator fxRateGenerator;

    @Autowired
    private SpreadPipCalculator spreadPipCalculator;

    @Autowired
    APIUserRepository userRepository;

    @Autowired
    private APIUtil apiUtil;

    // Shared Flux that all clients will subscribe to
    private Flux<ResponseEntity<Object>> sharedFlux;

    @PostConstruct
    public void init() {
        // Generate the shared flux in the constructor
        List<Map<String, Object>> currencyPairs = fxRateGenerator.generateCurrencyPairList();

        this.sharedFlux = Flux.interval(Duration.ofMillis(500))
                .onBackpressureDrop()
                .map(tick -> ResponseHandler.generateResponse(
                        "Currency pairs with bid and ask prices", 
                        HttpStatus.OK, 
                        spreadPipCalculator.changeSpread(currencyPairs)))
                .publish() // Convert the Flux to a ConnectableFlux (hot stream)
                .refCount(); // Automatically connect and disconnect subscribers
    }

    @GetMapping(value = "/currencypairs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<Object>> getBidAsk(@RequestHeader String API_KEY) {

        ApiUser user = userRepository.findByToken(API_KEY);
        if (user == null) {
            return Flux.error(new RuntimeException("Invalid API key"));
        }
        if(API_KEY.equals(user.getToken())) {
           apiUtil.validateToken(API_KEY, user);
        } else {
            return Flux.error(new RuntimeException("Invalid API key"));
        }
          
        return Flux.interval(Duration.ofSeconds(2))
            .map(tick -> {
                List<Map<String, Object>> currencyPairs = fxRateGenerator.generateCurrencyPairList();
                return filterDataBasedOnPlan(user.getPlanType(), currencyPairs);
            })
            .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
            .onErrorResume(error -> {
                System.err.println("Connection aborted by client: " + error.getMessage());
                return Flux.empty();
            });
    }

    private ResponseEntity<Object> filterDataBasedOnPlan(String plan, List<Map<String, Object>> currencyPairs) {
        List<Map<String, Object>> filteredData;
        if ("FREE_api_key".equals(plan)) {
            filteredData = currencyPairs.stream().limit(10).collect(Collectors.toList());
        } else if ("STANDARD_api_key".equals(plan)) {
            filteredData = currencyPairs.stream().limit(20).collect(Collectors.toList());
        } else {
            filteredData = currencyPairs;
        }
        return ResponseHandler.generateResponse("Currency pairs with bid and ask prices", HttpStatus.OK, filteredData);
    }
}
