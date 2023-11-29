package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.ListUserResponse;
import com.anakki.data.entity.response.UserDetailResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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

    AnUser getByNickname(String nickname);

    BasePageResult<ListUserResponse> listUser(ListUserRequest listUserRequest);

    Boolean createUser(CreateUserRequest createUserRequest);

    Boolean updateUser(UpdateUserRequest updateUserRequest);

    UserDetailResponse detail();

    void uploadAvatar(MultipartFile file) throws IOException;

    Boolean sendSms(String telephone);


    Boolean verifyCode(String telephone, String code);

    Boolean telephoneChange(String telephone, String code);
}
