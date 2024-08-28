package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnChat;
import com.anakki.data.entity.AnChatRoom;
import com.anakki.data.entity.AnChatRoomUser;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.*;
import com.anakki.data.mapper.AnChatMapper;
import com.anakki.data.service.*;
import com.anakki.data.utils.common.EmailUtil;
import com.anakki.data.utils.common.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private EmailUtil emailUtil;
    @Autowired
    private AnUserService anUserService;
    @Autowired
    private AnChatRoomService anChatRoomService;
    @Autowired
    private  AnChatRoomUserService anChatRoomUserService;

    @Autowired
    private AnSystemConfigService anSystemConfigService;
    @Override
    public Boolean sendToRoom(SendToRoomRequest sendToRoomRequest) {
        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = anUserService.getByNickname(currentNickname);
        Long userId = user.getId();
        Long roomId = sendToRoomRequest.getRoomId();
        AnChatRoom chatRoom = anChatRoomService.getById(roomId);
        if (null==chatRoom){
            throw new RuntimeException("聊天室不存在");
        }
        if (!anChatRoomUserService.isRoomUser(userId,roomId)) {
            throw new RuntimeException("请先进入聊天室");
        }

        AnChat anChat = new AnChat();
        anChat.setAvatar(user.getAvatar());
        anChat.setRoomId(roomId);
        anChat.setIsTemp(sendToRoomRequest.getIsTemp());
        anChat.setContent(sendToRoomRequest.getContent());
        anChat.setNickname(user.getNickname());
        anChat.setContentType(sendToRoomRequest.getContentType());
        anChat.setStatus("NORMAL");
        anChat.setIsEncrypt(false);
        anChat.setUserId(user.getId());
        boolean save = save(anChat);
        if (save){
            sendEmailToUsers(roomId,user.getId(),sendToRoomRequest.getContent());
        }

        return true;
    }

    @Async
    public void sendEmailToUsers(Long roomId,Long senderUserId,final String content){
        Long chatroomNoticeIntervalTime = anSystemConfigService.getNumberConfigValue("CHATROOM_NOTICE_INTERVAL_TIME");
        String currentNickname = BaseContext.getCurrentNickname();
        QueryWrapper<AnChatRoomUser> anChatRoomUserQueryWrapper = new QueryWrapper<>();
        anChatRoomUserQueryWrapper.eq("room_id",roomId);
        anChatRoomUserQueryWrapper.ne("user_id",senderUserId);
        List<AnChatRoomUser> roomUserList = anChatRoomUserService.list(anChatRoomUserQueryWrapper);

        roomUserList.forEach(roomUser->{
            AnUser roomUserInfo = anUserService.getById(roomUser.getUserId());
            if (null!=roomUserInfo&&!roomUserInfo.getState().equals("ban")&&roomUserInfo.getIsChatroomMailNotice()){
                if (null!=roomUserInfo.getMail()){
                    boolean noticeEnable = RedisUtil
                            .StringOps
                            .setIfAbsent(
                                    "UserChatroomNotice_"+roomId+"_"+roomUser.getId(),
                                    roomUserInfo.getMail(),
                                    chatroomNoticeIntervalTime,
                                    TimeUnit.MINUTES);
                    if (noticeEnable){
                        String sendMsg=content;
                        if (content.length()>30){
                            sendMsg=content.substring(0,30);
                        }
                        emailUtil.sendMessage(
                                roomUserInfo.getMail(),
                                "【ANAKKI-X】您有一条新的消息来自聊天室:"+roomId,
                                "发送人:"+currentNickname+"\n" +
                                        "发送内容:"+sendMsg+"\n"+
                                        "本信息"+chatroomNoticeIntervalTime+"分钟内不再提示\n"
                        );
                    }
                }
            }
        });
    }

    @Override
    public List<AnChat> receiveFromRoom(ReceiveFromRoomRequest request) {
        String currentNickname = BaseContext.getCurrentNickname();
        Long roomId = request.getRoomId();
        AnUser byNickname = anUserService.getByNickname(currentNickname);
        if (null==byNickname){
            throw new RuntimeException("用户不存在！");
        }

        if (!anChatRoomUserService.isRoomUser(roomId,byNickname.getId())) {
            throw new RuntimeException("请先进入聊天室");
        }


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
        AnUser byNickname = anUserService.getByNickname(currentNickname);
        if (null==byNickname){
            throw new RuntimeException("用户不存在！");
        }
        Long roomId = request.getRoomId();

        if (!anChatRoomUserService.isRoomUser(byNickname.getId(),roomId)) {
            throw new RuntimeException("请先进入聊天室");
        }

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

    @Override
    public Boolean userSetting(ChatRoomSettingRequest request) {
        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = anUserService.getByNickname(currentNickname);
        if (!StringUtils.isEmpty(request.getEmail())){
            user.setMail(request.getEmail());
        }
        return anUserService.updateById(user);
    }

    @Override
    public Boolean userSettingMailNotice(TurnOnOffRequest request) {
        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = anUserService.getByNickname(currentNickname);
        user.setIsChatroomMailNotice(request.getStatus());
        return anUserService.updateById(user);
    }
}
