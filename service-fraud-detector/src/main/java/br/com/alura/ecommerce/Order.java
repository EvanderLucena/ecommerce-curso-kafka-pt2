package br.com.alura.ecommerce;

import java.math.BigDecimal;

public class Order {

    private final String orderId;
    private final String email;
    private final BigDecimal amount;

    public Order(String orderId, BigDecimal amount, String email) {
        this.orderId = orderId;
        this.email = email;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", email='" + email + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getEmail() {
        return email;
    }
}
