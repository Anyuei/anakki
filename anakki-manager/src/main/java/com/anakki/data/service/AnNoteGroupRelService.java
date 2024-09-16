package com.anakki.data.service;

import com.anakki.data.entity.AnNoteGroupRel;
import com.anakki.data.entity.request.AddNoteToGroupManageRequest;
import com.anakki.data.entity.request.RemoveNoteFromGroupManageRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2024-09-14
 */
public interface AnNoteGroupRelService extends IService<AnNoteGroupRel> {

    void saveNoteToGroup(List<Long> noteGroupIds, Long noteId);

    @Transactional(rollbackFor = Exception.class)
    void removeNoteFromGroup(List<Long> noteGroupIds, Long noteId);

    void removeNoteFromGroup(RemoveNoteFromGroupManageRequest request);

    void addNoteToGroup(AddNoteToGroupManageRequest request);
}
