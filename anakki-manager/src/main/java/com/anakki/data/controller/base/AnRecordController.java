package com.anakki.data.controller.base;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.request.GetContentRequest;
import com.anakki.data.service.AnRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-08
 */
@RestController
@RequestMapping("/base/anakki/record")
public class AnRecordController {


    @Autowired
    private AnRecordService anRecordService;

    @ApiOperation(value = "获取图文")
    @GetMapping("/flow")
    public ResponseDTO<BasePageResult<AnRecord>> flow(GetContentRequest getContentRequest) {
        return ResponseDTO.succData(anRecordService.flow(getContentRequest));
    }

    @ApiOperation(value = "根据id获取图文")
    @GetMapping("/flowById")
    public ResponseDTO<AnRecord> flow(Long id) {
        AnRecord byId = anRecordService.getById(id);
        if (null==byId){
            byId=new AnRecord();
            return ResponseDTO.succData(byId);
        }
        anRecordService.increaseViewCount(id);
        byId.setImgUrl(byId.getImgUrl().split("\\?")[0]);
        return ResponseDTO.succData(byId);
    }



}
