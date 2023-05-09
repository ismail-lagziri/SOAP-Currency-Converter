package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDateTime timestamp;
    private String fromCurrency;
    private String toCurrency;
    private double amount;
    private double rate;
    private double convertedAmount;

    public Transaction(LocalDateTime timestamp, String fromCurrency, String toCurrency, double amount, double rate, double convertedAmount) {
        this.timestamp = timestamp;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.rate = rate;
        this.convertedAmount = convertedAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public double getRate() {
        return rate;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] Converted %.2f %s to %.2f %s at rate %.4f",
                timestamp.format(formatter), amount, fromCurrency, convertedAmount, toCurrency, rate);
    }
}