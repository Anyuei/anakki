package com.anakki.data.controller.manage;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.AnNoteGroup;
import com.anakki.data.entity.request.ListNoteForManageRequest;
import com.anakki.data.entity.request.ListNoteGroupForManageRequest;
import com.anakki.data.entity.request.SaveNoteGroupRequest;
import com.anakki.data.entity.request.UpdateNoteGroupRequest;
import com.anakki.data.service.AnNoteGroupService;
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
@RequestMapping("/manage/note")
public class AnNoteManageController {

    @Autowired
    private AnNoteService anNoteService;

    @ApiOperation(value = "查询笔记")
    @GetMapping("/list-notes-for-manage")
    public ResponseDTO<BasePageResult<AnNote>> listNotesForManage(@Valid ListNoteForManageRequest request) {
        BasePageResult<AnNote> anNoteGroups = anNoteService.listNotesForManage(request);
        return ResponseDTO.succData(anNoteGroups);
    }

//    @ApiOperation(value = "移除笔记分组")
//    @DeleteMapping("/delete-batch")
//    public ResponseDTO<Boolean> removeNoteGroup(@Valid @RequestBody IdListRequest request) {
//        anNoteService.removeNoteGroup(request);
//        return ResponseDTO.succData(true);
//    }
//
//    @ApiOperation(value = "修改笔记分组")
//    @PutMapping("/updateNote")
//    public ResponseDTO<Boolean> updateNoteGroup(@Valid @RequestBody UpdateNoteGroupRequest request) {
//        anNoteService.updateNoteGroup(request);
//        return ResponseDTO.succData(true);
//    }
}
