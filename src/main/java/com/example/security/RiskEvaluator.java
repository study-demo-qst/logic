package com.example.security;

import com.example.common.model.CreditCardDetails;
import org.springframework.stereotype.Component;

@Component
public class RiskEvaluator {
    public boolean evaluate(CreditCardDetails details) {
        // リスク評価のロジックを実装
        return true; // 仮にリスクがないと評価
    }
}
