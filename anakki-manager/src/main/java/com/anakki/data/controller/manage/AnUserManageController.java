package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.ListUserResponse;
import com.anakki.data.service.AnUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-15
 */
@RestController
@Api(value = "用户管理", tags = {"用户管理"})
@RequestMapping("/manage/user")
public class AnUserManageController {
    @Autowired
    private AnUserService anUserService;

    @ApiOperation(value = "获取用户列表")
    @GetMapping("/list")
    public ResponseDTO<BasePageResult<ListUserResponse>> listUser(ListUserRequest listUserRequest) {
        BasePageResult<ListUserResponse> list = anUserService.listUser(listUserRequest);

        return ResponseDTO.succData(list);
    }

    @ApiOperation(value = "创建用户")
    @PostMapping("/create")
    public ResponseDTO<Boolean> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseDTO.succData(anUserService.createUser(createUserRequest));
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/update")
    public ResponseDTO<Boolean> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseDTO.succData(anUserService.updateUser(updateUserRequest));
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/delete-batch")
    public ResponseDTO<Boolean> deleteUser(@RequestBody IdListRequest idListRequest) {
        return ResponseDTO.succData(anUserService.removeByIds(idListRequest.getIdList()));
    }
}
