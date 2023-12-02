package com.anakki.data.bean.constant;

import lombok.Data;

/**
 * ClassName: RedisKey
 * Description:
 *
 * @author Anakki
 * @date 2023/12/1 13:02
 */
@Data
public class RedisKey {
    //用户每日短信发送次数 redis计次key
    public static String USER_SMS_SEND_LIMIT_FOR_EVERY_24HOURS_COUNT_KEY = "USER_SMS_SEND_LIMIT_FOR_EVERY_24HOURS_COUNT_KEY_";
}
