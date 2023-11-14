package com.anakki.data.controller;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnFriendsComment;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.request.GetContentRequest;
import com.anakki.data.entity.request.ListCommentsRequest;
import com.anakki.data.service.AnFriendsCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @ApiOperation(value = "获取评论")
    @GetMapping("/list-comments")
    public ResponseDTO<BasePageResult<AnFriendsComment>> listComments(ListCommentsRequest listCommentsRequest) {
        BasePageResult<AnFriendsComment> anFriendsCommentBasePageResult = anFriendsCommentService.listComments(listCommentsRequest);
        return ResponseDTO.succData(anFriendsCommentBasePageResult);
    }
}
