package com.anakki.data.service.impl;

import com.anakki.data.entity.AnChatRoomUser;
import com.anakki.data.mapper.AnChatRoomUserMapper;
import com.anakki.data.service.AnChatRoomUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-08-28
 */
@Service
public class AnChatRoomUserServiceImpl extends ServiceImpl<AnChatRoomUserMapper, AnChatRoomUser> implements AnChatRoomUserService {

    @Override
    public Boolean isRoomUser(Long userId,Long roomId){
        QueryWrapper<AnChatRoomUser> chatRoomUserQueryWrapper = new QueryWrapper<>();
        chatRoomUserQueryWrapper.eq("user_id",userId);
        chatRoomUserQueryWrapper.eq("room_id",roomId);
        AnChatRoomUser one = getOne(chatRoomUserQueryWrapper);
        return null!=one;
    }
}
