package com.example.demo.spot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SpotRateAnalysis {

    public static Map<String, Map<String, Object>> findBestBanks(Map<String, SpotRateService.SpotValues> spotValues) {
        String lowestBuySpotBank = null;
        String highestSellSpotBank = null;
        double lowestBuySpotLotValue = Double.MAX_VALUE;
        double highestSellSpotLotValue = Double.MIN_VALUE;

        for (Map.Entry<String, SpotRateService.SpotValues> entry : spotValues.entrySet()) {
            String bankName = entry.getKey();
            SpotRateService.SpotValues values = entry.getValue();

            if (values.getBuySpotLotValue() < lowestBuySpotLotValue) {
                lowestBuySpotLotValue = values.getBuySpotLotValue();
                lowestBuySpotBank = bankName;
            }

            if (values.getSellSpotLotValue() > highestSellSpotLotValue) {
                highestSellSpotLotValue = values.getSellSpotLotValue();
                highestSellSpotBank = bankName;
            }
        }

        Map<String, Map<String, Object>> result = new HashMap<>();
        Map<String, Object> lowestBuySpotInfo = new HashMap<>();
        Map<String, Object> highestSellSpotInfo = new HashMap<>();

        lowestBuySpotInfo.put("Bank", lowestBuySpotBank);
        lowestBuySpotInfo.put("Value", lowestBuySpotLotValue);
        result.put("LowestBuySpot", lowestBuySpotInfo);

        highestSellSpotInfo.put("Bank", highestSellSpotBank);
        highestSellSpotInfo.put("Value", highestSellSpotLotValue);
        result.put("HighestSellSpot", highestSellSpotInfo);

        return result;
    }
}
