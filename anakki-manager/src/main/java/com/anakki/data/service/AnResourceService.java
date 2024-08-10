package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnResource;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.ListResourceManageResponse;
import com.anakki.data.entity.response.ListResourceResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2024-03-14
 */
public interface AnResourceService extends IService<AnResource> {

    void removeTemporaryFiles();

    void upload(UploadResourceRequest uploadResourceRequest) throws IOException;

    BasePageResult<ListResourceResponse> listResource(ListResourceRequest listResourceRequest);

    BasePageResult<ListResourceManageResponse> listResource(ListResourceManageRequest listResourceManageRequest);

    Boolean removeResource(RemoveResourceRequest request);

    Boolean removeResourcesByManage(IdListRequest request);

    Boolean disableResourcesByManage(IdListRequest request);

    Boolean enableResourcesByManage(IdListRequest request);

    void openResource(IdNotNullRequest request);

    void closeResource(IdNotNullRequest request);
}
