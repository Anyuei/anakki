package com.anakki.data.controller.manage.base;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.ListManagerResponse;
import com.anakki.data.service.AnManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
@RequestMapping("/base/anakki/manager")
public class AnBaseManagerController {
    @Autowired
    private AnManagerService anManagerService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResponseDTO<String> managerLogin(@RequestBody ManagerLoginRequest managerLoginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String token = anManagerService.login(managerLoginRequest);
        return ResponseDTO.succData(token);
    }



}
