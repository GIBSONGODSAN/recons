package com.example.demo.controller;

import com.example.demo.fxrates.CurrencyPairGenerator;
import com.example.demo.handler.ResponseHandler;
import com.example.demo.model.CurrencyPairTypeRequest;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/get-ccypairs")
public class CCYPairController {

    @Autowired
    private CurrencyPairGenerator currencyPairGenerator;

    private CurrencyPairTypeRequest currencyPairTypeRequest;

     @PostMapping("/bytype")
    public ResponseEntity<Object> getCurrencyPairsByType(@RequestBody CurrencyPairTypeRequest currencyPairTypeRequest) {
        List<String> pairs = currencyPairGenerator.getPairsByType(currencyPairTypeRequest.getType());
        Map<String, Object> response = new HashMap<>();
        response.put("pairs", pairs);
        return ResponseHandler.generateResponse("Currency Pairs by Type", HttpStatus.OK, response);
    }
}
