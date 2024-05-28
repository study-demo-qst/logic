package com.example.gateway;

import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayFactory {
    public PaymentGateway getGateway(String cardType) {
        // カードタイプに基づいて適切なPaymentGatewayを返すロジックを実装
        // ここでは仮に、カードタイプが"VISA"の場合、VisaPaymentGatewayを返すとします
        if (cardType.equalsIgnoreCase("VISA")) {
            return new VisaPaymentGateway();
        } else {
            // 他のカードタイプに対する処理を追加
            return new DefaultPaymentGateway();
        }
    }
}
