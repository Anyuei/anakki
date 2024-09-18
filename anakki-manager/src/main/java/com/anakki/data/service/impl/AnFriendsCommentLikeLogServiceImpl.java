package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.entity.AnFriendsCommentLikeLog;
import com.anakki.data.entity.AnUser;
import com.anakki.data.mapper.AnFriendsCommentLikeLogMapper;
import com.anakki.data.service.AnFriendsCommentLikeLogService;
import com.anakki.data.service.AnUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-09-19
 */
@Service
public class AnFriendsCommentLikeLogServiceImpl extends ServiceImpl<AnFriendsCommentLikeLogMapper, AnFriendsCommentLikeLog> implements AnFriendsCommentLikeLogService {


    @Autowired
    private AnUserService anUserService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public synchronized void insertCommentLikeLog(Long commentId){
        String currentNickname = BaseContext.getCurrentNickname(false);
        if (null!=currentNickname){
            try{
                AnUser likeUser = anUserService.getByNickname(currentNickname);
                AnFriendsCommentLikeLog anFriendsCommentLikeLog = new AnFriendsCommentLikeLog();
                anFriendsCommentLikeLog.setCommentId(commentId);
                anFriendsCommentLikeLog.setUserId(likeUser.getId());
                anFriendsCommentLikeLog.setAvatar(likeUser.getAvatar());
                anFriendsCommentLikeLog.setUserName(currentNickname);
                save(anFriendsCommentLikeLog);
            }catch (Exception e){
                log.error("操作失败:"+e.getMessage());
            }
        }
    }
}
