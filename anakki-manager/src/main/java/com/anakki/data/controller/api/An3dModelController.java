package com.anakki.data.controller.api;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.List3dModelRequest;
import com.anakki.data.entity.request.ListCommentsRequest;
import com.anakki.data.entity.request.RandomNameRequest;
import com.anakki.data.entity.response.AnFriendsCommentResponse;
import com.anakki.data.entity.response.List3dModelResponse;
import com.anakki.data.service.An3dModelService;
import com.anakki.data.service.AnGiftLogService;
import com.anakki.data.service.impl.An3dModelServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-12-16
 */
@RestController
@RequestMapping("/api/anakki/3dModel")
public class An3dModelController {

    @Autowired
    private An3dModelService an3dModelService;

    @Autowired
    private AnGiftLogService anGiftLogService;
    @ApiOperation(value = "获取3d模型下载链接")
    @GetMapping("/download")
    public ResponseDTO<String> downloadUrl(Long id) {
        String fileUrl = an3dModelService.downloadModel(id);
        return ResponseDTO.succData(fileUrl);
    }

    @ApiOperation(value = "随机名字")
    @PostMapping("/setRandomName")
    public ResponseDTO<String> setRandomName(@RequestBody RandomNameRequest request) {
        anGiftLogService.insertRandomNames(request.getNames(),request.getOpenTime(),request.getGift());
        return ResponseDTO.succ();
    }
}
