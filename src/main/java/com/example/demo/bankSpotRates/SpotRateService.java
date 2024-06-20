package com.example.demo.bankSpotRates;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SpotRateService {

    public Map<String, SpotValues> getSpotValues(double bid, double ask, int lots) {
        SpotRate spotRate = new SpotRate();
        SpotRateCalculator calculator = new SpotRateCalculator(bid, ask, lots);
        Map<String, SpotValues> result = new HashMap<>();

        for (SpotRate.BankRate bankRate : spotRate.getBankRates()) {
            double buySpotValue = calculator.calculateBuySpotValue(bankRate.getRate());
            double sellSpotValue = calculator.calculateSellSpotValue(bankRate.getRate());
            double buySpotLotValue = calculator.calculateBuySpotLotValue(bankRate.getRate(), lots);
            double sellSpotLotValue = calculator.calculateSellSpotLotValue(bankRate.getRate(), lots);
            int lotsValue = lots;
            result.put(bankRate.getBankName(), new SpotValues(buySpotValue, sellSpotValue, buySpotLotValue, sellSpotLotValue, lotsValue));
        }

        return result;
    }

    public static class SpotValues {
        private final double buySpotValue;
        private final double sellSpotValue;
        private final double buySpotLotValue;
        private final double sellSpotLotValue;
        private final int lotsValue;

        public SpotValues(double buySpotValue, double sellSpotValue, double buySpotLotValue, double sellSpotLotValue, int lotsValue) {
            this.buySpotValue = buySpotValue;
            this.sellSpotValue = sellSpotValue;
            this.buySpotLotValue = buySpotLotValue;
            this.sellSpotLotValue = sellSpotLotValue;
            this.lotsValue = lotsValue;
        }

        public double getBuySpotValue() {
            return buySpotValue;
        }

        public double getSellSpotValue() {
            return sellSpotValue;
        }

        public double getBuySpotLotValue() {
            return buySpotLotValue;
        }

        public double getSellSpotLotValue() {
            return sellSpotLotValue;
        }

        public int getLotsValue() {
            return lotsValue;
        }
    }
}

