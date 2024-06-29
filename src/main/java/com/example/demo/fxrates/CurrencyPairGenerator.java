package com.example.demo.fxrates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CurrencyPairGenerator {

    // Major Currency Pairs
    private ArrayList<String> majorCurrencyPairs = new ArrayList<>(Arrays.asList(
        "EUR/USD", // Euro/US Dollar
        "USD/JPY", // US Dollar/Japanese Yen
        "GBP/USD", // British Pound/US Dollar
        "USD/CHF", // US Dollar/Swiss Franc
        "AUD/USD", // Australian Dollar/US Dollar
        "USD/CAD", // US Dollar/Canadian Dollar
        "NZD/USD"  // New Zealand Dollar/US Dollar
    ));
    
    // Minor Currency Pairs
    private ArrayList<String> minorCurrencyPairs = new ArrayList<>(Arrays.asList(
        "EUR/GBP", // Euro/British Pound
        "EUR/AUD", // Euro/Australian Dollar
        "EUR/CAD", // Euro/Canadian Dollar
        "EUR/CHF", // Euro/Swiss Franc
        "EUR/JPY", // Euro/Japanese Yen
        "EUR/NZD", // Euro/New Zealand Dollar
        "GBP/AUD", // British Pound/Australian Dollar
        "GBP/CAD", // British Pound/Canadian Dollar
        "GBP/CHF", // British Pound/Swiss Franc
        "GBP/JPY", // British Pound/Japanese Yen
        "GBP/NZD", // British Pound/New Zealand Dollar
        "AUD/CAD", // Australian Dollar/Canadian Dollar
        "AUD/CHF", // Australian Dollar/Swiss Franc
        "AUD/JPY", // Australian Dollar/Japanese Yen
        "AUD/NZD", // Australian Dollar/New Zealand Dollar
        "CAD/CHF", // Canadian Dollar/Swiss Franc
        "CAD/JPY", // Canadian Dollar/Japanese Yen
        "CHF/JPY", // Swiss Franc/Japanese Yen
        "NZD/CAD", // New Zealand Dollar/Canadian Dollar
        "NZD/CHF", // New Zealand Dollar/Swiss Franc
        "NZD/JPY"  // New Zealand Dollar/Japanese Yen
    ));

    // Exotic Currency Pairs
    private ArrayList<String> exoticCurrencyPairs = new ArrayList<>(Arrays.asList(
        "USD/HKD", // US Dollar/Hong Kong Dollar
        "USD/SGD", // US Dollar/Singapore Dollar
        "USD/TRY", // US Dollar/Turkish Lira
        "USD/ZAR", // US Dollar/South African Rand
        "USD/MXN", // US Dollar/Mexican Peso
        "USD/THB", // US Dollar/Thai Baht
        "USD/DKK", // US Dollar/Danish Krone
        "USD/NOK", // US Dollar/Norwegian Krone
        "USD/SEK", // US Dollar/Swedish Krona
        "EUR/TRY", // Euro/Turkish Lira
        "EUR/ZAR", // Euro/South African Rand
        "EUR/MXN", // Euro/Mexican Peso
        "EUR/SGD", // Euro/Singapore Dollar
        "EUR/HKD", // Euro/Hong Kong Dollar
        "GBP/TRY", // British Pound/Turkish Lira
        "GBP/ZAR", // British Pound/South African Rand
        "GBP/MXN", // British Pound/Mexican Peso
        "GBP/SGD", // British Pound/Singapore Dollar
        "GBP/HKD", // British Pound/Hong Kong Dollar
        "AUD/SGD", // Australian Dollar/Singapore Dollar
        "AUD/HKD", // Australian Dollar/Hong Kong Dollar
        "AUD/TRY", // Australian Dollar/Turkish Lira
        "NZD/SGD", // New Zealand Dollar/Singapore Dollar
        "NZD/HKD"  // New Zealand Dollar/Hong Kong Dollar
    ));

    // Method to get all currency pairs
    public ArrayList<String> getAllCurrencyPairs() {
        ArrayList<String> allCurrencyPairs = new ArrayList<>();
        allCurrencyPairs.addAll(majorCurrencyPairs);
        allCurrencyPairs.addAll(minorCurrencyPairs);
        allCurrencyPairs.addAll(exoticCurrencyPairs);
        return allCurrencyPairs;
    }

    // Helper method to determine the type of the pair
    public String getType(String pair) {
        if (majorCurrencyPairs.contains(pair)) {
            return "major";
        } else if (minorCurrencyPairs.contains(pair)) {
            return "minor";
        } else {
            return "exotic";
        }
    }

    public List<String> getPairsByType(String type) {
        switch (type.toLowerCase()) {
            case "major":
                return majorCurrencyPairs;
            case "minor":
                return minorCurrencyPairs;
            case "exotic":
                return exoticCurrencyPairs;
            default:
                return new ArrayList<>();
        }
    }
}
