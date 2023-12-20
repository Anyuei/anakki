package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.An3dModel;
import com.anakki.data.entity.request.List3dModelRequest;
import com.anakki.data.entity.response.List3dModelResponse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2023-12-16
 */
public interface An3dModelService extends IService<An3dModel> {

    BasePageResult<List3dModelResponse> listAn3dModel(List3dModelRequest list3dModelRequest);

    String downloadModel(Long id);
}
