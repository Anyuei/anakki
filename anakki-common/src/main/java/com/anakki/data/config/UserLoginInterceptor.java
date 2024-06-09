package com.anakki.data.config;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.UserToken;
import com.anakki.data.utils.common.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: yunhu
 * @date: 2022/6/8
 *
 * 拦截器：验证用户是否登录
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 request 的 header 中获得 token 值
        String token = request.getHeader("authorization");
        if (token == null || token.isEmpty()) {

            return false;
        }
        // 验证 token, JwtUtil 是自己定义的类，里面有个方法验证 token 
        UserToken sub = JwtUtil.validateToken(token);
        if (sub == null || null==sub.getNickname()) {

            return false;
        }
        // 更新 token 有效时间 
        if (JwtUtil.isNeedUpdate(token)) {
            log.info("token更新");
            // 过期就创建新的 token 给前端
            String newToken = JwtUtil.createToken(sub);
            response.setHeader(JwtUtil.USER_LOGIN_TOKEN, newToken);
        }
        BaseContext.setCurrentNickname(sub.getNickname());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        BaseContext.removeCurrentNickname();
    }
}