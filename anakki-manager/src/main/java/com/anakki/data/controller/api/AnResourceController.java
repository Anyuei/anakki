package com.anakki.data.controller.api;


import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.constant.CosBucketNameConst;
import com.anakki.data.entity.request.UploadResourceRequest;
import com.anakki.data.service.An3dModelService;
import com.anakki.data.service.AnResourceService;
import com.anakki.data.utils.common.COSUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
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
    public ResponseDTO<String> resourceDownloadUrl(String key) {
        String fileUrl = COSUtil.getObjectUrl(CosBucketNameConst.BUCKET_NAME_IMAGES,"resource/"+key);
        return ResponseDTO.succData(fileUrl);
    }


    @ApiOperation(value = "上传资源")
    @GetMapping("/upload")
    public ResponseDTO<String> resourceUpload(UploadResourceRequest uploadResourceRequest) throws IOException {
        anResourceService.upload(uploadResourceRequest);
        return ResponseDTO.succData("上传成功");
    }
}
