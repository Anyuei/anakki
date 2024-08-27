package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnChatRoom;
import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.ListChatRoomRequest;
import com.anakki.data.mapper.AnChatRoomMapper;
import com.anakki.data.service.AnChatRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-08-28
 */
@Service
public class AnChatRoomServiceImpl extends ServiceImpl<AnChatRoomMapper, AnChatRoom> implements AnChatRoomService {


    @Override
    public BasePageResult<AnChatRoom> listRoom(ListChatRoomRequest request) {

        String currentNickname = BaseContext.getCurrentNickname(false);

        IPage<AnChatRoom> anChatRoomIPage = new Page<>(
                request.getCurrent(),
                request.getSize());
        QueryWrapper<AnChatRoom> anChatRoomQueryWrapper = new QueryWrapper<>();
        anChatRoomQueryWrapper.ge(
                null != request.getCreateTimeStart(), "create_time", request.getCreateTimeStart());
        anChatRoomQueryWrapper.le(
                null != request.getCreateTimeEnd(), "create_time", request.getCreateTimeEnd());
        anChatRoomQueryWrapper.orderByDesc("create_time");
        IPage<AnChatRoom> page = page(anChatRoomIPage, anChatRoomQueryWrapper);
        List<AnChatRoom> records = page.getRecords();
        return new BasePageResult<>(records, page.getTotal());
    }
}
