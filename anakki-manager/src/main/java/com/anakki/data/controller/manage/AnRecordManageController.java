package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.request.*;
import com.anakki.data.service.AnRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drew.imaging.ImageProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDTO<Boolean> upload(@RequestBody UploadRecordForManageRequest uploadRecordRequest) throws IOException, ImageProcessingException {
        Boolean status=anRecordService.uploadRecordForManage(uploadRecordRequest);
        return ResponseDTO.succData(status);
    }

    @ApiOperation(value = "修改图文状态")
    @PostMapping("/change-status")
    public ResponseDTO<Boolean> changeRecordStatus(@RequestBody ChangeRecordRequest changeRecordRequest) {
        anRecordService.changeRecordStatus(changeRecordRequest);
        return ResponseDTO.succData(true);
    }
    @ApiOperation(value = "删除图文状态")
    @DeleteMapping("/delete-batch")
    public ResponseDTO<Boolean> deleteRecord(@RequestBody IdListRequest idListRequest) {
        anRecordService.removeByIds(idListRequest.getIdList());
        return ResponseDTO.succData(true);
    }
}
