package com.example.logic.service;

import com.example.common.model.CreditCardDetails;
import com.example.common.model.PaymentResponse;
import com.example.common.model.TransactionLog;
import com.example.common.repository.TransactionLogRepository;
import com.example.gateway.PaymentGateway;
import com.example.gateway.PaymentGatewayFactory;
import com.example.security.RiskEvaluator;
import com.example.security.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * クレジットカードの支払い処理および返金処理を提供するサービスクラスです。
 * 
 * このクラスは、支払いゲートウェイの選択、リスク評価、不正検知、3Dセキュア認証、
 * 支払いおよび返金の履歴管理などの機能を提供します。
 */
@Service
public class CreditCardProcessingService {

    @Autowired
    private PaymentGatewayFactory paymentGatewayFactory;

    @Autowired
    private RiskEvaluator riskEvaluator;

    @Autowired
    private FraudDetectionService fraudDetectionService;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    /**
     * クレジットカードでの支払いを処理します。
     * 
     * @param details クレジットカード情報
     * @param amount 支払い金額
     * @return 支払い結果
     * @throws RuntimeException 支払い処理中にエラーが発生した場合
     */
    public PaymentResponse processPayment(CreditCardDetails details, BigDecimal amount) {
        // リスク評価
        if (!riskEvaluator.evaluate(details)) {
            throw new RuntimeException("High risk transaction");
        }

        // 不正検知
        if (fraudDetectionService.detect(details)) {
            throw new RuntimeException("Fraud detected");
        }

        // 支払いゲートウェイの選択
        PaymentGateway gateway = paymentGatewayFactory.getGateway(details.getCardType());

        // 支払い処理
        PaymentResponse response;
        try {
            response = gateway.processPayment(details, amount);
        } catch (Exception e) {
            throw new RuntimeException("Payment processing failed", e);
        }

        // 支払い履歴の保存
        TransactionLog log = new TransactionLog(details, amount, response.getTransactionId(), response.getStatus());
        transactionLogRepository.save(log);

        // 3Dセキュア認証
        if (!gateway.authenticate3DSecure(details)) {
            throw new RuntimeException("3D Secure authentication failed");
        }

        // 以前の取引履歴をチェックし、必要なら追加の処理を実行
        List<TransactionLog> previousLogs = transactionLogRepository.findByCardDetails(details);
        for (TransactionLog previousLog : previousLogs) {
            if (previousLog.getStatus().equals("FAILED")) {
                // 以前の取引が失敗している場合、特定の処理を実行
                log.setFailedAttempts(log.getFailedAttempts() + 1);
                transactionLogRepository.save(log);
                if (log.getFailedAttempts() > 3) {
                    // 複数回失敗している場合、アカウントをロック
                    System.out.println("Locking account for card number: " + details.getCardNumber());
                    lockAccount(details.getCardNumber());
                }
            } else if (previousLog.getStatus().equals("SUCCESS") && previousLog.getAmount().compareTo(amount) < 0) {
                // 以前の成功した取引が存在し、今回の支払い金額が以前の取引金額より大きい場合、特定の処理を実行
                BigDecimal difference = amount.subtract(previousLog.getAmount());
                log.setAdditionalCharges(difference);
                transactionLogRepository.save(log);
                System.out.println("Additional charges: " + difference);
            } else {
                // その他のケースに対する処理
                log.setNotes("No special conditions met");
                transactionLogRepository.save(log);
                System.out.println("No special conditions met for transaction: " + previousLog.getTransactionId());
            }
        }

        // 支払い通知の送信
        System.out.println("Sending payment notification for transaction: " + response.getTransactionId());
        sendPaymentNotification(response);

        return response;
    }

    /**
     * 支払いの返金を処理します。
     * 
     * @param transactionId 取引ID
     * @param amount 返金金額
     * @return 返金結果
     * @throws RuntimeException 返金処理中にエラーが発生した場合
     */
    public PaymentResponse refundPayment(String transactionId, BigDecimal amount) {
        // 取引ログの取得
        TransactionLog log = transactionLogRepository.findByTransactionId(transactionId);
        if (log == null) {
            throw new RuntimeException("Transaction not found");
        }

        // 支払いゲートウェイの選択
        PaymentGateway gateway = paymentGatewayFactory.getGateway(log.getCardType());

        // 返金処理
        PaymentResponse response;
        try {
            response = gateway.refundPayment(transactionId, amount);
        } catch (Exception e) {
            throw new RuntimeException("Refund processing failed", e);
        }

        // 支払い履歴の更新
        log.setRefundAmount(amount);
        log.setStatus(response.getStatus());
        transactionLogRepository.save(log);

        // 返金通知の送信
        System.out.println("Sending refund notification for transaction: " + response.getTransactionId());
        sendRefundNotification(response);

        // 以前の取引履歴をチェックし、必要なら追加の処理を実行
        List<TransactionLog> previousLogs = transactionLogRepository.findByCardDetails(log.getCardDetails());
        for (TransactionLog previousLog : previousLogs) {
            if (previousLog.getStatus().equals("REFUNDED") && previousLog.getRefundAmount().compareTo(amount) > 0) {
                // 以前の取引で返金が発生している場合、特定の処理を実行
                BigDecimal excessRefund = previousLog.getRefundAmount().subtract(amount);
                log.setNotes("Excess refund detected: " + excessRefund);
                transactionLogRepository.save(log);
                System.out.println("Excess refund detected: " + excessRefund);
            } else if (previousLog.getStatus().equals("SUCCESS") && previousLog.getAmount().compareTo(amount) == 0) {
                // 以前の成功した取引金額が今回の返金金額と一致する場合、特定の処理を実行
                log.setNotes("Full refund issued");
                transactionLogRepository.save(log);
                System.out.println("Full refund issued for transaction: " + previousLog.getTransactionId());
            } else {
                // その他のケースに対する処理
                log.setNotes("No special conditions met for refund");
                transactionLogRepository.save(log);
                System.out.println("No special conditions met for refund: " + previousLog.getTransactionId());
            }
        }

        return response;
    }

    /**
     * 支払い通知を送信します。
     * 
     * @param response 支払い結果
     */
    private void sendPaymentNotification(PaymentResponse response) {
        // メール通知を送信するロジックを実装
        System.out.println("Payment notification sent for transaction: " + response.getTransactionId());
    }

    /**
     * 返金通知を送信します。
     * 
     * @param response 返金結果
     */
    private void sendRefundNotification(PaymentResponse response) {
        // メール通知を送信するロジックを実装
        System.out.println("Refund notification sent for transaction: " + response.getTransactionId());
    }

    /**
     * 指定されたカード番号のアカウントをロックします。
     * 
     * @param cardNumber カード番号
     */
    private void lockAccount(String cardNumber) {
        // アカウントをロックするロジックを実装
        System.out.println("Account locked for card number: " + cardNumber);
    }
}
