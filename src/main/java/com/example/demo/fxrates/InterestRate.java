package com.example.demo.fxrates;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InterestRate {

    private static final Random RANDOM = new Random();

    private Map<String, Double> generateRandomInterestRates(String pair) {
        String[] currencies = pair.split("/");

        Map<String, Double> interestRates = new HashMap<>();
        interestRates.put(currencies[0], round(1 + RANDOM.nextDouble() * 4, 2));
        interestRates.put(currencies[1], round(1 + RANDOM.nextDouble() * 4, 2));
        return interestRates;
    }

    public Map<String, Double> getInterestRates(String pair) {
        return generateRandomInterestRates(pair);
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

