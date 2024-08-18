package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnChat;
import com.anakki.data.entity.request.ReceiveFromRoomRequest;
import com.anakki.data.entity.request.SendToRoomRequest;
import com.baomidou.mybatisplus.extension.service.IService;

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

    BasePageResult<AnChat> receiveFromRoom(ReceiveFromRoomRequest request);
}
