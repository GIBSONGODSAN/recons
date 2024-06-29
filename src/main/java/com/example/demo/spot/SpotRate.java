package com.example.demo.spot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SpotRate {

    private final List<BankRate> bankRates;

    public SpotRate() {
        bankRates = new ArrayList<>();
        initializeBankRates();
    }

    private void initializeBankRates() {
        bankRates.add(new BankRate("HDFC", 0.003, 0.003));
        bankRates.add(new BankRate("ICICI", 0.003, 0.003));
        bankRates.add(new BankRate("SBI", 0.003, 0.003));
        bankRates.add(new BankRate("AxisBank", 0.003, 0.003));
        bankRates.add(new BankRate("IDFC", 0.003, 0.003));
    }

    public List<BankRate> getBankRates() {
        return bankRates;
    }

    public static class BankRate {
        private final String bankName;
        private final double bidrate;
        private final double askrate;

        public BankRate(String bankName, double bidrate, double askrate) {
            this.bankName = bankName;
            this.bidrate = bidrate;
            this.askrate = askrate;
        }

        public String getBankName() {
            return bankName;
        }

        public double getBidrate() {
            return bidrate;
        }

        public double getAskrate() {
            return askrate;
        }
    }
}

