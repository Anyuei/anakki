package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
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
}
