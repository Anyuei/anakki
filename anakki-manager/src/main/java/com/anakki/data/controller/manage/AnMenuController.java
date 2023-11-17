package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnMenu;
import com.anakki.data.entity.request.GetContentRequest;
import com.anakki.data.service.AnMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-15
 */
@RestController
@Api(value = "菜单管理", tags = {"菜单管理"})
@RequestMapping("/manage/menu")
public class AnMenuController {

    @Autowired
    private AnMenuService anMenuService;
    @ApiOperation(value = "获取菜单")
    @GetMapping("/list")
    public ResponseDTO<List<AnMenu>> listMenus() {
        QueryWrapper<AnMenu> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.orderByAsc("sort");
        List<AnMenu> list = anMenuService.list(objectQueryWrapper);
        return ResponseDTO.succData(list);
    }
}
