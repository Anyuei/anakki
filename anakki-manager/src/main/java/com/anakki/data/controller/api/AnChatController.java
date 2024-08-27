package com.anakki.data.controller.api;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnChat;
import com.anakki.data.entity.request.*;
import com.anakki.data.service.AnChatService;
import com.anakki.data.service.AnFriendsCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2024-08-18
 */
@RestController
@RequestMapping("/api/anakki/chat")
public class AnChatController {
    @Autowired
    private AnChatService anChatService;
    @ApiOperation(value = "发送消息到聊天室")
    @PostMapping("/send-to-room")
    public ResponseDTO<Boolean> sendToRoom(@RequestBody SendToRoomRequest sendToRoomRequest) {
        Boolean ok = anChatService.sendToRoom(sendToRoomRequest);
        return ResponseDTO.succData(ok);
    }

    @ApiOperation(value = "获取消息从聊天室")
    @PostMapping("/receive-from-room")
    public ResponseDTO<List<AnChat>> receiveFromRoom(@RequestBody ReceiveFromRoomRequest request) {
        List<AnChat> anChatList = anChatService.receiveFromRoom(request);
        return ResponseDTO.succData(anChatList);
    }

    @ApiOperation(value = "获取新消息从聊天室")
    @PostMapping("/receive-new-from-room")
    public ResponseDTO<List<AnChat>> receiveNewFromRoom(@RequestBody ReceiveNewFromRoomRequest request) {
        List<AnChat> anChatList = anChatService.receiveNewFromRoom(request);
        return ResponseDTO.succData(anChatList);
    }
    @ApiOperation(value = "设置用户聊天室")
    @PostMapping("/user-setting")
    public ResponseDTO<Boolean> userSetting(@RequestBody ChatRoomSettingRequest request) {
        Boolean save= anChatService.userSetting(request);
        return ResponseDTO.succData(save);
    }

    @ApiOperation(value = "设置用户邮箱提醒开关")
    @PostMapping("/user-setting-mail-notice")
    public ResponseDTO<Boolean> userSettingMailNotice(@RequestBody TurnOnOffRequest request) {
        Boolean save= anChatService.userSettingMailNotice(request);
        return ResponseDTO.succData(save);
    }
}
