package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.constant.CosBucketNameConst;
import com.anakki.data.bean.constant.CosPathConst;
import com.anakki.data.bean.constant.RedisKey;
import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.AnNoteUserPermission;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.common.ExpKeyConst;
import com.anakki.data.entity.AnUser;
import com.anakki.data.bean.common.UserToken;
import com.anakki.data.entity.common.SystemConfigConst;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.ListUserForNoteResponse;
import com.anakki.data.entity.response.ListUserResponse;
import com.anakki.data.entity.response.NoteUserPermissionResponse;
import com.anakki.data.entity.response.UserDetailResponse;
import com.anakki.data.mapper.AnUserMapper;
import com.anakki.data.service.*;
import com.anakki.data.utils.common.*;
import com.anakki.data.utils.dealUtils.MD5SaltUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    @Autowired
    @Lazy
    private AnRecordService anRecordService;
    @Autowired
    @Lazy
    private AnNoteService anNoteService;

    @Autowired
    private  AnNoteUserPermissionService anNoteUserPermissionService;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private AnSystemConfigService  anSystemConfigService;
    @Override
    public String login(UserLoginRequest userLoginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        AnUser user = getByNickname(username);
        if (null == user || !MD5SaltUtil.validData(password, user.getPassword())) {
            throw new RuntimeException("用户不存在或密码错误");
        }
        if (!RedisUtil.KeyOps.hasKey(ExpKeyConst.EXP_LOGIN + "#anakki#" + user.getId())) {
            RedisUtil.StringOps.setIfAbsent(ExpKeyConst.EXP_LOGIN + "#anakki#" + user.getId(), "true", TimeUtil.getMinutesOfTodayLeft(), TimeUnit.MINUTES);
            user.setExp(user.getExp() + ExpKeyConst.EXP_CONFIG_MAP.get(ExpKeyConst.EXP_LOGIN));
            user.setLoginDays(user.getLoginDays() + 1);
        }
        updateById(user);
        UserToken userToken = new UserToken();
        userToken.setNickname(username);
        return JwtUtil.createToken(userToken);
    }
    @Override
    public Boolean register(UserRegisterRequest userRegisterRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String email = userRegisterRequest.getEmail();
        String verify = userRegisterRequest.getVerify();
        if (nicknameExist(username)) {
            throw new RuntimeException("昵称已存在");
        }
        if (emailExist(email)){
            throw new RuntimeException("邮箱已注册");
        }

        if (!verifyEmail(email,verify)) {
            throw new RuntimeException("验证码不正确");
        }

        String encryptedData = MD5SaltUtil.getEncryptedData(password);

        AnUser anUser = new AnUser();
        anUser.setExp(0L);
        anUser.setPassword(encryptedData);
        anUser.setLoginDays(0);
        anUser.setNickname(username);
        anUser.setMail(email);
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
    public Boolean emailExist(String email) {
        QueryWrapper<AnUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("mail", email);
        return 0 < anUserMapper.selectCount(userQueryWrapper);
    }
    @Override
    public Boolean verifyEmail(String email, String verifyCode) {
        if (StringUtils.isBlank(email)){
            throw new RuntimeException("请输入邮箱");
        }
        if (StringUtils.isBlank(verifyCode)){
            throw new RuntimeException("请输入验证码");
        }
        String realVerifyCode = RedisUtil.StringOps.get("EmailVerify_" + email);
        if (StringUtils.isBlank(realVerifyCode)){
            throw new RuntimeException("验证码已失效");
        }
        return verifyCode.equals(realVerifyCode);
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
        anUser.setExp(0L);
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
    public UserDetailResponse detail() {
        String currentNickname = BaseContext.getCurrentNickname();
        AnUser byNickname = getByNickname(currentNickname);
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        BeanUtils.copyProperties(byNickname, userDetailResponse);
        return userDetailResponse;
    }

    @Override
    public void uploadAvatar(UploadAvatarRequest uploadAvatarRequest) throws IOException {
        String currentNickname = BaseContext.getCurrentNickname();
        AnUser byNickname = getByNickname(currentNickname);
        String selectedAvatar = uploadAvatarRequest.getSelectedAvatar();
        Long id = uploadAvatarRequest.getId();
        MultipartFile file = uploadAvatarRequest.getFile();

        if (StringUtils.isNotBlank(selectedAvatar)) {

            AnRecord newAvatarImg = anRecordService.getById(id);
            if (null==newAvatarImg){
                throw new RuntimeException("找不到此头像");
            }
            Long avatarUserId = newAvatarImg.getAvatarUserId();
            if (null!=avatarUserId&&avatarUserId!=-1){
                if (!avatarUserId.equals(byNickname.getId())){
                    throw new RuntimeException("头像已经被其他用户使用");
                }else{
                    throw new RuntimeException("您正在使用此头像");
                }
            }
            newAvatarImg.setAvatarUserId(byNickname.getId());
            //移除旧占位，如果有的话
            anRecordService.removeByAvatarUserId(byNickname.getId());
            //占有新的头像
            anRecordService.updateById(newAvatarImg);

            byNickname.setAvatar(selectedAvatar);
        }else{
            long sizeInBytes = file.getSize();
            long sizeInMB = sizeInBytes / (1024 * 1024);
            if (sizeInMB > 2) {
                throw new RuntimeException("头像不能大于2MB");
            }

            String url = COSUtil.uploadImage(
                    file,
                    COSUtil.region,
                    CosBucketNameConst.BUCKET_NAME_IMAGES, CosPathConst.BUCKET_NAME_AVATAR,
                    false
            );
            byNickname.setAvatar(url);
        }

        updateById(byNickname);
    }

    @Override
    public Boolean sendSms(String telephone) {
        String currentNickname = BaseContext.getCurrentNickname();
        Random random = new Random();
        // 生成6位随机数字
        String randomNumber = random.nextInt(900000) + 100000 + "";
        AnUser user = getByNickname(currentNickname);
        boolean set = RedisUtil.StringOps.setIfAbsent("USER_TELEPHONE_VERIFY_" + user.getId(), randomNumber + "##" + telephone, 2L, TimeUnit.MINUTES);
        if (!set) {
            throw new RuntimeException("请稍后再试");
        }

        String smsLimit = RedisUtil.StringOps.get(RedisKey.USER_SMS_SEND_LIMIT_FOR_EVERY_24HOURS_COUNT_KEY + user.getId());
        if (null == smsLimit) {
            RedisUtil.StringOps.setEx(RedisKey.USER_SMS_SEND_LIMIT_FOR_EVERY_24HOURS_COUNT_KEY + user.getId(), "1", 24, TimeUnit.HOURS);
            smsLimit="1";
        } else {
            long userCount = Long.parseLong(smsLimit);
            String systemLimit = SystemConfigConst.CONFIG_MAP.get(SystemConfigConst.USER_SMS_SEND_LIMIT_FOR_EVERY_24HOURS);
            if (null == systemLimit) {
                //系统未配置发送次数数，默认每天3次
                systemLimit = "3";
            }
            long systemLimitLong = Long.parseLong(systemLimit);
            if (systemLimitLong >= userCount) {
                throw new RuntimeException("每天只能发送"+systemLimit+"条短信");
            }
        }
        boolean state = SmsTencentUtil.sendSms(telephone, randomNumber);
        RedisUtil.StringOps.set(RedisKey.USER_SMS_SEND_LIMIT_FOR_EVERY_24HOURS_COUNT_KEY + user.getId(), Long.parseLong(smsLimit)+1+"");
        return state;
    }

    @Override
    public Boolean verifyCode(String telephone, String code) {
        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = getByNickname(currentNickname);
        String realCode = RedisUtil.StringOps.get("USER_TELEPHONE_VERIFY_" + user.getId());
        if (realCode.split("##")[0].equals(code) && realCode.split("##")[1].equals(telephone)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean telephoneChange(String telephone, String code) {
        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = getByNickname(currentNickname);
        LocalDateTime telephoneLastUpdateTime = user.getTelephoneLastUpdateTime();
        if (telephoneLastUpdateTime.plusDays(30).isAfter(LocalDateTime.now())) {
            throw new RuntimeException("请稍后再试");
        }
        if (verifyCode(telephone, code)) {
            user.setTelephone(telephone);
            updateById(user);
            return true;
        }
        return null;
    }

    @Override
    public BasePageResult<ListUserForNoteResponse> listForNote(ListUserForNoteRequest listUserForNoteRequest) {
        Long noteId = listUserForNoteRequest.getNoteId();
        String searchParams = listUserForNoteRequest.getKeyword();

        // 查询笔记
        AnNote note = anNoteService.getById(noteId);
        if (note == null) {
            throw new RuntimeException("笔记不存在");
        }

        String authorIds = note.getAuthorIds();
        // 查询联合作者
        Set<Long> oldAuthorIdList = anNoteService.getAuthorIdList(authorIds);

        // 创建查询条件
        IPage<AnUser> userPage = new Page<>(listUserForNoteRequest.getCurrent(), listUserForNoteRequest.getSize());
        QueryWrapper<AnUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", "COMMON")
                .eq("searchable", true);

        // 如果搜索条件存在，增加搜索条件
        if (StringUtils.isNotBlank(searchParams)) {
            queryWrapper.and(wrapper -> wrapper.like("nickname", searchParams)
                    .or().eq("id", searchParams)
                    .or().eq("telephone", searchParams)
                    .or().eq("mail", searchParams));
        }

        // 执行分页查询
        IPage<AnUser> page = page(userPage, queryWrapper);
        List<AnUser> records = page.getRecords();

        // 转换用户列表
        List<ListUserForNoteResponse> editUsers = com.anakki.data.utils.common.BeanUtils.copyBeanList(records, ListUserForNoteResponse.class);

        // 查询笔记用户权限
        List<AnNoteUserPermission> userPermissionsByNoteId = anNoteUserPermissionService.getUserPermissionsByNoteId(noteId);
        // 按 userId 分组
        Map<Long, List<AnNoteUserPermission>> permissionsGroupedByUserId = userPermissionsByNoteId.stream()
                .collect(Collectors.groupingBy(AnNoteUserPermission::getUserId));

        // 处理用户数据
        editUsers.forEach(editUser -> {
            Long userID = editUser.getId();
            if (oldAuthorIdList.contains(userID)) {
                editUser.setIsCollaborator(true);
            }
            // 装配用户权限
            List<AnNoteUserPermission> permissions = permissionsGroupedByUserId.get(userID);
            if (!CollectionUtils.isEmpty(permissions)) {
                List<NoteUserPermissionResponse> permissionResponses =
                        com.anakki.data.utils.common.BeanUtils.copyBeanList(permissions, NoteUserPermissionResponse.class);
                editUser.setPermissions(permissionResponses);
            }
        });

        return new BasePageResult<>(editUsers, page.getTotal());
    }

    @Override
    public Boolean registerEmailSend(UserRegisterVerifyRequest request) {
        Long registerEmailSendInterval = anSystemConfigService.getNumberConfigValue("registerEmailSendInterval");
        String email = request.getEmail();
        String verifyCode = VerificationCodeGenerator.generateVerificationCode();
        boolean sent = RedisUtil.KeyOps.hasKey("EmailVerify_" + email);
        if (sent){
            throw new RuntimeException(registerEmailSendInterval+"分钟请勿重复发送");
        }
        emailUtil.sendMessage(
                email,
                "【ANAKKIX】欢迎注册",
                getVerificationEmailContent(email, verifyCode));
        boolean success = RedisUtil.StringOps.setIfAbsent("EmailVerify_" + email, verifyCode, registerEmailSendInterval, TimeUnit.MINUTES);
        if (!success){
            throw new RuntimeException(registerEmailSendInterval+"分钟请勿重复发送");
        }
        return true;
    }

    public static String getVerificationEmailContent(String userName, String verificationCode) {
        return "<html>" +
                "<body>" +
                "<h2>尊敬的 " + userName + "，您好！</h2>" +
                "<p>感谢您注册我的网站 O(∩_∩)O。</p>" +
                "<p>您的验证码是：<strong>" + verificationCode + "</strong></p>" +
                "<p>请在 2 分钟内使用该验证码完成验证。</p>" +
                "<br/>" +
                "<p>如有问题，请联系我们：</p>" +
                "</body>" +
                "</html>";
    }
}
