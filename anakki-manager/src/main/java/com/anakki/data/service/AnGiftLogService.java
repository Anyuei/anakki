package com.anakki.data.service;

import com.anakki.data.bean.common.RandomList;
import com.anakki.data.entity.AnGiftLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2024-04-15
 */
public interface AnGiftLogService extends IService<AnGiftLog> {

    boolean  insertRandomNames(
            List<String> names,
            LocalDateTime openTime,
            String gift
    );

    List<AnGiftLog> getRandomNames();
}
