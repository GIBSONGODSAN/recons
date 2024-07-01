package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.UserCredentials;

import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

import java.util.Date;

@Component
public class JWTUtil {

    private String SECRET="narselmary";

    private static final int JWT_TOKEN_VALIDITY = 5 * 60 * 60; 
    private static final String CLAIM_KEY_USER_ID = "accessNumber";  

    public JWTUtil(Environment env) {
        // Validate key existence using Spring's Environment (optional)
        if (env.getProperty("jwt.secret") == null) {
            throw new IllegalArgumentException("JWT secret not found in configuration!");
        }
    }
    
    public String generateToken(UserCredentials user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        try {
            Date issuedAt = new Date();
            Date expiresAt = new Date(issuedAt.getTime() + JWT_TOKEN_VALIDITY * 1000);

            return JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim(CLAIM_KEY_USER_ID, user.getAccessNumber()) 
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

    public boolean validateToken(String token, UserCredentials userDetails) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token);
            return (getUsernameFromToken(token).equals(userDetails.getUsername()));
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}