package com.anakki.data.controller.manage;


import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.AddNoteToGroupManageRequest;
import com.anakki.data.entity.request.RemoveNoteFromGroupManageRequest;
import com.anakki.data.service.AnNoteGroupRelService;
import com.anakki.data.service.AnNoteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2024-09-14
 */
@RestController
@RequestMapping("/manage/note-group-rel")
public class AnNoteGroupRelManageController {

    @Autowired
    private AnNoteGroupRelService anNoteGroupRelService;

    @ApiOperation(value = "添加笔记到分组")
    @PostMapping("/add-note-to-group")
    public ResponseDTO<Boolean> addNoteToGroup(@Valid @RequestBody AddNoteToGroupManageRequest request) {
        anNoteGroupRelService.addNoteToGroup(request);
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "从分组移除笔记")
    @DeleteMapping("/remove-note-from-group")
    public ResponseDTO<Boolean> removeNoteFromGroup(@Valid @RequestBody RemoveNoteFromGroupManageRequest request) {
        anNoteGroupRelService.removeNoteFromGroup(request);
        return ResponseDTO.succData(true);
    }

}
