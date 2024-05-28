package com.example.gateway;

import com.example.common.model.CreditCardDetails;
import com.example.common.model.PaymentResponse;

import java.math.BigDecimal;

public class DefaultPaymentGateway implements PaymentGateway {

    @Override
    public PaymentResponse processPayment(CreditCardDetails details, BigDecimal amount) {
        // デフォルトの支払い処理を実装
        return new PaymentResponse("default_txn123", "SUCCESS");
    }

    @Override
    public PaymentResponse refundPayment(String transactionId, BigDecimal amount) {
        // デフォルトの返金処理を実装
        return new PaymentResponse(transactionId, "REFUNDED");
    }

    @Override
    public boolean authenticate3DSecure(CreditCardDetails details) {
        // デフォルトの3Dセキュア認証を実装
        return true;
    }
}
