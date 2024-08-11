package com.anakki.data.bean.common;

public class BaseContextForManage {
 
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
 
    public static void setCurrentNickname(String nickname) {
        threadLocal.set(nickname);
    }
 
    public static String  getCurrentNickname(Boolean throwException) {
        String nickname= threadLocal.get();
        if (null==nickname&&throwException){
            throw new RuntimeException("请登录");
        }
        return nickname;
    }

    public static String getCurrentNickname() {
        return getCurrentNickname(true);
    }
    public static void removeCurrentNickname() {
        threadLocal.remove();
    }
 
}