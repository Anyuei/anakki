package com.anakki.data.service;

import com.anakki.data.entity.AnFriendsCommentLikeLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2024-09-19
 */
public interface AnFriendsCommentLikeLogService extends IService<AnFriendsCommentLikeLog> {


    @Transactional(rollbackFor = RuntimeException.class)
    void insertCommentLikeLog(Long commentId);
}
