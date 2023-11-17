package com.anakki.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author 云胡
 * @date 2022/6/8
 * 
 * 注册自定义拦截器
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 registration 拦截器
        InterceptorRegistration registration = registry.addInterceptor(new UserLoginInterceptor());
        
        // 拦截所有的路径
        registration.addPathPatterns("/api/**");
        
        // 添加不拦截路径 /api/user/login 是登录的请求, /api/user/register 注册的请求
        registration.excludePathPatterns(
                "/base/**",
                // html 静态资源
                "/**/*.html",
                // js 静态资源
                "/**/*.js",
                // css 静态资源
                "/**/*.css"
        );


        InterceptorRegistration managerRegistration = registry.addInterceptor(new ManagerLoginInterceptor());
        // 拦截所有的路径
        managerRegistration.addPathPatterns("/manage/**");

        // 添加不拦截路径 /api/user/login 是登录的请求, /api/user/register 注册的请求
        managerRegistration.excludePathPatterns(
                "/base/**",
                // html 静态资源
                "/**/*.html",
                // js 静态资源
                "/**/*.js",
                // css 静态资源
                "/**/*.css"
        );
    }
}