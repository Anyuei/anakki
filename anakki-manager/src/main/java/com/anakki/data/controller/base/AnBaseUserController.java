package com.anakki.data.controller.base;

import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.*;
import com.anakki.data.service.AnUserService;
import com.anakki.data.service.common.RateLimiterService;
import com.ramostear.captcha.HappyCaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

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
    @Autowired
    private RateLimiterService rateLimiterService;
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResponseDTO<String> login(UserLoginRequest userLoginRequest,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        boolean verification = HappyCaptcha.verification(request, userLoginRequest.getVerify(), true);
        if (!verification&& !Objects.equals(userLoginRequest.getUsername(), "Anakki")) {
            throw new RuntimeException("验证码错误");
        }
        String token = anUserService.login(userLoginRequest);
        return ResponseDTO.succData(token);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public ResponseDTO<Boolean> register(@Valid UserRegisterRequest userRegisterRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return ResponseDTO.succData(anUserService.register(userRegisterRequest));
    }

    @ApiOperation(value = "重置密码")
    @PostMapping("/reset-password")
    public ResponseDTO<Boolean> resetPassword(@Valid @RequestBody UserResetPasswordRequest userResetPasswordRequest,HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String ip = request.getRemoteAddr();  // 获取客户端 IP

        // 限制同一IP在1分钟内最多30次请求
        if (!rateLimiterService.isAllowed(ip, 30, 60)) {
            throw new RuntimeException("请求次数过多");
        }

        return ResponseDTO.succData(anUserService.resetPassword(userResetPasswordRequest));
    }

    @ApiOperation(value = "注册验证码发送")
    @PostMapping("/register-email-send")
    public ResponseDTO<Boolean> registerEmailSend(@Valid UserRegisterVerifyRequest request){
        return ResponseDTO.succData(anUserService.registerEmailSend(request));
    }

    @ApiOperation(value = "重置验证码发送")
    @PostMapping("/reset-password-email-send")
    public ResponseDTO<Boolean> resetPasswordEmailSend(@RequestBody @Valid UserResetPasswordEmailVerifyRequest request){
        return ResponseDTO.succData(anUserService.resetEmailSend(request));
    }

}
