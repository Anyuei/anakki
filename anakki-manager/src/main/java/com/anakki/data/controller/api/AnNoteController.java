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
    public ResponseDTO<Boolean> createNote(@RequestBody CreateNoteRequest createNoteRequest, HttpServletRequest request){
        anNoteService.save(createNoteRequest,request);
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
