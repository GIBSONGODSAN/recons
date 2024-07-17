package com.example.demo.controller;

import com.example.demo.fxrates.CurrencyPairGenerator;
import com.example.demo.handler.ResponseHandler;
import com.example.demo.model.CurrencyPairTypeRequest;
import com.example.demo.model.UserCredentials;
import com.example.demo.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/get-ccypairs")
public class CCYPairController {

    @Autowired
    private CurrencyPairGenerator currencyPairGenerator;

    @Autowired
    private JWTUtil jwtUtil;

    private CurrencyPairTypeRequest currencyPairTypeRequest;

    @Operation(summary = "Get Currency Pairs by Type", description = "Fetches currency pairs based on the provided type and validates the user token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Currency Pairs by Type retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Bad Request: Currency Pair Type Request, Currency Pair Type, or Username cannot be empty"),
        @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid Token"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/bytype")
    public ResponseEntity<Object> getCurrencyPairsByType(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CurrencyPairTypeRequest currencyPairTypeRequest) {

        if (currencyPairTypeRequest == null) {
            return ResponseHandler.generateResponse("Currency Pair Type Request cannot be empty", HttpStatus.BAD_REQUEST, null);
        }
        if (currencyPairTypeRequest.getType() == null || currencyPairTypeRequest.getType().isEmpty()) {
            return ResponseHandler.generateResponse("Currency Pair Type cannot be empty", HttpStatus.BAD_REQUEST, null);
        }
        if (currencyPairTypeRequest.getUsername() == null || currencyPairTypeRequest.getUsername().isEmpty()) {
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
