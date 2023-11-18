package com.anakki.data.utils.common;

import com.alibaba.fastjson.JSONObject;
import com.anakki.data.bean.common.ManagerToken;
import com.anakki.data.bean.common.UserToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConfigurationProperties(prefix = "jwt")
@Slf4j
public class JwtUtil {

    /**
     * 定义 token 返回头部
     */
    public static String header;

    /**
     * token 前缀
     */
    public static String tokenPrefix;

    /**
     * 签名密钥
     */
    public static String secret;


    /**
     * 管理员签名密钥
     */
    public static String managerSecret;

    public void setExpireManagerTime(int expireManagerTime) {
        JwtUtil.expireManagerTime = expireManagerTime;
    }
    /**
     * 有效期
     */
    public static long expireTime;
    public static long expireManagerTime;
    /**
     * 存进客户端的 token 的 key 名
     */
    public static final String USER_LOGIN_TOKEN = "token";

    /**
     * 设置 token 头部
     * @param header token 头部
     */
    public void setHeader(String header) {
        JwtUtil.header = header;
    }

    /**
     * 设置 token 前缀
     * @param tokenPrefix token 前缀
     */
    public void setTokenPrefix(String tokenPrefix) {
        JwtUtil.tokenPrefix = tokenPrefix;
    }

    /**
     * 设置 token 密钥
     * @param secret token 密钥
     */
    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }


    /**
     * 设置 token 密钥
     * @param managerSecret token 密钥
     */
    public void setManagerSecret(String managerSecret) {
        JwtUtil.managerSecret = managerSecret;
    }
    /**
     * 设置 token 有效时间
     * @param expireTimeInt token 有效时间
     */
    public void setExpireTime(int expireTimeInt) {
        JwtUtil.expireTime = expireTimeInt;
    }
    /**
     * 创建 TOKEN
     * JWT 构成: header, payload, signature
     * @param userToken jwt 所面向的用户，即用户名
     * @return token 值
     */
    public static String createToken(UserToken userToken) {
        return tokenPrefix + JWT.create()
                .withSubject(JSONObject.toJSONString(userToken))
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .sign(Algorithm.HMAC512(secret));
    }
    public static String createMangerToken(ManagerToken managerToken) {
        return tokenPrefix + JWT.create()
                .withSubject(JSONObject.toJSONString(managerToken))
                .withExpiresAt(new Date(System.currentTimeMillis() + expireManagerTime))
                .sign(Algorithm.HMAC512(managerSecret));
    }
    /**
     * 验证 token
     * @param token 验证的 token 值
     * @return 用户名
     */
    public static UserToken validateToken(String token) throws Exception {
        try {
            Verification verification = JWT.require(Algorithm.HMAC512(secret));
            JWTVerifier jwtVerifier = verification.build();
            // 去除 token 的前缀
            String noPrefixToken = token.replace(tokenPrefix, "");
            DecodedJWT decodedJwt = jwtVerifier.verify(noPrefixToken);
            return JSONObject.parseObject(decodedJwt.getSubject(), UserToken.class);
        } catch (Exception e){
            log.info("token过期："+e.getMessage());
            return new UserToken();
        }
    }

    /**
     * 验证 token
     * @param token 验证的 token 值
     * @return 用户名
     */
    public static ManagerToken validateManagerToken(String token) throws Exception {
        try {
            Verification verification = JWT.require(Algorithm.HMAC512(managerSecret));
            JWTVerifier jwtVerifier = verification.build();
            // 去除 token 的前缀
            String noPrefixToken = token.replace(tokenPrefix, "");
            DecodedJWT decodedJwt = jwtVerifier.verify(noPrefixToken);
            return JSONObject.parseObject(decodedJwt.getSubject(), ManagerToken.class);
        } catch (Exception e){
            log.info("token过期："+e.getMessage());
            return new ManagerToken();
        }

    }

    /**
     * 检查 token 是否需要更新
     * @param token token 值
     * @return 过期时间
     */
    public static boolean isNeedUpdate(String token) throws Exception {
        // 获取 token 过期时间
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getExpiresAt();
        } catch (TokenExpiredException e){
            return true;
        } catch (Exception e){
            throw new Exception(("token 验证失败"));
        }
        // 需要更新
        return (expiresAt.getTime() - System.currentTimeMillis()) < (expireTime >> 1);
    }


    /**
     * 检查 token 是否需要更新
     * @param token token 值
     * @return 过期时间
     */
    public static boolean isNeedUpdateManager(String token) throws Exception {
        // 获取 token 过期时间
        Date expiresAt = null;
        try {
            expiresAt = JWT.require(Algorithm.HMAC512(managerSecret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getExpiresAt();
        } catch (TokenExpiredException e){
            return true;
        } catch (Exception e){
            throw new Exception(("token 验证失败"));
        }
        // 需要更新
        return (expiresAt.getTime() - System.currentTimeMillis()) < (expireManagerTime >> 1);
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        if (token != null && token.startsWith(tokenPrefix)) {
            return token.replace(tokenPrefix, "");
        }
        return null;
    }
}