package com.anakki.data.aspect;

import com.anakki.data.service.AnPathStatisticLogService;
import com.anakki.data.service.AnStatisticService;
import com.anakki.data.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
public class CustomizationAop  {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AnPathStatisticLogService anPathStatisticLogService;


    @Pointcut("execution(* com.anakki.data.controller..*.*(..))")
    public void apiEndpoint() {
    }

    @Before("apiEndpoint()")
    public void logBeforeAPIAccess(JoinPoint joinPoint) {
        String ipAddr = IPUtils.getIpAddr(request);
        String requestURI = request.getRequestURI();
        anPathStatisticLogService.log(requestURI,ipAddr,null);
    }

    @AfterReturning(pointcut = "apiEndpoint()", returning = "result")
    public void logAfterAPIAccess(JoinPoint joinPoint, Object result) {
    }
}