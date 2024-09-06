package com.anakki.data.service.common;

import com.anakki.data.utils.common.RedisUtil;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RateLimiterService {


    public boolean isAllowed(String ip, int maxRequests, int windowInSeconds) {
        String key = "rate_limit:" + ip;
        long currentCount = RedisUtil.StringOps.incrBy(key,1);

        if (currentCount == 1) {
            // 设置过期时间为1分钟
            RedisUtil.KeyOps.expire(key, windowInSeconds, TimeUnit.SECONDS);
        }

        return currentCount <= maxRequests;
    }
}