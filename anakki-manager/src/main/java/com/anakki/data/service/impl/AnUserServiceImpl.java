package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnUser;
import com.anakki.data.bean.common.UserToken;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.ListUserResponse;
import com.anakki.data.entity.response.UserDetailResponse;
import com.anakki.data.mapper.AnUserMapper;
import com.anakki.data.service.AnUserService;
import com.anakki.data.utils.common.JwtUtil;
import com.anakki.data.utils.dealUtils.MD5SaltUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@Service
public class AnUserServiceImpl extends ServiceImpl<AnUserMapper, AnUser> implements AnUserService {

    @Autowired
    private AnUserMapper anUserMapper;


    @Override
    public String login(UserLoginRequest userLoginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        AnUser user = getByNickname(username);
        if (!MD5SaltUtil.validData(password,user.getPassword())) {
            throw new RuntimeException("用户不存在或密码错误");
        }
        UserToken userToken = new UserToken();
        userToken.setNickname(username);
        return JwtUtil.createToken(userToken);
    }

    @Override
    public Boolean register(UserRegisterRequest userRegisterRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        if (nicknameExist(username)) {
            throw new RuntimeException("昵称已存在");
        }
        String encryptedData = MD5SaltUtil.getEncryptedData(password);

        AnUser anUser = new AnUser();
        anUser.setExp(0L);
        anUser.setPassword(encryptedData);
        anUser.setLoginDays(0);
        anUser.setNickname(username);
        save(anUser);
        return true;
    }

    @Override
    public Boolean nicknameExist(String nickname) {
        QueryWrapper<AnUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("nickname", nickname);
        return 0 < anUserMapper.selectCount(userQueryWrapper);
    }
    @Override
    public AnUser getByNickname(String nickname) {
        QueryWrapper<AnUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("nickname", nickname);
        return anUserMapper.selectOne(userQueryWrapper);
    }
    @Override
    public BasePageResult<ListUserResponse> listUser(ListUserRequest listUserRequest) {
        String mail = listUserRequest.getMail();
        String nickname = listUserRequest.getNickname();
        IPage<AnUser> anManagerIPage = new Page<>(
                listUserRequest.getCurrent(),
                listUserRequest.getSize());
        QueryWrapper<AnUser> anRecordQueryWrapper = new QueryWrapper<>();
        anRecordQueryWrapper.like(null != mail, "mail", mail);
        anRecordQueryWrapper.like(null != nickname, "nickname", nickname);
        IPage<AnUser> page = page(anManagerIPage, anRecordQueryWrapper);
        List<AnUser> records = page.getRecords();
        List<ListUserResponse> listManagerResponses = com.anakki.data.utils.common.BeanUtils.copyBeanList(records, ListUserResponse.class);
        return new BasePageResult<>(listManagerResponses, page.getTotal());
    }

    @Override
    public Boolean createUser(CreateUserRequest createUserRequest) {

        AnUser anUser = new AnUser();
        BeanUtils.copyProperties(createUserRequest, anUser);
        return save(anUser);
    }

    @Override
    public Boolean updateUser(UpdateUserRequest updateUserRequest) {
        Long id = updateUserRequest.getId();
        AnUser anUser = getById(id);
        if (null == anUser) {
            throw new RuntimeException("管理员不存在");
        }
        QueryWrapper<AnUser> anUserQueryWrapper = new QueryWrapper<>();
        anUserQueryWrapper.eq(null != updateUserRequest.getNickname(), "nickname", updateUserRequest.getNickname());
        AnUser one = getOne(anUserQueryWrapper);
        if (null != one && !one.getId().equals(id)) {
            throw new RuntimeException("昵称已存在");
        }
        BeanUtils.copyProperties(updateUserRequest, anUser);
        return updateById(anUser);
    }

    @Override
    public UserDetailResponse detail(UserDetailRequest userLoginRequest) {
        String currentNickname = BaseContext.getCurrentNickname();
        AnUser byNickname = getByNickname(currentNickname);
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        BeanUtils.copyProperties(byNickname,userDetailResponse);
        return userDetailResponse;
    }
}
