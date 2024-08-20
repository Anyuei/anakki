package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnChat;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.ReceiveFromRoomRequest;
import com.anakki.data.entity.request.ReceiveNewFromRoomRequest;
import com.anakki.data.entity.request.SendToRoomRequest;
import com.anakki.data.mapper.AnChatMapper;
import com.anakki.data.service.AnChatService;
import com.anakki.data.service.AnUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-08-18
 */
@Service
public class AnChatServiceImpl extends ServiceImpl<AnChatMapper, AnChat> implements AnChatService {

    @Autowired
    private AnUserService anUserService;
    @Override
    public Boolean sendToRoom(SendToRoomRequest sendToRoomRequest) {
        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = anUserService.getByNickname(currentNickname);
        AnChat anChat = new AnChat();
        anChat.setAvatar(user.getAvatar());
        anChat.setRoomId(sendToRoomRequest.getRoomId());
        anChat.setIsTemp(sendToRoomRequest.getIsTemp());
        anChat.setContent(sendToRoomRequest.getContent());
        anChat.setNickname(user.getNickname());
        anChat.setContentType(sendToRoomRequest.getContentType());
        anChat.setStatus("NORMAL");
        anChat.setIsEncrypt(false);
        anChat.setUserId(user.getId());
        return save(anChat);
    }

    @Override
    public List<AnChat> receiveFromRoom(ReceiveFromRoomRequest request) {
        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = anUserService.getByNickname(currentNickname);
        Long roomId = request.getRoomId();
        Long firstIndex = request.getFirstIndex();
        QueryWrapper<AnChat> anChatQueryWrapper = new QueryWrapper<>();
        anChatQueryWrapper.eq("room_id", roomId);
        anChatQueryWrapper.eq("status", "NORMAL");
        anChatQueryWrapper.lt(null!=firstIndex,"id",firstIndex);
        anChatQueryWrapper.orderByDesc("create_time");
        // 限制结果为前10条
        anChatQueryWrapper.last("LIMIT 30");
        return list(anChatQueryWrapper);
    }

    @Override
    public List<AnChat> receiveNewFromRoom(ReceiveNewFromRoomRequest request) {
        String currentNickname = BaseContext.getCurrentNickname();

        Long roomId = request.getRoomId();
        Long currentIndex = request.getCurrentIndex();

        QueryWrapper<AnChat> anChatQueryWrapper = new QueryWrapper<>();
        anChatQueryWrapper.eq("room_id", roomId);
        anChatQueryWrapper.gt(null!=currentIndex,"id",currentIndex);
        anChatQueryWrapper.eq("status", "NORMAL");

        if (null!=currentIndex){
            anChatQueryWrapper.orderByAsc("create_time");
            anChatQueryWrapper.last("LIMIT 60");
            return list(anChatQueryWrapper);
        }else{
            anChatQueryWrapper.orderByDesc("create_time");
            // 限制结果为前10条
            anChatQueryWrapper.last("LIMIT 30");
            List<AnChat> list = list(anChatQueryWrapper);
            Collections.reverse(list);
            return list;
        }

    }
}
