package com.example.demo.fxrates;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FxRateGenerator {

    private static final Random RANDOM = new Random();

    private final CurrencyPairGenerator currencyPairGenerator = new CurrencyPairGenerator();
    private final BidAskGenerator bidAskGenerator = new BidAskGenerator();

    InterestRate interestRate = new InterestRate();

    public List<Map<String, Object>> generateCurrencyPairList() {
        List<Map<String, Object>> currencyPairsList = new ArrayList<>();
        List<String> allPairs = currencyPairGenerator.getAllCurrencyPairs();

        for (String pair : allPairs) {
            int integerPart = 20 + RANDOM.nextInt(80);
            double fixedPart = RANDOM.nextDouble(100);
            double[] bidAskPrices = bidAskGenerator.generateRandomBidAskPrice(integerPart, fixedPart);
            String type = currencyPairGenerator.getType(pair);

            Map<String, Object> pairInfo = new LinkedHashMap<>();
            pairInfo.put("ccyPair", pair);
            pairInfo.put("pairType", type);
            pairInfo.put("bid", bidAskPrices[1]);
            pairInfo.put("ask", bidAskPrices[0]);
            pairInfo.put("interestRates", interestRate.getInterestRates(pair));
            currencyPairsList.add(pairInfo);
        }
        return currencyPairsList;
    }
}
