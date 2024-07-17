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
import org.springframework.web.bind.annotation.RequestHeader;
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
    public ResponseEntity<Object> getCurrencyPairsByType(@RequestHeader("Authorization") String authorizationHeader ,@RequestBody CurrencyPairTypeRequest currencyPairTypeRequest) {/

        if(currencyPairTypeRequest == null) {
            return ResponseHandler.generateResponse("Currency Pair Type Request cannot be empty", HttpStatus.BAD_REQUEST, null);
        } if (currencyPairTypeRequest.getType() == null || currencyPairTypeRequest.getType().isEmpty()){
            return ResponseHandler.generateResponse("Currency Pair Type cannot be empty", HttpStatus.BAD_REQUEST, null);
        } if (currencyPairTypeRequest.getUsername() == null || currencyPairTypeRequest.getUsername().isEmpty()) {
            return ResponseHandler.generateResponse("Username cannot be empty", HttpStatus.BAD_REQUEST, null);
        }

        String token = authorizationHeader.substring(7);
        String username = currencyPairTypeRequest.getUsername();

        if (token == null || username == null) {
            return ResponseHandler.generateResponse("Access Token or User Name cannot be empty", HttpStatus.BAD_REQUEST, null);
        }
        UserCredentials user = UserCredentials.getUserByName(username);

        if (!jwtUtil.validateToken(token, user)) {
            return ResponseHandler.generateResponse("Invalid Token", HttpStatus.UNAUTHORIZED, null);
        }
        List<String> pairs = currencyPairGenerator.getPairsByType(currencyPairTypeRequest.getType());
        Map<String, Object> response = new HashMap<>();
        response.put("pairs", pairs);
        return ResponseHandler.generateResponse("Currency Pairs by Type", HttpStatus.OK, response);
    }
}
