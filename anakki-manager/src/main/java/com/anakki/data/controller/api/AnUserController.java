package com.anakki.data.controller.api;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.UserDetailRequest;
import com.anakki.data.entity.request.UserLoginRequest;
import com.anakki.data.entity.request.UserRegisterRequest;
import com.anakki.data.entity.response.UserDetailResponse;
import com.anakki.data.service.AnUserService;
import com.anakki.data.utils.common.JwtUtil;
import com.ramostear.captcha.HappyCaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @ApiOperation(value = "上传头像")
    @PostMapping ("/upload-avatar")
    public ResponseDTO<Boolean> uploadAvatar(MultipartFile file) throws IOException {
        anUserService.uploadAvatar(file);
        return ResponseDTO.succData(true);
    }
}
