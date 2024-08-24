package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnManager;
import com.anakki.data.entity.AnSystemConfig;
import com.anakki.data.entity.request.CreateSystemConfigRequest;
import com.anakki.data.entity.request.ListSystemConfigRequest;
import com.anakki.data.entity.response.ListManagerResponse;
import com.anakki.data.mapper.AnSystemConfigMapper;
import com.anakki.data.service.AnSystemConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public BasePageResult<AnSystemConfig> listSystemConfig(ListSystemConfigRequest listSystemConfigRequest) {
        IPage<AnSystemConfig> anSystemConfigIPage = new Page<>(
                listSystemConfigRequest.getCurrent(),
                listSystemConfigRequest.getSize());
        String type = listSystemConfigRequest.getType();
        QueryWrapper<AnSystemConfig> anSystemConfigQueryWrapper = new QueryWrapper<>();
        anSystemConfigQueryWrapper.eq(null != type, "type", type);
        IPage<AnSystemConfig> page = page(anSystemConfigIPage, anSystemConfigQueryWrapper);
        return new BasePageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public Boolean saveSystemConfig(CreateSystemConfigRequest createSystemConfigRequest) {

        AnSystemConfig anSystemConfig = new AnSystemConfig();
        BeanUtils.copyProperties(createSystemConfigRequest,anSystemConfig);
        save(anSystemConfig);
        return true;
    }

    @Override
    public Boolean deleteSystemConfig(IdListRequest idListRequest) {
        return removeByIds(idListRequest.getIdList());
    }


}
