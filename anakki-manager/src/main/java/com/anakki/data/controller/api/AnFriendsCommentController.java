package com.anakki.data.controller.api;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnFriendsComment;
import com.anakki.data.entity.request.CreateCommentsRequest;
import com.anakki.data.entity.request.ListCommentsRequest;
import com.anakki.data.service.AnFriendsCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        String currentNickname = BaseContext.getCurrentNickname();
        Boolean comment = anFriendsCommentService.createComment(currentNickname, createCommentsRequest);
        return ResponseDTO.succData(comment);
    }
}
