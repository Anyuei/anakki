package com.anakki.data.controller.base;

import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.UserLoginRequest;
import com.anakki.data.entity.request.UserRegisterRequest;
import com.anakki.data.service.AnUserService;
import com.ramostear.captcha.HappyCaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/base/anakki/user")
public class AnBaseUserController {

    @Autowired
    private AnUserService anUserService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResponseDTO<String> login(UserLoginRequest userLoginRequest,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        boolean verification = HappyCaptcha.verification(request, userLoginRequest.getVerify(), true);
        if (!verification) {
            throw new RuntimeException("验证码错误");
        }
        String token = anUserService.login(userLoginRequest);
        return ResponseDTO.succData(token);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public ResponseDTO<Boolean> register(UserRegisterRequest userRegisterRequest, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        boolean verification = HappyCaptcha.verification(request, userRegisterRequest.getVerify(), true);
        if (!verification) {
            throw new RuntimeException("验证码错误");
        }
        return ResponseDTO.succData(anUserService.register(userRegisterRequest));
    }
}
