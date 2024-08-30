package com.anakki.data.controller.api;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdNotNullRequest;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.request.AvatarImgListRequest;
import com.anakki.data.entity.request.GetContentRequest;
import com.anakki.data.entity.response.AvatarImgListResponse;
import com.anakki.data.service.AnRecordService;
import com.anakki.data.utils.IPUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-08
 */
@RestController
@RequestMapping("/api/anakki/record")
public class AnUserRecordController {


    @Autowired
    private AnRecordService anRecordService;

    @ApiOperation(value = "点赞")
    @GetMapping("/like")
    public ResponseDTO<Object> like(@RequestBody IdNotNullRequest id, HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);
        Object like = anRecordService.userOperate(id.getId(), ipAddr, "LIKE");
        return ResponseDTO.succData(like);
    }

    @ApiOperation(value = "不喜欢")
    @GetMapping("/unLike")
    public ResponseDTO<Object> unLike(@RequestBody IdNotNullRequest id,HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);
        Object unlike = anRecordService.userOperate(id.getId(), ipAddr, "UNLIKE");
        return ResponseDTO.succData(unlike);
    }

    @ApiOperation(value = "获取头像列表")
    @GetMapping("/list-avatars")
    public ResponseDTO<BasePageResult<AvatarImgListResponse>> listAvatars(AvatarImgListRequest avatarImgListRequest) {
        BasePageResult<AvatarImgListResponse> pages = anRecordService.listAvatars(avatarImgListRequest);
        return ResponseDTO.succData(pages);
    }

}
