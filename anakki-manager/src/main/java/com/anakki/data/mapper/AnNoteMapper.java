package com.anakki.data.mapper;

import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.request.ListNoteForManageRequest;
import com.anakki.data.entity.request.ListNoteRequest;
import com.anakki.data.entity.response.AnNoteResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
@Mapper
public interface AnNoteMapper extends BaseMapper<AnNote> {

   void incrNoteViewCount(Long id);

    void likeNote(Long id);

    IPage<AnNoteResponse> listNotes(IPage<AnNoteResponse> page, @Param("listNoteRequest") ListNoteRequest listNoteRequest);

    IPage<AnNote> listNotesForManage(IPage<AnNote> page, ListNoteForManageRequest listNoteRequest);
}
