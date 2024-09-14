package com.anakki.data.service.impl;

import com.anakki.data.entity.AnNoteGroupRel;
import com.anakki.data.mapper.AnNoteGroupRelMapper;
import com.anakki.data.service.AnNoteGroupRelService;
import com.anakki.data.service.AnNoteGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class AnNoteGroupRelServiceImpl extends ServiceImpl<AnNoteGroupRelMapper, AnNoteGroupRel> implements AnNoteGroupRelService {


    @Autowired
    private AnNoteGroupService anNoteGroupService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNoteToGroup(List<Long> noteGroupIds, Long noteId) {
        List<AnNoteGroupRel> batchAddRels=new ArrayList<>();
        for (Long noteGroupId : noteGroupIds) {
            anNoteGroupService.existAndIncrNoteCount(noteGroupId);
            AnNoteGroupRel anNoteGroupRel = new AnNoteGroupRel();
            anNoteGroupRel.setNoteGroupId(noteGroupId);
            anNoteGroupRel.setNoteId(noteId);
            batchAddRels.add(anNoteGroupRel);
        }
        saveBatch(batchAddRels);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeNoteFromGroup(List<Long> noteGroupIds, Long noteId) {
        for (Long noteGroupId : noteGroupIds) {
            anNoteGroupService.existAndDecrNoteCount(noteGroupId);
        }
        QueryWrapper<AnNoteGroupRel> relQueryWrapper = new QueryWrapper<>();
        relQueryWrapper.eq("note_id",noteId);
        relQueryWrapper.in("note_group_id",noteGroupIds);
        remove(relQueryWrapper);
    }
}
