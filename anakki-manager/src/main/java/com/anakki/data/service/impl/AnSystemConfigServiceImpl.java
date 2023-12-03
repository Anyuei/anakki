package com.anakki.data.service.impl;

import com.anakki.data.entity.AnSystemConfig;
import com.anakki.data.mapper.AnSystemConfigMapper;
import com.anakki.data.service.AnSystemConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-25
 */
@Service
public class AnSystemConfigServiceImpl extends ServiceImpl<AnSystemConfigMapper, AnSystemConfig> implements AnSystemConfigService {

    @Override
    public Long getNumberConfigValue(String key) {
        QueryWrapper<AnSystemConfig> systemConfigQueryWrapper = new QueryWrapper<>();
        systemConfigQueryWrapper.eq("config_key",key);
        systemConfigQueryWrapper.eq("type","user");
        AnSystemConfig one = getOne(systemConfigQueryWrapper);
        return Long.parseLong(one.getConfigValue());
    }

    @Override
    public String getStringConfigValue(String key) {
        QueryWrapper<AnSystemConfig> systemConfigQueryWrapper = new QueryWrapper<>();
        systemConfigQueryWrapper.eq("config_key",key);
        systemConfigQueryWrapper.eq("type","user");
        AnSystemConfig one = getOne(systemConfigQueryWrapper);
        return one.getConfigValue();
    }
}
