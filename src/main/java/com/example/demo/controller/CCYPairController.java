package com.example.demo.controller;

import com.example.demo.fxrates.CurrencyPairGenerator;
import com.example.demo.handler.ResponseHandler;
import com.example.demo.model.CurrencyPairTypeRequest;
import com.example.demo.model.UserCredentials;
import com.example.demo.util.JWTUtil;

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

    @Autowired
    private JWTUtil jwtUtil;

    private CurrencyPairTypeRequest currencyPairTypeRequest;

     @PostMapping("/bytype")
    public ResponseEntity<Object> getCurrencyPairsByType(@RequestBody CurrencyPairTypeRequest currencyPairTypeRequest) {
        String token = currencyPairTypeRequest.getAccessToken();
        int accessNumber = currencyPairTypeRequest.getAccessNumber();

        if (token == null || accessNumber == 0) {
            return ResponseHandler.generateResponse("Access Token or Access Number cannot be empty", HttpStatus.BAD_REQUEST, null);
        }
        UserCredentials user = UserCredentials.getUserByAccessNumber(accessNumber);

        if (!jwtUtil.validateToken(token, user)) {
            return ResponseHandler.generateResponse("Invalid Token", HttpStatus.UNAUTHORIZED, null);
        }
        List<String> pairs = currencyPairGenerator.getPairsByType(currencyPairTypeRequest.getType());
        Map<String, Object> response = new HashMap<>();
        response.put("pairs", pairs);
        return ResponseHandler.generateResponse("Currency Pairs by Type", HttpStatus.OK, response);
    }
}
