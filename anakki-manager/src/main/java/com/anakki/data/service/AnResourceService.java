package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnResource;
import com.anakki.data.entity.request.IdNotNullRequest;
import com.anakki.data.entity.request.ListResourceRequest;
import com.anakki.data.entity.request.RemoveResourceRequest;
import com.anakki.data.entity.request.UploadResourceRequest;
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

    Boolean removeResource(RemoveResourceRequest request);

    void openResource(IdNotNullRequest request);

    void closeResource(IdNotNullRequest request);
}
