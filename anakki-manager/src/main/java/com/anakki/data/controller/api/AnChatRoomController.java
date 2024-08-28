package com.anakki.data.controller.api;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnChat;
import com.anakki.data.entity.AnChatRoom;
import com.anakki.data.entity.request.ListChatRoomRequest;
import com.anakki.data.entity.request.ReceiveFromRoomRequest;
import com.anakki.data.service.AnChatRoomService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2024-08-28
 */
@RestController
@RequestMapping("/api/anakki/chat-room")
public class AnChatRoomController {
    @Autowired
    private AnChatRoomService anChatRoomService;
    @ApiOperation(value = "获取聊天室列表")
    @PostMapping("/list")
    public ResponseDTO<BasePageResult<AnChatRoom>> listRoom(@RequestBody ListChatRoomRequest request) {
        BasePageResult<AnChatRoom> anChatRoomList = anChatRoomService.listRoom(request);
        return ResponseDTO.succData(anChatRoomList);
    }

    @ApiOperation(value = "获取聊天室详情")
    @GetMapping("/info")
    public ResponseDTO<AnChatRoom> info(Long roomId) {
        if (null==roomId){
            throw new RuntimeException("请输入房间号");
        }
        AnChatRoom room = anChatRoomService.getById(roomId);
        return ResponseDTO.succData(room);
    }

}
