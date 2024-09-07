package com.anakki.data.utils.common;

import java.security.SecureRandom;

public class VerificationCodeGenerator {

    // 使用 SecureRandom 生成更安全的随机数
    private static final SecureRandom random = new SecureRandom();
    private static final int CODE_LENGTH = 6;

    public static String generateVerificationCode() {
        // 生成6位随机验证码（100000-999999）
        int verificationCode = random.nextInt(900000) + 100000;
        return String.valueOf(verificationCode);
    }

}