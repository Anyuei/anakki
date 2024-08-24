package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnSystemConfig;
import com.anakki.data.entity.request.CreateSystemConfigRequest;
import com.anakki.data.entity.request.ListManagerRequest;
import com.anakki.data.entity.request.ListSystemConfigRequest;
import com.anakki.data.entity.request.UpdateSystemConfigRequest;
import com.anakki.data.entity.response.ListManagerResponse;
import com.anakki.data.mapper.AnSystemConfigMapper;
import com.anakki.data.service.AnSystemConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/system-config")
public class AnSystemConfigController {

    @Autowired
    private AnSystemConfigService anSystemConfigService;
    @ApiOperation(value = "获取系统设置列表")
    @GetMapping("/list")
    public ResponseDTO<BasePageResult<AnSystemConfig>> listSystemConfig(ListSystemConfigRequest listSystemConfigRequest) {
        BasePageResult<AnSystemConfig> list = anSystemConfigService.listSystemConfig(listSystemConfigRequest);

        return ResponseDTO.succData(list);
    }

    @ApiOperation(value = "新增系统设置")
    @PostMapping("/add")
    public ResponseDTO<Boolean> saveSystemConfig(@RequestBody CreateSystemConfigRequest createSystemConfigRequest) {
        Boolean save = anSystemConfigService.saveSystemConfig(createSystemConfigRequest);

        return ResponseDTO.succData(save);
    }
    @ApiOperation(value = "修改系统设置")
    @PostMapping("/update")
    public ResponseDTO<Boolean> updateSystemConfig(@RequestBody UpdateSystemConfigRequest updateSystemConfigRequest) {
        Boolean update = anSystemConfigService.update(updateSystemConfigRequest);

        return ResponseDTO.succData(update);
    }

    @ApiOperation(value = "批量删除系统设置")
    @DeleteMapping("/delete-batch")
    public ResponseDTO<Boolean> deleteUpdateSystemConfig(@RequestBody IdListRequest idListRequest) {
        Boolean delete = anSystemConfigService.deleteSystemConfig(idListRequest);

        return ResponseDTO.succData(delete);
    }
}
