package com.anakki.data.controller.api;

import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.UserDetailRequest;
import com.anakki.data.entity.request.UserLoginRequest;
import com.anakki.data.entity.request.UserRegisterRequest;
import com.anakki.data.entity.response.UserDetailResponse;
import com.anakki.data.service.AnUserService;
import com.ramostear.captcha.HappyCaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@RestController
@RequestMapping("/api/anakki/user")
public class AnUserController {

    @Autowired
    private AnUserService anUserService;

    @ApiOperation(value = "个人信息")
    @GetMapping("/detail")
    public ResponseDTO<UserDetailResponse> detail(){
        UserDetailResponse detail = anUserService.detail();
        return ResponseDTO.succData(detail);
    }
}
