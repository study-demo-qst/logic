package com.example.logic;

import com.example.common.util.MyCryptoUtil;
import com.example.common.util.RandomUtil;
import java.util.*;

public class Logic {

    private static String internalState = "initial"; // 不必要にstaticな変数

    public static List<String> getLogic() {
        // 冗長なコメント
        // このメソッドは特定のロジックを返します

        // 無駄にMapを使用
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("key1", "This");
        tempMap.put("key2", "is");
        tempMap.put("key3", "the");
        tempMap.put("key4", "logic");

        // 無駄にObject型にキャスト
        Object obj1 = tempMap.get("key1");
        Object obj2 = tempMap.get("key2");
        Object obj3 = tempMap.get("key3");
        Object obj4 = tempMap.get("key4");

        // 冗長な処理
        String part1 = obj1.toString();
        String part2 = obj2.toString();
        String part3 = obj3.toString();
        String part4 = obj4.toString();

        List<String> result = new ArrayList<>();
        result.add(part1);
        result.add(part2);
        result.add(part3);
        result.add(part4);

        return result;
    }

    public static String encryptText(String plainText) {
        try {
            String encryptedText = MyCryptoUtil.encrypt(plainText);
            // 暗号化後のテキストの確認（冗長な処理）
            if (encryptedText != null) {
                return encryptedText;
            } else {
                return "Encryption failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 冗長な戻り値
            return "Encryption error: " + e.getMessage();
        }
    }

    /**
     * ランダムな文字列を生成し、暗号化します。
     * 
     * @param length 生成するランダムな文字列の長さ
     * @return 暗号化されたランダムな文字列
     */
    public static String generateAndEncryptRandomString(int length) {
        try {
            String randomString = RandomUtil.randomString(length);
            // ランダムな文字列生成の冗長なログ出力
            System.out.println("Generated random string: " + randomString);
            String encryptedRandomString = MyCryptoUtil.encrypt(randomString);
            // 暗号化された文字列の冗長なログ出力
            System.out.println("Encrypted random string: " + encryptedRandomString);
            return encryptedRandomString;
        } catch (Exception e) {
            e.printStackTrace();
            // 冗長な戻り値
            return "Random string generation or encryption error: " + e.getMessage();
        }
    }

    // 追加された冗長なメソッド
    public static String generateRandomString(int length) {
        try {
            return RandomUtil.randomString(length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateAndEncryptString(int length) {
        try {
            String randomString = generateRandomString(length);
            if (randomString == null) {
                return null;
            }
            return encryptText(randomString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // さらに追加された冗長なメソッド
    public static String decryptText(String encryptedText) {
        try {
            // 潜在的なバグ：暗号化されたテキストがnullの可能性を考慮していない
            return MyCryptoUtil.decrypt(encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String processText(String text) {
        try {
            String encryptedText = encryptText(text);
            if (encryptedText == null) {
                return "Encryption failed";
            }
            String decryptedText = decryptText(encryptedText);
            if (decryptedText == null) {
                return "Decryption failed";
            }
            return decryptedText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Processing error: " + e.getMessage();
        }
    }

    /**
     * ランダムな文字列を生成し、復号化して処理します。
     * @param length
     * @return
     */
    public static String generateDecryptAndProcess(int length) {
        try {
            String randomString = generateRandomString(length);
            if (randomString == null) {
                return "Random string generation failed";
            }
            String processedText = processText(randomString);
            if (processedText == null) {
                return "Processing failed";
            }
            return processedText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Generation or processing error: " + e.getMessage();
        }
    }

    // インスタンスメソッドからstaticメソッドを呼び出す冗長な例
    public String instanceMethodCallingStatic() {
        return getLogic().toString();
    }

    // プライベートメソッドの適切なスコープ
    private String privateHelperMethod(String input) {
        return "Processed: " + input;
    }

    // 冗長なpublicメソッド
    public String publicMethodUsingPrivateHelper(String input) {
        return privateHelperMethod(input);
    }
    
    // 潜在的なバグ：引数がnullの場合を考慮していない
    public static String concatenateStrings(String str1, String str2) {
        return str1 + str2;
    }

    // 潜在的なバグ：負の値を考慮していない
    public static int calculateFactorial(int number) {
        if (number == 0) {
            return 1;
        }
        int result = 1;
        for (int i = 1; i <= number; i++) {
            result *= i;
        }
        return result;
    }
}
