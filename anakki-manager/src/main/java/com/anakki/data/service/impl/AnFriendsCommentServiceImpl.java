package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnFriendsComment;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.request.ListCommentsRequest;
import com.anakki.data.mapper.AnFriendsCommentMapper;
import com.anakki.data.service.AnFriendsCommentService;
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
 * @since 2023-11-14
 */
@Service
public class AnFriendsCommentServiceImpl extends ServiceImpl<AnFriendsCommentMapper, AnFriendsComment> implements AnFriendsCommentService {


    @Override
    public BasePageResult<AnFriendsComment> listComments(ListCommentsRequest listCommentsRequest){
        IPage<AnFriendsComment>  anFriendsCommentPage= new Page<>(
                listCommentsRequest.getCurrent(),
                listCommentsRequest.getSize());
        String type = listCommentsRequest.getType();
        QueryWrapper<AnFriendsComment> anFriendsCommentQueryWrapper = new QueryWrapper<>();
        anFriendsCommentQueryWrapper.eq("type",type);
        anFriendsCommentQueryWrapper.ge(
                null!=listCommentsRequest.getCreateTimeStart(),"create_time",listCommentsRequest.getCreateTimeStart());
        anFriendsCommentQueryWrapper.le(
                null!=listCommentsRequest.getCreateTimeEnd(),"create_time",listCommentsRequest.getCreateTimeEnd());
        anFriendsCommentQueryWrapper.orderByDesc("create_time");
        IPage<AnFriendsComment> page = page(anFriendsCommentPage, anFriendsCommentQueryWrapper);
        List<AnFriendsComment> records = page.getRecords();
        return new BasePageResult<>(records, page.getTotal());
    }
}