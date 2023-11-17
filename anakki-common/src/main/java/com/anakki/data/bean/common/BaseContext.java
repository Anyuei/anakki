package com.anakki.data.bean.common;

public class BaseContext {
 
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
 
    public static void setCurrentNickname(String nickname) {
        threadLocal.set(nickname);
    }
 
    public static String getCurrentNickname() {
        String nickname= threadLocal.get();
        if (null==nickname){
            throw new RuntimeException("请登录");
        }
        return nickname;
    }
 
    public static void removeCurrentNickname() {
        threadLocal.remove();
    }
 
}