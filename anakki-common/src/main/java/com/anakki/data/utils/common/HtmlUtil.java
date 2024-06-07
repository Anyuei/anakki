package com.anakki.data.utils.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: HtmlUtil
 * Description:
 *
 * @author Anakki
 * @date 2024/6/7 7:38
 */
public class HtmlUtil {

    public static String getFirstImg(String html){
        // Regular expression pattern to match the src attribute of the first img tag
        Pattern pattern = Pattern.compile("<img\\s+[^>]*src\\s*=\\s*['\"]([^'\"]*?)['\"][^>]*>");

        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    public static String getFirstH1(String html){
        // Regular expression pattern to match the src attribute of the first img tag
        Pattern pattern = Pattern.compile("<h1.*?>(.*?)</h1>");
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    public static String getFirstP(String html){
        // Regular expression pattern to match the src attribute of the first img tag
        Pattern pattern = Pattern.compile("<p.*?>(.*?)</p>");
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
