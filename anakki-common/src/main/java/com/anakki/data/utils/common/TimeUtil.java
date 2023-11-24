package com.anakki.data.utils.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

/**
 * ClassName: TimeUtil
 * Description:
 *
 * @author Anakki
 * @date 2023/11/25 3:41
 */
public class TimeUtil {
    public static Long getMinutesOfTodayLeft(){
        // 获取当前日期
        LocalDate localDate = LocalDate.now();
        // 获取今晚12点的时间
        LocalDateTime midnight = LocalDateTime.of(localDate, LocalTime.MAX);
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 计算当前时间到今晚12点的分钟数
        return now.until(midnight, ChronoUnit.MINUTES);
    }
}
