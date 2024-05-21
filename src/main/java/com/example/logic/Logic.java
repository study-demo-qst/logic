package com.example.logic;

import com.example.common.util.CryptoUtil;
import com.example.common.util.RandomUtil;

public class Logic {

    public String getLogic() {
        return "This is the logic";
    }

    public String encryptText(String plainText) {
        try {
            return CryptoUtil.encrypt(plainText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ランダムな文字列を生成し、暗号化します。
     * 
     * @param length 生成するランダムな文字列の長さ
     * @return 暗号化されたランダムな文字列
     */
    public String generateAndEncryptRandomString(int length) {
        try {
            String randomString = RandomUtil.randomString(length);
            return CryptoUtil.encrypt(randomString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
