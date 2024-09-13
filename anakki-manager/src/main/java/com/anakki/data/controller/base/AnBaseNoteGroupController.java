package com.anakki.data.controller.base;


import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.SaveNoteGroupRequest;
import com.anakki.data.entity.response.AnNoteGroupResponse;
import com.anakki.data.service.AnNoteGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2024-09-14
 */
@RestController
@RequestMapping("/base/anakki/an-note-group")
public class AnBaseNoteGroupController {

    @Autowired
    private AnNoteGroupService anNoteGroupService;
    @ApiOperation(value = "获取笔记分组")
    @GetMapping("/list-note-group")
    public ResponseDTO<List<AnNoteGroupResponse>> listNoteGroup() {
        return ResponseDTO.succData(anNoteGroupService.listNoteGroup());
    }
}
