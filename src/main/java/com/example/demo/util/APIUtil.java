package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

import com.example.demo.model.ApiUser;

import java.util.Date;

@Component
public class APIUtil {

    private String SECRET="narselmary";

    private static final long JWT_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 30; // 30 days
    private static final String CLAIM_KEY_USER_ID = "planType";
    private static final String FREE_api_key = "FREEplanner"; 
    private static final String STANDARD_api_key = "STANDARDplanner"; 
    private static final String PREMIUM_api_key = "PROplanner";

    public APIUtil(Environment env) {
        // Validate key existence using Spring's Environment (optional)
        if (env.getProperty("jwt.secret") == null) {
            throw new IllegalArgumentException("JWT secret not found in configuration!");
        }
    }
    
    public String generateToken(ApiUser user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        try {
            Date issuedAt = new Date();
            Date expiresAt = new Date(issuedAt.getTime() + JWT_TOKEN_VALIDITY);

            return JWT.create()
                    .withSubject(user.getEmail())
                    .withClaim(CLAIM_KEY_USER_ID, user.getPlanType()) 
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            // Handle token creation errors gracefully (e.g., logging)
            throw new RuntimeException("Failed to create JWT token", exception);
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            // Handle token verification errors gracefully (e.g., logging)
            throw new RuntimeException("Failed to extract username from JWT token", e);
        }
    }

    public boolean validateToken(String token, ApiUser userDetails) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token);
            return (getUsernameFromToken(token).equals(userDetails.getEmail()));
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}