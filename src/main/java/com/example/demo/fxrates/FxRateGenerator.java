package com.example.demo.fxrates;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FxRateGenerator {

    private static final Random RANDOM = new Random();

    private final CurrencyPairGenerator currencyPairGenerator = new CurrencyPairGenerator();
    private final BidAskGenerator bidAskGenerator = new BidAskGenerator();

    public List<Map<String, Object>> generateCurrencyPairList() {
        List<Map<String, Object>> currencyPairsList = new ArrayList<>();
        List<String> allPairs = currencyPairGenerator.getAllCurrencyPairs();

        for (String pair : allPairs) {
            int integerPart = RANDOM.nextInt(100);
            double fixedPart = RANDOM.nextDouble(100) * 100;
            double[] bidAskPrices = bidAskGenerator.generateRandomBidAskPrice(integerPart, fixedPart);
            double bidPrice = bidAskPrices[0];
            double askPrice = bidAskPrices[1];
            String type = currencyPairGenerator.getType(pair);

            Map<String, Object> pairInfo = new LinkedHashMap<>();
            pairInfo.put("pair", pair);
            pairInfo.put("type", type);
            pairInfo.put("bid", bidPrice);
            pairInfo.put("ask", askPrice);

            currencyPairsList.add(pairInfo);
        }
        return currencyPairsList;
    }

}
