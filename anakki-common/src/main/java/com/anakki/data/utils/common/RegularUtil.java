package com.anakki.data.utils.common;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: RegularUtil
 * Description:
 *
 * @author Anakki
 * @date 2023/5/28 22:38
 */
public class RegularUtil {

    /**
     * 获取与正则匹配的字符串
     * @param text
     * @param regex
     * @return
     */
    public static String matcher(String text,String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

    /**
     * 获取与正则匹配的字段数组
     *
     * @param regular
     * @param fields
     * @return
     */
    public static List<String> getMatchedFields(String regular, List<String> fields) {
        LinkedList<String> matchedStringList = new LinkedList<>();
        fields.forEach(field -> {
            if (field.matches(regular)) {
                matchedStringList.add(field);
            }
        });
        return matchedStringList;
    }
}
