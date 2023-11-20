package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.request.GetContentRequest;
import com.anakki.data.entity.request.ListRecordRequest;
import com.anakki.data.entity.request.UploadRecordRequest;
import com.anakki.data.service.AnRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-08
 */
@RestController
@RequestMapping("/manage/record")
public class AnRecordManageController {


    @Autowired
    private AnRecordService anRecordService;

    @ApiOperation(value = "获取图文列表")
    @GetMapping("/list")
    public ResponseDTO<BasePageResult<AnRecord>> list(ListRecordRequest listRecordRequest) {
        BasePageResult<AnRecord> anRecordBasePageResult = anRecordService.listRecordRequest(listRecordRequest);
        return ResponseDTO.succData(anRecordBasePageResult);
    }

    @ApiOperation(value = "上传图文")
    @PostMapping("/upload")
    public ResponseDTO<Boolean> upload(UploadRecordRequest uploadRecordRequest) {
        Boolean status=anRecordService.uploadRecord(uploadRecordRequest);
        return ResponseDTO.succData(status);
    }
}
