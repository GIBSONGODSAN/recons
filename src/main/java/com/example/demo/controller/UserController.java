package com.example.demo.controller;

import com.example.demo.handler.ResponseHandler;
import com.example.demo.model.ApiUser;
// import com.example.demo.util.APIUtil;
import com.example.demo.util.JWTUtil;
import com.example.demo.repository.APIUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    APIUserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody ApiUser user) {
        try {
            // Validate user input
            if (user == null) {
                return ResponseHandler.generateResponse("User object is required", HttpStatus.BAD_REQUEST, null);
            }
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseHandler.generateResponse("Email is required", HttpStatus.BAD_REQUEST, null);
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseHandler.generateResponse("Password is required", HttpStatus.BAD_REQUEST, null);
            }
            if (user.getPlanType() == null || user.getPlanType().isEmpty()) {
                return ResponseHandler.generateResponse("Plan type is required", HttpStatus.BAD_REQUEST, null);
            }

            // Save user to database
            userRepository.save(user);
            // Return user object with token
            return ResponseHandler.generateResponse("User signed up successfully", HttpStatus.OK, user);
        } catch (Exception e) {
            // Handle any exceptions that occur during signup process
            return ResponseHandler.generateResponse("Error occurred during signup", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody ApiUser user) {
        try {
            // Validate user input
            if (user == null) {
                return ResponseHandler.generateResponse("User object is required", HttpStatus.BAD_REQUEST, null);
            }
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseHandler.generateResponse("Email is required", HttpStatus.BAD_REQUEST, null);
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseHandler.generateResponse("Password is required", HttpStatus.BAD_REQUEST, null);
            }

            // Find user in database
            ApiUser existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser == null) {
                return ResponseHandler.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
            }

            // Validate user password
            if (!existingUser.getPassword().equals(user.getPassword())) {
                return ResponseHandler.generateResponse("Invalid password", HttpStatus.UNAUTHORIZED, null);
            }

            String API_KEY = userRepository.findByEmail(user.getEmail()).getToken();

            // Return user object with token
            return ResponseHandler.generateResponse("User logged in successfully", HttpStatus.OK, API_KEY);
        } catch (Exception e) {
            // Handle any exceptions that occur during login process
            return ResponseHandler.generateResponse("Error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
       
}