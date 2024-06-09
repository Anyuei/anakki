package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.constant.CosBucketNameConst;
import com.anakki.data.bean.constant.CosPathConst;
import com.anakki.data.entity.AnResource;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.IdNotNullRequest;
import com.anakki.data.entity.request.ListResourceRequest;
import com.anakki.data.entity.request.RemoveResourceRequest;
import com.anakki.data.entity.request.UploadResourceRequest;
import com.anakki.data.entity.response.ListResourceResponse;
import com.anakki.data.mapper.AnResourceMapper;
import com.anakki.data.service.AnResourceService;
import com.anakki.data.service.AnUserService;
import com.anakki.data.utils.common.COSUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcloud.cos.auth.BasicSessionCredentials;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-03-14
 */
@Service
@Slf4j
public class AnResourceServiceImpl extends ServiceImpl<AnResourceMapper, AnResource> implements AnResourceService {

    @Autowired
    private AnUserService anUserService;

    @Override
    @Scheduled(cron = "0 * * * * *") // 每分钟的0秒执行一次
    public void removeTemporaryFiles() {
        QueryWrapper<AnResource> resourceQueryWrapper = new QueryWrapper<>();
        resourceQueryWrapper.ne("status", "DELETE");
        resourceQueryWrapper.le(
                "expiration_date",
                LocalDateTime.now());
        List<AnResource> list = list(resourceQueryWrapper);
        list.forEach(anResource -> {
            String fileUrl = anResource.getFileUrl();
            COSUtil.deleteObject(CosBucketNameConst.BUCKET_NAME_IMAGES, fileUrl);
            log.info("文件已定时删除：" + fileUrl);
            anResource.setStatus("DELETE");
            updateById(anResource);
        });
    }

    @Override
    public void upload(UploadResourceRequest uploadResourceRequest) throws IOException {
        String currentNickname = BaseContext.getCurrentNickname(false);
        AnUser byNickname = anUserService.getByNickname(currentNickname);
        if (null == uploadResourceRequest.getFiles()) {
            throw new RuntimeException("未选择文件");
        }
        //文件大小判空
        for (MultipartFile multipartFile : uploadResourceRequest.getFiles()) {
            long size = multipartFile.getSize();
            if (size > 1024 * 1024 * 1024) {
                throw new RuntimeException("文件不能超过1GB");
            }
        }
        for (MultipartFile multipartFile : uploadResourceRequest.getFiles()) {
            AnResource anResource = new AnResource();
            BeanUtils.copyProperties(uploadResourceRequest, anResource);
            BasicSessionCredentials sessionCredential = COSUtil.getSessionCredential();

            String key = COSUtil.uploadResource(
                    multipartFile,
                    sessionCredential,
                    COSUtil.region,
                    CosBucketNameConst.BUCKET_NAME_IMAGES,
                    CosPathConst.BUCKET_NAME_RESOURCE);
            if (StringUtils.isEmpty(uploadResourceRequest.getTitle())) {
                anResource.setTitle(multipartFile.getOriginalFilename());
            }
            if (StringUtils.isEmpty(uploadResourceRequest.getDescription())) {
                anResource.setDescription(multipartFile.getOriginalFilename());
            }
            if (uploadResourceRequest.getIsTemporary()) {
                anResource.setExpirationDate(uploadResourceRequest.getAvailableTime());
            }
            anResource.setFileUrl(key);
            anResource.setFileSize(multipartFile.getSize() / 1024);
            anResource.setUploadUser(currentNickname);
            anResource.setResourceName(multipartFile.getOriginalFilename());
            anResource.setUploadUserId(byNickname.getId());
            anResource.setType(multipartFile.getContentType());
            anResource.setIsPublic(uploadResourceRequest.getIsPublic());
            save(anResource);
        }
    }

    @Override
    public BasePageResult<ListResourceResponse> listResource(ListResourceRequest listResourceRequest) {
        String currentNickname = BaseContext.getCurrentNickname(false);
        AnUser user = anUserService.getByNickname(currentNickname);

        String type = listResourceRequest.getType();
        String description = listResourceRequest.getDescription();
        String title = listResourceRequest.getTitle();
        Boolean isPublic = listResourceRequest.getIsPublic();
        IPage<AnResource> resourceIPage = new Page<>(
                listResourceRequest.getCurrent(),
                listResourceRequest.getSize());
        QueryWrapper<AnResource> anRecordQueryWrapper = new QueryWrapper<>();
        anRecordQueryWrapper.like(null != type, "type", type);
        anRecordQueryWrapper.like(null != description, "description", description);
        anRecordQueryWrapper.like(null != title, "title", title);
        anRecordQueryWrapper.eq("status", "COMMON");
        anRecordQueryWrapper.eq(isPublic,"is_public", true);

        if(null == user&&!isPublic){
            throw new RuntimeException("请登录");
        }

        if(null != user&&!isPublic){
            anRecordQueryWrapper.eq("upload_user_id", user.getId());
        }
        anRecordQueryWrapper.orderByDesc("create_time");
        IPage<AnResource> page = page(resourceIPage, anRecordQueryWrapper);
        List<ListResourceResponse> listResourceResponses
                = com.anakki.data.utils.common.BeanUtils.copyBeanList(page.getRecords(), ListResourceResponse.class);
        return new BasePageResult<>(listResourceResponses, page.getTotal());
    }

    @Override
    public Boolean removeResource(RemoveResourceRequest request) {
        String currentNickname = BaseContext.getCurrentNickname(false);
        Long id = request.getId();
        AnUser user = anUserService.getByNickname(currentNickname);
        AnResource resource = getById(id);
        if (!user.getIsSuper() && !user.getId().equals(resource.getUploadUserId())) {
            throw new RuntimeException("没有权限！用户只能删除自己上传的文件");
        }
        COSUtil.deleteObject(CosBucketNameConst.BUCKET_NAME_IMAGES, resource.getFileUrl());
        return removeById(id);
    }

    @Override
    public void openResource(IdNotNullRequest request) {
        String currentNickname = BaseContext.getCurrentNickname(false);
        Long id = request.getId();
        AnUser user = anUserService.getByNickname(currentNickname);
        AnResource resource = getById(id);
        if (!user.getIsSuper() && !user.getId().equals(resource.getUploadUserId())) {
            throw new RuntimeException("没有权限！用户只能删除自己上传的文件");
        }
        resource.setIsPublic(true);
        updateById(resource);
    }

    @Override
    public void closeResource(IdNotNullRequest request) {
        String currentNickname = BaseContext.getCurrentNickname(false);
        Long id = request.getId();
        AnUser user = anUserService.getByNickname(currentNickname);
        AnResource resource = getById(id);
        if (!user.getIsSuper() && !user.getId().equals(resource.getUploadUserId())) {
            throw new RuntimeException("没有权限！用户只能操作自己上传的文件");
        }
        resource.setIsPublic(false);
        updateById(resource);
    }

    public static void main(String[] args) {
        LinkedHashMap<Object, Object> objectObjectLinkedHashMap = new LinkedHashMap<>();
    }
}
