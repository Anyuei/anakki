package com.anakki.data.service;

import com.anakki.data.entity.AnStatistic;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-29
 */
public interface AnStatisticService extends IService<AnStatistic> {


    Boolean increaseByName(String name,String ipAddr);
}
