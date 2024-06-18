package com.anakki.data.controller.base;

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
@RequestMapping("/base/anakki/note")
public class AnBaseNoteController {

    @Autowired
    private AnNoteService anNoteService;


    @ApiOperation(value = "查看笔记列表")
    @GetMapping("/listNote")
    public ResponseDTO<BasePageResult<AnNote>> listNote(ListNoteRequest listNoteRequest){
        BasePageResult<AnNote> anNoteBasePageResult = anNoteService.listNotes(listNoteRequest);
        return ResponseDTO.succData(anNoteBasePageResult);
    }
    @ApiOperation(value = "查看笔记详情")
    @GetMapping("/note-detail")
    public ResponseDTO<AnNote> noteDetail(@RequestParam("id") Long id, HttpServletRequest request){
        AnNote note = anNoteService.getNoteDetail(id);
        return ResponseDTO.succData(note);
    }

    @ApiOperation(value = "点赞笔记")
    @PostMapping("/likeNote")
    public ResponseDTO<Boolean> likeNote(@RequestBody @Valid IdNotNullRequest createNoteRequest, HttpServletRequest request){
        anNoteService.likeNote(createNoteRequest.getId(),request);
        return ResponseDTO.succData(true);
    }
}
