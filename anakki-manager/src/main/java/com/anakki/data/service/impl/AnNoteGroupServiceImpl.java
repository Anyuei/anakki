package com.anakki.data.service.impl;

import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnNoteGroup;
import com.anakki.data.entity.AnNoteGroupRel;
import com.anakki.data.entity.request.SaveNoteGroupRequest;
import com.anakki.data.entity.request.UpdateNoteGroupRequest;
import com.anakki.data.entity.response.AnNoteGroupResponse;
import com.anakki.data.mapper.AnNoteGroupMapper;
import com.anakki.data.service.AnNoteGroupRelService;
import com.anakki.data.service.AnNoteGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AnNoteGroupRelService anNoteGroupRelService;

    @Override
    public void saveNoteGroup(SaveNoteGroupRequest saveNoteGroupRequest) {
        AnNoteGroup anNoteGroup = new AnNoteGroup();
        BeanUtils.copyProperties(saveNoteGroupRequest,anNoteGroup);
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
}
