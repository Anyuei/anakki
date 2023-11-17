package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnManager;
import com.anakki.data.entity.AnMenu;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.request.*;
import com.anakki.data.service.AnManagerService;
import com.anakki.data.service.AnMenuService;
import com.anakki.data.utils.common.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-15
 */
@RestController
@Api(value = "管理员管理", tags = {"管理员管理"})
@RequestMapping("/manage/manager")
public class AnManagerController {
    @Autowired
    private AnManagerService anManagerService;

    @ApiOperation(value = "获取管理员列表")
    @GetMapping("/list")
    public ResponseDTO<BasePageResult<ListManagerResponse>> listManager(ListManagerRequest listManagerRequest) {
        BasePageResult<ListManagerResponse> list = anManagerService.listManager(listManagerRequest);

        return ResponseDTO.succData(list);
    }

    @ApiOperation(value = "创建管理员")
    @PostMapping("/create")
    public ResponseDTO<Boolean> createManager(@RequestBody CreateManagerRequest createManagerRequest) {
        return ResponseDTO.succData(anManagerService.createManager(createManagerRequest));
    }

    @ApiOperation(value = "修改管理员")
    @PostMapping("/update")
    public ResponseDTO<Boolean> updateManager(@RequestBody UpdateManagerRequest updateManagerRequest) {
        return ResponseDTO.succData(anManagerService.updateManager(updateManagerRequest));
    }
}
