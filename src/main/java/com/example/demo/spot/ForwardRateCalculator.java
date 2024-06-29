package com.example.demo.spot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ForwardRateCalculator {

    private SpotRate spotRate;

    public ForwardRateCalculator(SpotRate spotRate) {
        this.spotRate = spotRate;
    }

    public Map<String, Map<String, Object>> calculateForwardRates(double bid, double ask, double amount, int noOfDays, double bidInterestRate, double quoteInterestRate) {
        Map<String, Map<String, Object>> data = new HashMap<>();
        List<SpotRate.BankRate> bankRates = spotRate.getBankRates();

        for (SpotRate.BankRate bankRate : bankRates) {
            Map<String, Object> bankData = new HashMap<>();
            bankData.put("buy", calculateRates(bid, bankRate.getBidrate(), noOfDays, bidInterestRate, quoteInterestRate, amount));
            bankData.put("sell", calculateRates(ask, bankRate.getAskrate(), noOfDays, bidInterestRate, quoteInterestRate, amount));
            data.put(bankRate.getBankName(), bankData);
        }

        return data;
    }

    private Map<String, Double> calculateRates(double spotRate, double bankRate, int noOfDays, double bidInterestRate, double quoteInterestRate, double amount) {
        Map<String, Double> rates = new HashMap<>();

        double forwardPoint = calculateForwardPoint(spotRate, bankRate, noOfDays, bidInterestRate, quoteInterestRate);
        double forwardRate = spotRate + forwardPoint / 10000;
        double diff = (forwardRate - spotRate) * amount;

        rates.put("spot", spotRate);
        rates.put("forwardPoint", forwardPoint);
        rates.put("diff", diff);
        rates.put("forwardRate", forwardRate);

        return rates;
    }

    private double calculateForwardPoint(double spotRate, double bankRate, int noOfDays, double bidInterestRate, double quoteInterestRate) {
        return ((quoteInterestRate - bidInterestRate) * spotRate * noOfDays) / (360 * 100) ;
    }

    public SpotRate getSpotRate() {
        return spotRate;
    }

    public void setSpotRate(SpotRate spotRate) {
        this.spotRate = spotRate;
    }
}
