package com.anakki.data.controller.api;


import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.SaveNoteDraftRequest;
import com.anakki.data.service.AnNoteDraftService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
@RestController
@RequestMapping("/api/anakki/an-note-draft")
public class AnNoteDraftController {

    @Autowired
    private AnNoteDraftService anNoteDraftService;


    @ApiOperation(value = "保存笔记草稿")
    @PostMapping("/saveNoteDraft")
    public ResponseDTO<Long> saveNoteDraft(@RequestBody SaveNoteDraftRequest saveNoteDraftRequest, HttpServletRequest request){
        Long noteId = anNoteDraftService.saveDraft(saveNoteDraftRequest, request);
        return ResponseDTO.succData(noteId);
    }
}
