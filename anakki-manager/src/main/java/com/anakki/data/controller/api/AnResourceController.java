package com.anakki.data.controller.api;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.constant.CosBucketNameConst;
import com.anakki.data.entity.AnResource;
import com.anakki.data.entity.request.IdNotNullRequest;
import com.anakki.data.entity.request.ListResourceRequest;
import com.anakki.data.entity.request.RemoveResourceRequest;
import com.anakki.data.entity.request.UploadResourceRequest;
import com.anakki.data.entity.response.ListResourceResponse;
import com.anakki.data.service.An3dModelService;
import com.anakki.data.service.AnResourceService;
import com.anakki.data.utils.common.COSUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-12-16
 */
@RestController
@RequestMapping("/api/anakki/resource")
public class AnResourceController {

    @Autowired
    private AnResourceService anResourceService;

    @ApiOperation(value = "获取资源下载链接")
    @GetMapping("/download")
    public ResponseDTO<String> resourceDownloadUrl(String key, HttpServletResponse response) {
        AnResource byId = anResourceService.getById(key);
        String fileUrl = COSUtil.getObjectUrl(CosBucketNameConst.BUCKET_NAME_IMAGES, byId.getFileUrl());
        long count = byId.getDownloadCount() + 1;
        byId.setDownloadCount(count);
        anResourceService.updateById(byId);
        return ResponseDTO.succData(fileUrl);
    }

    @ApiOperation(value = "获取资源列表")
    @GetMapping("/list")
    public ResponseDTO<BasePageResult<ListResourceResponse>> listResource(ListResourceRequest listResourceRequest) {
        BasePageResult<ListResourceResponse> listResourceResponseBasePageResult = anResourceService.listResource(listResourceRequest);
        return ResponseDTO.succData(listResourceResponseBasePageResult);
    }

    @ApiOperation(value = "上传资源")
    @PostMapping("/upload")
    public ResponseDTO<String> resourceUpload(UploadResourceRequest uploadResourceRequest) throws IOException {
        anResourceService.upload(uploadResourceRequest);
        return ResponseDTO.succData("上传成功");
    }
    @ApiOperation(value = "删除资源")
    @PostMapping("/remove")
    public ResponseDTO<String> resourceRemove(@RequestBody RemoveResourceRequest request) {
        anResourceService.removeResource(request);
        return ResponseDTO.succData("删除成功");
    }
    @ApiOperation(value = "公开资源")
    @PostMapping("/open")
    public ResponseDTO<String> open(@RequestBody IdNotNullRequest request) {
        anResourceService.openResource(request);
        return ResponseDTO.succData("操作成功");
    }

    @ApiOperation(value = "仅自己可见资源")
    @PostMapping("/close")
    public ResponseDTO<String> close(@RequestBody IdNotNullRequest request) {
        anResourceService.closeResource(request);
        return ResponseDTO.succData("操作成功");
    }
}
