package com.example.gateway;

import com.example.common.model.CreditCardDetails;
import com.example.common.model.PaymentResponse;

import java.math.BigDecimal;

public interface PaymentGateway {
    PaymentResponse processPayment(CreditCardDetails details, BigDecimal amount);
    PaymentResponse refundPayment(String transactionId, BigDecimal amount);
    boolean authenticate3DSecure(CreditCardDetails details);
}
