package com.example.demo.bankSpotRates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpotRate {

    private final List<BankRate> bankRates;

    public SpotRate() {
        bankRates = new ArrayList<>();
        initializeBankRates();
    }

    private void initializeBankRates() {
        Random random = new Random();
        bankRates.add(new BankRate("HDFC", getRandomRate(random)));
        bankRates.add(new BankRate("ICICI", getRandomRate(random)));
        bankRates.add(new BankRate("SBI", getRandomRate(random)));
        bankRates.add(new BankRate("AxisBank", getRandomRate(random)));
        bankRates.add(new BankRate("IDFC", getRandomRate(random)));
    }

    private double getRandomRate(Random random) {
        return (random.nextDouble() * 0.01) - 0.005; // Generates a random number between -0.005 and 0.005
    }

    public List<BankRate> getBankRates() {
        return bankRates;
    }

    public static class BankRate {
        private final String bankName;
        private final double rate;

        public BankRate(String bankName, double rate) {
            this.bankName = bankName;
            this.rate = rate;
        }

        public String getBankName() {
            return bankName;
        }

        public double getRate() {
            return rate;
        }
    }
}

