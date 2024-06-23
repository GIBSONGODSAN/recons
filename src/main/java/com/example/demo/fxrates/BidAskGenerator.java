package com.example.demo.fxrates;

import java.util.Random;

public class BidAskGenerator {

    private static final Random RANDOM = new Random();

    private static double generateExchangeRate(int integerPart, double fixedPart) {
       double varyingPart = RANDOM.nextDouble() * 10000; 
       if (varyingPart % 2 == 0) {
           varyingPart = -varyingPart;
         } 
        double rate = integerPart + fixedPart / 10000.0 + varyingPart / 10000.0;
        double roundedRate = Math.round(rate * 10000.0) / 10000.0; // Round to 7 decimal places
        return roundedRate;
    }

    public double[] generateRandomBidAskPrice(int integerPart, double fixedPart) {
        double bidPrice = generateExchangeRate(integerPart, fixedPart);
        double askPrice = generateExchangeRate(integerPart, fixedPart);
        return new double[] { bidPrice, askPrice };
    }
}
