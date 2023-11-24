package com.anakki.data.service;

import com.anakki.data.entity.AnSystemConfig;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-25
 */
public interface AnSystemConfigService extends IService<AnSystemConfig> {


    Long getNumberConfigValue(String key);
}
