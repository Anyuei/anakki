package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnFriendsComment;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.AnFriendsCommentResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
public interface AnFriendsCommentService extends IService<AnFriendsComment> {

    BasePageResult<AnFriendsCommentResponse> listComments(ListCommentsRequest listCommentsRequest);


    BasePageResult<AnFriendsComment> listCommentsManage(ListCommentsManageRequest listCommentsManageRequest);

    Boolean createComment(CreateCommentsRequest createCommentsRequest);

    void updateCommentState(UpdateCommentStateRequest updateCommentStateRequest);

    void operateBatch(List<Long> idList,String status);

    @Transactional(rollbackFor = Exception.class)
    void likeComment(Long commentId);

    Boolean replyComment(ReplyCommentRequest request);
}
