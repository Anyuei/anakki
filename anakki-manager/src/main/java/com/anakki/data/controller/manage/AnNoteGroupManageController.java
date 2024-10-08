package com.anakki.data.controller.manage;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnNoteGroup;
import com.anakki.data.entity.request.ListNoteGroupForManageRequest;
import com.anakki.data.entity.request.SaveNoteGroupRequest;
import com.anakki.data.entity.request.UpdateNoteGroupRequest;
import com.anakki.data.service.AnNoteGroupService;
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
@RequestMapping("/manage/an-note-group")
public class AnNoteGroupManageController {

    @Autowired
    private AnNoteGroupService anNoteGroupService;
    @ApiOperation(value = "保存笔记分组")
    @PostMapping("/saveNoteGroup")
    public ResponseDTO<Boolean> saveNoteGroup(@Valid @RequestBody SaveNoteGroupRequest request) {
        anNoteGroupService.saveNoteGroup(request);
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "查询笔记分组")
    @GetMapping("/listNoteGroup")
    public ResponseDTO<BasePageResult<AnNoteGroup>> listNoteGroup(@Valid ListNoteGroupForManageRequest request) {
        BasePageResult<AnNoteGroup> anNoteGroups = anNoteGroupService.listNoteGroup(request);
        return ResponseDTO.succData(anNoteGroups);
    }

    @ApiOperation(value = "移除笔记分组")
    @DeleteMapping("/delete-batch")
    public ResponseDTO<Boolean> removeNoteGroup(@Valid @RequestBody IdListRequest request) {
        anNoteGroupService.removeNoteGroup(request);
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "修改笔记分组")
    @PutMapping("/updateNoteGroup")
    public ResponseDTO<Boolean> updateNoteGroup(@Valid @RequestBody UpdateNoteGroupRequest request) {
        anNoteGroupService.updateNoteGroup(request);
        return ResponseDTO.succData(true);
    }
}
