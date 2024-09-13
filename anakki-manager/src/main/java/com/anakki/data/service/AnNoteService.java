package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.AnNoteDetailResponse;
import com.anakki.data.entity.response.AnNoteResponse;
import com.anakki.data.entity.response.AnUserDetailForNoteResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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

    Boolean addAuthorToNotes(AddNoteOtherAuthorRequest createNoteRequest);

    Boolean removeAuthorToNotes(RemoveNoteOtherAuthorRequest request);

    String getAuthorIdListString(List<Long> ids);

    Set<Long> getAuthorIdList(String ids);

    BasePageResult<AnNoteResponse> listNotes(ListNoteRequest listNoteRequest);

    String uploadFiles(UploadNoteMediaRequest uploadNoteMediaRequest) throws IOException;

    Boolean deleteFiles(DeleteNoteMediaRequest deleteNoteMediaRequest) throws IOException;

    void remove(IdNotNullRequest createNoteRequest, HttpServletRequest request);

    BasePageResult<AnNote> listDraftNote(ListNoteRequest listNoteRequest);

    AnNoteDetailResponse getNoteDetail(Long id);

    List<AnUserDetailForNoteResponse> getUserDetailForNote(Set<Long> authorIdList);

    void likeNote(Long id, HttpServletRequest request);
}
