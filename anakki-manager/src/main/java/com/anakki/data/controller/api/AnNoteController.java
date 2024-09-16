package com.anakki.data.controller.api;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.request.*;
import com.anakki.data.service.AnNoteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@RestController
@RequestMapping("/api/anakki/note")
public class AnNoteController {

    @Autowired
    private AnNoteService anNoteService;

    @ApiOperation(value = "发布笔记")
    @PostMapping("/createNote")
    public ResponseDTO<Long> createNote(@RequestBody CreateNoteRequest createNoteRequest, HttpServletRequest request){
        Long noteId = anNoteService.save(createNoteRequest, request);
        return ResponseDTO.succData(noteId);
    }

    @ApiOperation(value = "修改笔记")
    @PostMapping("/updateNote")
    public ResponseDTO<Long> createNote(@RequestBody UpdateNoteRequest updateNoteRequest, HttpServletRequest request){
        Long id= anNoteService.update(updateNoteRequest, request);
        return ResponseDTO.succData(id);
    }

    @ApiOperation(value = "添加协作者到笔记")
    @PostMapping("/addAuthorToNotes")
    public ResponseDTO<Boolean> addAuthorToNotes(@Valid @RequestBody AddNoteOtherAuthorRequest createNoteRequest){
        anNoteService.addAuthorToNotes(createNoteRequest);
        return ResponseDTO.succData(true);
    }
    @ApiOperation(value = "移除协作者到笔记")
    @PostMapping("/removeAuthorToNotes")
    public ResponseDTO<Boolean> removeAuthorToNotes(@Valid @RequestBody RemoveNoteOtherAuthorRequest request){
        anNoteService.removeAuthorToNotes(request);
        return ResponseDTO.succData(true);
    }
    @ApiOperation(value = "查看笔记草稿")
    @GetMapping("/listDraftNote")
    public ResponseDTO<BasePageResult<AnNote>> listDraftNote(ListNoteRequest listNoteRequest){
        BasePageResult<AnNote> anNoteBasePageResult = anNoteService.listDraftNote(listNoteRequest);
        return ResponseDTO.succData(anNoteBasePageResult);
    }
    @ApiOperation(value = "删除笔记")
    @PostMapping("/deleteNote")
    public ResponseDTO<Boolean> deleteNote(@RequestBody @Valid IdNotNullRequest createNoteRequest, HttpServletRequest request){
        anNoteService.remove(createNoteRequest,request);
        return ResponseDTO.succData(true);
    }
    @ApiOperation(value = "上传媒体文件")
    @PostMapping("/uploadFiles")
    public ResponseDTO<String> uploadFiles(UploadNoteMediaRequest uploadNoteMediaRequest) throws IOException {

        String url = anNoteService.uploadFiles(uploadNoteMediaRequest);
        return ResponseDTO.succData(url);
    }

    @ApiOperation(value = "删除媒体文件")
    @PostMapping("/deleteFiles")
    public ResponseDTO<Boolean> deleteFiles(DeleteNoteMediaRequest deleteNoteMediaRequest) throws IOException {
        Boolean success = anNoteService.deleteFiles(deleteNoteMediaRequest);
        return ResponseDTO.succData(success);
    }
}
