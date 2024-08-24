package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnFriendsComment;
import com.anakki.data.entity.request.ListCommentsManageRequest;
import com.anakki.data.entity.request.ListCommentsRequest;
import com.anakki.data.entity.request.UpdateCommentStateRequest;
import com.anakki.data.service.AnFriendsCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@RestController
@RequestMapping("/manage/friends-comment")
public class AnFriendsCommentManageController {

    @Autowired
    private AnFriendsCommentService anFriendsCommentService;
    @ApiOperation(value = "获取评论")
    @GetMapping("/list-comments")
    public ResponseDTO<BasePageResult<AnFriendsComment>> listComments(ListCommentsManageRequest listCommentsManageRequest) {
        BasePageResult<AnFriendsComment> anFriendsCommentBasePageResult = anFriendsCommentService.listCommentsManage(listCommentsManageRequest);
        return ResponseDTO.succData(anFriendsCommentBasePageResult);
    }

    @ApiOperation(value = "修改评论状态")
    @PostMapping("/update-comments-state")
    public ResponseDTO<Boolean> updateCommentState(@Valid @RequestBody UpdateCommentStateRequest updateCommentStateRequest) {
        anFriendsCommentService.updateCommentState(updateCommentStateRequest);
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "删除评论")
    @DeleteMapping("/delete-batch")
    public ResponseDTO<Boolean> deleteCommentState(@Valid @RequestBody IdListRequest idListRequest) {
        anFriendsCommentService.removeByIds(idListRequest.getIdList());
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "通过评论")
    @PutMapping("/pass-batch")
    public ResponseDTO<Boolean> passCommentState(@Valid @RequestBody IdListRequest idListRequest) {
        anFriendsCommentService.operateBatch(idListRequest.getIdList(),"NORMAL");
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "关闭评论")
    @PutMapping("/close-batch")
    public ResponseDTO<Boolean> closeCommentState(@Valid @RequestBody IdListRequest idListRequest) {
        anFriendsCommentService.operateBatch(idListRequest.getIdList(),"CLOSED");
        return ResponseDTO.succData(true);
    }
}
