package com.example.logic.service;

import com.example.common.util.CryptoUtil;

public class EncyptDecryptService {
    
   // commonのCryptoUtilを使って、受け取った文字列を暗号化する
    public String encrypt(String plainText) throws Exception {
        return CryptoUtil.encrypt(plainText);
    }

    // commonのCryptoUtilを使って、受け取った文字列を復号化する
    public String decrypt(String cipherText) throws Exception {
        return CryptoUtil.decrypt(cipherText);
    }
}
