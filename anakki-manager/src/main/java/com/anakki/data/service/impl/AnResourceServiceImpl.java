package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.constant.CosBucketNameConst;
import com.anakki.data.bean.constant.CosPathConst;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.AnResource;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.UploadResourceRequest;
import com.anakki.data.mapper.AnResourceMapper;
import com.anakki.data.service.AnResourceService;
import com.anakki.data.service.AnUserService;
import com.anakki.data.utils.common.COSUtil;
import com.anakki.data.utils.common.JwtUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcloud.cos.auth.BasicSessionCredentials;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-03-14
 */
@Service
public class AnResourceServiceImpl extends ServiceImpl<AnResourceMapper, AnResource> implements AnResourceService {

    @Autowired
    AnUserService anUserService;
    @Override
    public void upload(UploadResourceRequest uploadResourceRequest) throws IOException {
        String currentNickname = BaseContext.getCurrentNickname(false);
        AnUser byNickname = anUserService.getByNickname(currentNickname);

        for (MultipartFile multipartFile : uploadResourceRequest.getFiles()) {
            AnResource anResource = new AnResource();
            BeanUtils.copyProperties(uploadResourceRequest, anResource);
            BasicSessionCredentials sessionCredential = COSUtil.getSessionCredential();

            String url = COSUtil.uploadObject(
                    multipartFile,
                    sessionCredential,
                    COSUtil.region,
                    CosBucketNameConst.BUCKET_NAME_IMAGES,
                    CosPathConst.BUCKET_NAME_RESOURCE,
                    true);
            anResource.setFileUrl(url);
            anResource.setFileSize(multipartFile.getSize() / 1024);
            save(anResource);
        }
    }
}
