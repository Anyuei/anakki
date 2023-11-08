package com.anakki.data.utils.common;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 随机手机号生成器
 */
@Component
public class RandomPhoneNumGenerator {

    public static void setGeneratorState(Boolean isAvailable) {
        RedisUtil.StringOps.set("PHONE_NUM_GENERATE_STATE", isAvailable.toString());
    }

    public static Boolean getGeneratorState() {
        String phoneNumGenerateState = RedisUtil.StringOps.get("PHONE_NUM_GENERATE_STATE");
        return Boolean.TRUE.toString().equals(phoneNumGenerateState);
    }
 
    public static String generatePhoneNum() {
        Random random = new Random();
        // 中国移动号段
        String[] cmccPrefix = {"134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159", "178", "182", "183", "184", "187", "188"};
        // 中国联通号段
        String[] cuccPrefix = {"130", "131", "132", "145", "155", "156", "166", "175", "176", "185", "186"};
        // 中国电信号段
        String[] ctcPrefix = {"133", "149", "153", "173", "177", "180", "181", "189", "199"};
 
        String prefix = "";
        int index = random.nextInt(3);
        switch (index) {
            case 0:
                prefix = cmccPrefix[random.nextInt(cmccPrefix.length)];
                break;
            case 1:
                prefix = cuccPrefix[random.nextInt(cuccPrefix.length)];
                break;
            case 2:
                prefix = ctcPrefix[random.nextInt(ctcPrefix.length)];
                break;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        for (int i = 0; i < 8; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}