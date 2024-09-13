package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnNoteGroup;
import com.anakki.data.entity.request.ListNoteGroupForManageRequest;
import com.anakki.data.entity.request.SaveNoteGroupRequest;
import com.anakki.data.entity.request.UpdateNoteGroupRequest;
import com.anakki.data.entity.response.AnNoteGroupResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2024-09-14
 */
public interface AnNoteGroupService extends IService<AnNoteGroup> {

    void saveNoteGroup(SaveNoteGroupRequest saveNoteGroupRequest);

    void removeNoteGroup(IdListRequest idListRequest);

    List<AnNoteGroupResponse> listNoteGroup();

    void updateNoteGroup(UpdateNoteGroupRequest request);

    BasePageResult<AnNoteGroup> listNoteGroup(ListNoteGroupForManageRequest request);
}
