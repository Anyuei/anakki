package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnFriendsComment;
import com.anakki.data.entity.request.ListCommentsManageRequest;
import com.anakki.data.entity.request.ListCommentsRequest;
import com.baomidou.mybatisplus.extension.service.IService;

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

    BasePageResult<AnFriendsComment> listComments(ListCommentsRequest listCommentsRequest);


    BasePageResult<AnFriendsComment> listCommentsManage(ListCommentsManageRequest listCommentsManageRequest);
}
