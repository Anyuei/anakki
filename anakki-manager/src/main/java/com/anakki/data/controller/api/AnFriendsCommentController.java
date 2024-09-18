package com.anakki.data.controller.api;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnFriendsComment;
import com.anakki.data.entity.request.CreateCommentsRequest;
import com.anakki.data.entity.request.LikeCommentsRequest;
import com.anakki.data.entity.request.ListCommentsRequest;
import com.anakki.data.entity.request.ReplyCommentRequest;
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
@RequestMapping("/api/anakki/friends-comment")
public class AnFriendsCommentController {

    @Autowired
    private AnFriendsCommentService anFriendsCommentService;
    @ApiOperation(value = "评论")
    @PostMapping("/create-comment")
    public ResponseDTO<Boolean> createComment(CreateCommentsRequest createCommentsRequest) {
        Boolean comment = anFriendsCommentService.createComment(createCommentsRequest);
        return ResponseDTO.succData(comment);
    }
    @ApiOperation(value = "回复评论")
    @PostMapping("/reply-comment")
    public ResponseDTO<Boolean> replyComment(@Valid @RequestBody ReplyCommentRequest request) {
        Boolean comment = anFriendsCommentService.replyComment(request);
        return ResponseDTO.succData(comment);
    }
    @ApiOperation(value = "喜欢评论")
    @PostMapping("/like")
    public ResponseDTO<Boolean> likeComment(
            @Valid @RequestBody LikeCommentsRequest request) {
        anFriendsCommentService.likeComment(request.getCommentId());
        return ResponseDTO.succData(true);
    }
}
