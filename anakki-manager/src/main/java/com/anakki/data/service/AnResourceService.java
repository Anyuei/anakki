package com.anakki.data.service;

import com.anakki.data.entity.AnResource;
import com.anakki.data.entity.request.UploadResourceRequest;
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

    void upload(UploadResourceRequest uploadResourceRequest) throws IOException;
}
