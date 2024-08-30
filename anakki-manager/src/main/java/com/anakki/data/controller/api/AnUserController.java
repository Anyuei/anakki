package com.anakki.data.controller.api;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.*;
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
    @PostMapping("/upload-avatar")
    public ResponseDTO<Boolean> uploadAvatar(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "selectedAvatar", required = false) String selectedAvatar,
            @RequestParam(value = "id", required = false) Long id) throws IOException {

        UploadAvatarRequest uploadAvatarRequest = new UploadAvatarRequest();
        uploadAvatarRequest.setFile(file);
        uploadAvatarRequest.setSelectedAvatar(selectedAvatar);
        uploadAvatarRequest.setId(id);
        anUserService.uploadAvatar(uploadAvatarRequest);
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "手机号验证")
    @PostMapping("/sendSms")
    public ResponseDTO<String> sendSms(@RequestBody TelephoneVerifyRequest telephoneVerifyRequest){
        return ResponseDTO.succData(anUserService.sendSms(telephoneVerifyRequest.getTelephone())?"发送成功":"发送失败");
    }
    @ApiOperation(value = "手机号验证并设置")
    @PostMapping("/telephone-change")
    public ResponseDTO<String> telephoneChange(@RequestBody CodeVerifyRequest codeVerifyRequest){
        anUserService.telephoneChange(codeVerifyRequest.getTelephone(),codeVerifyRequest.getCode());
        return ResponseDTO.succData("验证成功");
    }
}
