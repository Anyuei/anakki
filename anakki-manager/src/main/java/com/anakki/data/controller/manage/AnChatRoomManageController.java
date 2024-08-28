package com.anakki.data.controller.manage;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnChatRoom;
import com.anakki.data.entity.request.CreateChatRoomManageRequest;
import com.anakki.data.entity.request.ListChatRoomManageRequest;
import com.anakki.data.entity.request.ListChatRoomRequest;
import com.anakki.data.entity.request.ListUserForRoomManageRequest;
import com.anakki.data.entity.response.ListUserForRoomManageResponse;
import com.anakki.data.service.AnChatRoomService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
@RequestMapping("/manage/chat-room")
public class AnChatRoomManageController {
    @Autowired
    private AnChatRoomService anChatRoomService;
    @ApiOperation(value = "获取聊天室列表")
    @GetMapping("/list")
    public ResponseDTO<BasePageResult<AnChatRoom>> listRoom(ListChatRoomManageRequest request) {
        BasePageResult<AnChatRoom> anChatRoomList = anChatRoomService.listRoomForManage(request);
        return ResponseDTO.succData(anChatRoomList);
    }

    @ApiOperation(value = "创建聊天室")
    @PostMapping("/create")
    public ResponseDTO<Boolean> createRoom(@Valid @RequestBody CreateChatRoomManageRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Boolean save = anChatRoomService.createRoom(request);
        return ResponseDTO.succData(save);
    }


    @ApiOperation(value = "聊天室设置房主-用户搜索")
    @GetMapping("/list-user-for-room")
    public ResponseDTO<List<ListUserForRoomManageResponse>>  listUserForRoom(@Valid ListUserForRoomManageRequest request){
        return ResponseDTO.succData(anChatRoomService.listUserForRoom(request));
    }

}
