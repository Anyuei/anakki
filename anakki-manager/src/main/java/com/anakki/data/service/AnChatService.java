package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnChat;
import com.anakki.data.entity.request.ReceiveFromRoomRequest;
import com.anakki.data.entity.request.ReceiveNewFromRoomRequest;
import com.anakki.data.entity.request.SendToRoomRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2024-08-18
 */
public interface AnChatService extends IService<AnChat> {

    Boolean sendToRoom(SendToRoomRequest sendToRoomRequest);

    List<AnChat> receiveFromRoom(ReceiveFromRoomRequest request);

    List<AnChat> receiveNewFromRoom(ReceiveNewFromRoomRequest request);
}
