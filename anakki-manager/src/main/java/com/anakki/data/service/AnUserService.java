package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.ListUserForNoteResponse;
import com.anakki.data.entity.response.ListUserResponse;
import com.anakki.data.entity.response.UserDetailResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
public interface AnUserService extends IService<AnUser> {

    String login(UserLoginRequest userLoginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException;


    Boolean register(UserRegisterRequest userRegisterRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    Boolean nicknameExist(String nickname);

    Boolean emailExist(String email);

    Boolean verifyEmail(String email, String verifyCode,String businessKey);

    AnUser getByNickname(String nickname);

    AnUser getByEmail(String email);

    BasePageResult<ListUserResponse> listUser(ListUserRequest listUserRequest);

    Boolean createUser(CreateUserRequest createUserRequest);

    Boolean updateUser(UpdateUserRequest updateUserRequest);

    UserDetailResponse detail();

    void uploadAvatar(UploadAvatarRequest uploadAvatarRequest) throws IOException;

    Boolean sendSms(String telephone);


    Boolean verifyCode(String telephone, String code);

    Boolean telephoneChange(String telephone, String code);

    BasePageResult<ListUserForNoteResponse> listForNote(ListUserForNoteRequest request);

    /**
     * 注册发生邮箱验证码
     * @param request
     * @return
     */
    Boolean registerEmailSend(UserRegisterVerifyRequest request);

    Boolean resetEmailSend(UserResetPasswordEmailVerifyRequest request);

    Boolean resetPassword(UserResetPasswordRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
