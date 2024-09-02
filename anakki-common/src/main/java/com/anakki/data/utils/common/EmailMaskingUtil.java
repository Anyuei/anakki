package com.anakki.data.utils.common;

public class EmailMaskingUtil {

    /**
     * 脱敏邮箱地址
     *
     * @param email 原始邮箱地址
     * @return 脱敏后的邮箱地址
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }

        String[] parts = email.split("@");
        String localPart = parts[0];
        String domainPart = parts[1];

        // 保留localPart的前两位和最后一位字符，其余字符用星号替换
        if (localPart.length() > 3) {
            localPart = localPart.substring(0, 2) + repeat("*", localPart.length() - 3) + localPart.substring(localPart.length() - 1);
        } else {
            // 如果localPart不足4位，直接将其全部用星号替换
            localPart = repeat("*", localPart.length());
        }

        return localPart + "@" + domainPart;
    }

    /**
     * 重复给定字符若干次
     *
     * @param ch    要重复的字符
     * @param count 重复次数
     * @return 生成的字符串
     */
    private static String repeat(String ch, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String email = "example@example.com";
        String maskedEmail = maskEmail(email);
        System.out.println(maskedEmail);  // 输出: ex****e@example.com
    }
}