package com.anakki.data.service.impl;

import com.anakki.data.entity.AnNoteUserPermission;
import com.anakki.data.mapper.AnNoteUserPermissionMapper;
import com.anakki.data.service.AnNoteUserPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-09-05
 */
@Service
public class AnNoteUserPermissionServiceImpl extends ServiceImpl<AnNoteUserPermissionMapper, AnNoteUserPermission> implements AnNoteUserPermissionService {

    @Override
    public List<AnNoteUserPermission> getUserPermissionsByNoteId(Long noteId,List<Long> userIds) {
        if (null==noteId){
            return new ArrayList<>();
        }
        QueryWrapper<AnNoteUserPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("note_id",noteId);
        queryWrapper.in("user_id",userIds);
        return list(queryWrapper);
    }
}
