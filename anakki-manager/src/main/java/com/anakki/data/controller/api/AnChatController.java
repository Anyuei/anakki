package com.anakki.data.controller.api;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnChat;
import com.anakki.data.entity.request.CreateCommentsRequest;
import com.anakki.data.entity.request.ReceiveFromRoomRequest;
import com.anakki.data.entity.request.SendToRoomRequest;
import com.anakki.data.service.AnChatService;
import com.anakki.data.service.AnFriendsCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    public ResponseDTO<BasePageResult<AnChat>> receiveFromRoom(@RequestBody ReceiveFromRoomRequest request) {
        BasePageResult<AnChat> anChatBasePageResult = anChatService.receiveFromRoom(request);
        return ResponseDTO.succData(anChatBasePageResult);
    }

}
