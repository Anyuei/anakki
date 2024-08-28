package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnChatRoom;
import com.anakki.data.entity.request.CreateChatRoomManageRequest;
import com.anakki.data.entity.request.ListChatRoomManageRequest;
import com.anakki.data.entity.request.ListChatRoomRequest;
import com.anakki.data.entity.request.ListUserForRoomManageRequest;
import com.anakki.data.entity.response.ListUserForRoomManageResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2024-08-28
 */
public interface AnChatRoomService extends IService<AnChatRoom> {

    BasePageResult<AnChatRoom> listRoom(ListChatRoomRequest request);

    BasePageResult<AnChatRoom> listRoomForManage(ListChatRoomManageRequest request);

    Boolean createRoom(CreateChatRoomManageRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    List<ListUserForRoomManageResponse> listUserForRoom(ListUserForRoomManageRequest request);
}
