package com.anakki.data.service;

import com.anakki.data.entity.AnNoteUserPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2024-09-05
 */
public interface AnNoteUserPermissionService extends IService<AnNoteUserPermission> {
    List<AnNoteUserPermission> getUserPermissionsByNoteId(Long noteId);
}
