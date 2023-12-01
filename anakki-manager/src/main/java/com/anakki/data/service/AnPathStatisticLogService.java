package com.anakki.data.service;

import com.anakki.data.entity.AnPathStatisticLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2023-12-01
 */
public interface AnPathStatisticLogService extends IService<AnPathStatisticLog> {
    Boolean log(String path,String ipAddr);
}
