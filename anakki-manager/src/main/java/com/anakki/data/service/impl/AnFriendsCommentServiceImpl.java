package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnFriendsComment;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.AnFriendsCommentResponse;
import com.anakki.data.mapper.AnFriendsCommentMapper;
import com.anakki.data.service.AnFriendsCommentService;
import com.anakki.data.service.AnUserService;
import com.anakki.data.utils.common.EmailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@Service
public class AnFriendsCommentServiceImpl extends ServiceImpl<AnFriendsCommentMapper, AnFriendsComment> implements AnFriendsCommentService {

    @Autowired
    private AnUserService anUserService;


    @Autowired
    private EmailUtil emailUtil;
    @Override
    public BasePageResult<AnFriendsCommentResponse> listComments(ListCommentsRequest listCommentsRequest) {
        IPage<AnFriendsComment> anFriendsCommentPage = new Page<>(
                listCommentsRequest.getCurrent(),
                listCommentsRequest.getSize());
        String type = listCommentsRequest.getType();
        QueryWrapper<AnFriendsComment> anFriendsCommentQueryWrapper = new QueryWrapper<>();
        anFriendsCommentQueryWrapper.eq("type", type);
        anFriendsCommentQueryWrapper.eq("status", "NORMAL");
        anFriendsCommentQueryWrapper.ge(
                null != listCommentsRequest.getCreateTimeStart(), "create_time", listCommentsRequest.getCreateTimeStart());
        anFriendsCommentQueryWrapper.le(
                null != listCommentsRequest.getCreateTimeEnd(), "create_time", listCommentsRequest.getCreateTimeEnd());
        anFriendsCommentQueryWrapper.orderByDesc("create_time");
        IPage<AnFriendsComment> page = page(anFriendsCommentPage, anFriendsCommentQueryWrapper);
        List<AnFriendsComment> records = page.getRecords();
        List<AnFriendsCommentResponse> responses = new ArrayList<>();
        for (AnFriendsComment record : records) {
            AnFriendsCommentResponse anFriendsCommentResponse = new AnFriendsCommentResponse();
            BeanUtils.copyProperties(record, anFriendsCommentResponse);
            Long userId = record.getUserId();
            if (null != userId) {
                AnUser user = anUserService.getById(userId);
                if (null != user) {
                    Long exp = user.getExp();
                    anFriendsCommentResponse.setExp(exp);
                    anFriendsCommentResponse.setAvatar(user.getAvatar());
                    anFriendsCommentResponse.setNicknameColor(user.getNicknameColor());
                }
            }
            responses.add(anFriendsCommentResponse);
        }
        return new BasePageResult<>(responses, page.getTotal());
    }


    @Override
    public BasePageResult<AnFriendsComment> listCommentsManage(ListCommentsManageRequest listCommentsManageRequest) {
        IPage<AnFriendsComment> anFriendsCommentPage = new Page<>(
                listCommentsManageRequest.getCurrent(),
                listCommentsManageRequest.getSize());
        String type = listCommentsManageRequest.getType();
        QueryWrapper<AnFriendsComment> anFriendsCommentQueryWrapper = new QueryWrapper<>();
        anFriendsCommentQueryWrapper.eq(null != type, "type", type);
        anFriendsCommentQueryWrapper.ge(
                null != listCommentsManageRequest.getCreateTimeStart(), "create_time", listCommentsManageRequest.getCreateTimeStart());
        anFriendsCommentQueryWrapper.le(
                null != listCommentsManageRequest.getCreateTimeEnd(), "create_time", listCommentsManageRequest.getCreateTimeEnd());
        anFriendsCommentQueryWrapper.orderByDesc("create_time");
        IPage<AnFriendsComment> page = page(anFriendsCommentPage, anFriendsCommentQueryWrapper);
        List<AnFriendsComment> records = page.getRecords();
        return new BasePageResult<>(records, page.getTotal());
    }

    @Override
    public Boolean createComment(CreateCommentsRequest createCommentsRequest) {
        AnFriendsComment anFriendsComment = new AnFriendsComment();
        if (createCommentsRequest.getIsAnonymous()) {
            anFriendsComment.setNickname("匿名用户");
            anFriendsComment.setComment(createCommentsRequest.getComment());
            anFriendsComment.setStatus("IN_REVIEW");
        } else {
            String currentNickname = BaseContext.getCurrentNickname();
            AnUser byNickname = anUserService.getByNickname(currentNickname);
            BeanUtils.copyProperties(createCommentsRequest, anFriendsComment);
            anFriendsComment.setAvatar(byNickname.getAvatar());
            anFriendsComment.setNickname(currentNickname);
            anFriendsComment.setUserId(byNickname.getId());
            anFriendsComment.setUserName(byNickname.getUserName());
            anFriendsComment.setStatus("IN_REVIEW");
        }
        emailUtil.sendMessage("ayp199645aabb@qq.com","您收到一条留言!",
                "内容:"+createCommentsRequest.getComment()
        );


        return save(anFriendsComment);
    }

    @Override
    public void updateCommentState(UpdateCommentStateRequest updateCommentStateRequest) {
        String state = updateCommentStateRequest.getStatus();

        updateCommentStateRequest.getCommentIdList().forEach(commentId -> {
            AnFriendsComment byId = getById(commentId);
            byId.setStatus(state);
            updateById(byId);
        });

    }

    @Override
    public void operateBatch(List<Long> ids,String status) {
        List<AnFriendsComment> anFriendsComments = listByIds(ids);

        anFriendsComments.forEach(anFriendsComment -> {
            anFriendsComment.setStatus(status);
        });
        updateBatchById(anFriendsComments);
    }

}