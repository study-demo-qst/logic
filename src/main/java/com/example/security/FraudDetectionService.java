package com.example.security;

import com.example.common.model.CreditCardDetails;
import org.springframework.stereotype.Component;

@Component
public class FraudDetectionService {
    public boolean detect(CreditCardDetails details) {
        // 不正検知のロジックを実装
        return false; // 仮に不正がないと評価
    }
}
