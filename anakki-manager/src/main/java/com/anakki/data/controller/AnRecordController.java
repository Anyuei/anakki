package com.anakki.data.controller;

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
@RequestMapping("/api/anakki/record")
public class AnRecordController {


    @Autowired
    private AnRecordService anRecordService;

    @ApiOperation(value = "获取图文")
    @GetMapping("/flow")
    public ResponseDTO<BasePageResult<AnRecord>> flow(GetContentRequest getContentRequest) {
        IPage<AnRecord> anRecordIPage = new Page<>(
                getContentRequest.getCurrent(),
                getContentRequest.getSize());
        String type = getContentRequest.getImageType();
        QueryWrapper<AnRecord> anRecordQueryWrapper = new QueryWrapper<>();
        anRecordQueryWrapper.eq("type",type);
        IPage<AnRecord> page = anRecordService.page(anRecordIPage, anRecordQueryWrapper);
        List<AnRecord> records = page.getRecords();
        return ResponseDTO.succData(new BasePageResult<>(records, page.getTotal()));
    }

    @ApiOperation(value = "根据id获取图文")
    @GetMapping("/flowById")
    public ResponseDTO<AnRecord> flow(Long id) {
        AnRecord byId = anRecordService.getById(id);
        return ResponseDTO.succData(byId);
    }
}
