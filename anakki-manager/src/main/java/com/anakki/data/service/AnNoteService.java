package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.request.*;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
public interface AnNoteService extends IService<AnNote> {

    Boolean save(CreateNoteRequest createNoteRequest, HttpServletRequest request);

    BasePageResult<AnNote> listNotes(ListNoteRequest listNoteRequest);

    String uploadFiles(UploadNoteMediaRequest uploadNoteMediaRequest) throws IOException;

    Boolean deleteFiles(DeleteNoteMediaRequest deleteNoteMediaRequest) throws IOException;

    void remove(IdNotNullRequest createNoteRequest, HttpServletRequest request);

    BasePageResult<AnNote> listDraftNote(ListNoteRequest listNoteRequest);
}
