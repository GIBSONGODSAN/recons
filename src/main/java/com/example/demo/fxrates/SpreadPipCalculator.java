package com.example.demo.fxrates;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class SpreadPipCalculator {

    public List<Map<String, Object>> changeSpread(List<Map<String, Object>> currencyPairs) {

        for (Map<String, Object> pair : currencyPairs) {
            double bidPrice = (double) pair.get("bid");
            double askPrice = (double) pair.get("ask");

            double roundedBid = changePips(bidPrice);
            pair.put("bid", roundedBid);

            double roundedAsk = changePips(askPrice);
            pair.put("ask", roundedAsk);

            double spread = roundedAsk - roundedBid;
            pair.put("spread", spread);

            double midRateValue = (roundedBid + roundedAsk) / 2;
            pair.put("midRateValue", midRateValue);
        }
        return currencyPairs;
    }

    private double changePips(double value) {
        long intValue = (long) value;
        double fractionalPart = value - intValue;
        double randomDecimals = new Random().nextDouble() / 10000; 
        if (new Random().nextBoolean()) {
            randomDecimals = -randomDecimals;
        }
        return intValue + fractionalPart + randomDecimals;
    }


}
