package com.example.demo.controller;

import com.example.demo.fxrates.FxRateGenerator;
import com.example.demo.handler.ResponseHandler;
import com.example.demo.fxrates.SpreadPipCalculator;
import com.example.demo.model.UserCredentials;
import com.example.demo.util.JWTUtil;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import java.time.Duration;
import org.springframework.http.MediaType;

import javax.annotation.PostConstruct;


import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class BidAskController {

    @Autowired
    private FxRateGenerator fxRateGenerator;

    @Autowired
    private SpreadPipCalculator spreadPipCalculator;

    @Autowired
    private JWTUtil jwtUtil;

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
    public Flux<ResponseEntity<Object>> getBidAsk() {
        return sharedFlux
                .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
                .onErrorResume(error -> {
                    System.err.println("Connection aborted by client: " + error.getMessage());
                    return Flux.empty();
                });
    }

    @PostMapping("/userlogin")
    public ResponseEntity<Object> userLogin(@RequestBody UserCredentials userCredentials) {
        String accountNumber = userCredentials.getAccountNumber();
        String password = userCredentials.getPassword();

        if (accountNumber == null || password == null) {
            return ResponseEntity.badRequest().body("Account number and password are required");
        }

        UserCredentials user = UserCredentials.getUserByAccountNumber(accountNumber);

        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("Invalid user credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user credentials");
        } else {
            String token = jwtUtil.generateToken(user);
            List<Map<String, Object>> tokenAndAccountNumber = new ArrayList<>();
            Map<String, Object> entry = new HashMap<>();
            entry.put("token", token);
            entry.put("accountNumber", accountNumber);
            tokenAndAccountNumber.add(entry);
            System.out.println("User logged in successfully");
            return ResponseHandler.generateResponse("User logged in successfully", HttpStatus.OK, tokenAndAccountNumber);
        }
    }
    
}
