package com.anakki.data.utils.common;

/***
 * @author pei
 * @date 2024/8/29 3:57
 */
public class NumberUtil {

    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str); // 可以替换为 Double.parseDouble 如果你需要判断浮点数
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
