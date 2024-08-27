package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnChatRoom;
import com.anakki.data.entity.request.ListChatRoomRequest;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
