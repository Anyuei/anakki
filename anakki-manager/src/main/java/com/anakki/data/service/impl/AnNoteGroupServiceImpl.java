package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnNoteGroup;
import com.anakki.data.entity.AnNoteGroupRel;
import com.anakki.data.entity.request.ListNoteGroupForManageRequest;
import com.anakki.data.entity.request.SaveNoteGroupRequest;
import com.anakki.data.entity.request.UpdateNoteGroupRequest;
import com.anakki.data.entity.response.AnNoteGroupResponse;
import com.anakki.data.mapper.AnNoteGroupMapper;
import com.anakki.data.service.AnNoteGroupRelService;
import com.anakki.data.service.AnNoteGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-09-14
 */
@Service
public class AnNoteGroupServiceImpl extends ServiceImpl<AnNoteGroupMapper, AnNoteGroup> implements AnNoteGroupService {

    @Autowired
    @Lazy
    private AnNoteGroupRelService anNoteGroupRelService;

    @Override
    public void saveNoteGroup(SaveNoteGroupRequest saveNoteGroupRequest) {
        AnNoteGroup anNoteGroup = new AnNoteGroup();
        BeanUtils.copyProperties(saveNoteGroupRequest,anNoteGroup);
        if (null!=saveNoteGroupRequest.getFiles()&&saveNoteGroupRequest.getFiles().length>0){
            anNoteGroup.setCoverImage(saveNoteGroupRequest.getFiles()[0].getUrl());
        }
        save(anNoteGroup);
    }

    @Override
    public void removeNoteGroup(IdListRequest idListRequest) {
        List<Long> idList = idListRequest.getIdList();

        QueryWrapper<AnNoteGroupRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("note_group_id",idList);
        anNoteGroupRelService.remove(queryWrapper);

        removeByIds(idListRequest.getIdList());
    }

    @Override
    public List<AnNoteGroupResponse> listNoteGroup() {

        QueryWrapper<AnNoteGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","COMMON");
        List<AnNoteGroup> result = list(queryWrapper);

        return com.anakki.data.utils.common.BeanUtils.copyBeanList(result, AnNoteGroupResponse.class);
    }

    @Override
    public void updateNoteGroup(UpdateNoteGroupRequest request) {
        AnNoteGroup anNoteGroup = new AnNoteGroup();
        BeanUtils.copyProperties(request,anNoteGroup);
        updateById(anNoteGroup);
    }

    @Override
    public void existAndIncrNoteCount(Long noteGroupId) {
        if (null!=noteGroupId){
            AnNoteGroup anNoteGroup = getById(noteGroupId);
            if (null!=anNoteGroup){
                Integer noteCount = anNoteGroup.getNoteCount();
                anNoteGroup.setNoteCount(noteCount+1);
                updateById(anNoteGroup);
            }
        }
    }
    @Override
    public void existAndDecrNoteCount(Long noteGroupId) {
        if (null!=noteGroupId){
            AnNoteGroup anNoteGroup = getById(noteGroupId);
            if (null!=anNoteGroup){
                Integer noteCount = anNoteGroup.getNoteCount();
                int i = noteCount - 1;
                anNoteGroup.setNoteCount(Math.max(i, 0));
                updateById(anNoteGroup);
            }
        }
    }
    @Override
    public BasePageResult<AnNoteGroup> listNoteGroup(ListNoteGroupForManageRequest request) {
        // 创建查询条件
        QueryWrapper<AnNoteGroup> queryWrapper = new QueryWrapper<>();

        // 根据请求参数构建查询条件
        if (request.getGroupName() != null && !request.getGroupName().isEmpty()) {
            queryWrapper.like("group_name", request.getGroupName());
        }
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            queryWrapper.like("description", request.getDescription());
        }
        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            queryWrapper.eq("status", request.getStatus());
        }
        if (request.getScopeAccess() != null) {
            queryWrapper.eq("scope_access", request.getScopeAccess());
        }

        // 排序字段
        if (request.getOrderBy() != null) {
            queryWrapper.orderByDesc(request.getOrderBy());
        }else{
            queryWrapper.orderByDesc("create_time");
        }

        // 分页查询
        Page<AnNoteGroup> page = new Page<>(request.getCurrent(), request.getSize());
        Page<AnNoteGroup> resultPage = this.page(page, queryWrapper);

        return new BasePageResult<>(resultPage.getRecords(),resultPage.getTotal());
    }
}
