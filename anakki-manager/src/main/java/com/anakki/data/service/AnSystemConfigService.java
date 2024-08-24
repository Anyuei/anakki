package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnSystemConfig;

import com.anakki.data.entity.request.CreateSystemConfigRequest;
import com.anakki.data.entity.request.ListSystemConfigRequest;
import com.anakki.data.entity.request.UpdateSystemConfigRequest;
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

    String getStringConfigValue(String key);

    BasePageResult<AnSystemConfig> listSystemConfig(ListSystemConfigRequest listSystemConfigRequest);

    Boolean saveSystemConfig(CreateSystemConfigRequest createSystemConfigRequest);

    Boolean deleteSystemConfig(IdListRequest idListRequest);

    Boolean update(UpdateSystemConfigRequest updateSystemConfigRequest);
}
