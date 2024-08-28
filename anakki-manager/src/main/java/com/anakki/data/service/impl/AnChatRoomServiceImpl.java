package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnChatRoom;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.enums.EnterType;
import com.anakki.data.entity.request.CreateChatRoomManageRequest;
import com.anakki.data.entity.request.ListChatRoomManageRequest;
import com.anakki.data.entity.request.ListChatRoomRequest;
import com.anakki.data.entity.request.ListUserForRoomManageRequest;
import com.anakki.data.entity.response.ListUserForRoomManageResponse;
import com.anakki.data.mapper.AnChatRoomMapper;
import com.anakki.data.service.AnChatRoomService;
import com.anakki.data.service.AnUserService;
import com.anakki.data.utils.common.NumberUtil;
import com.anakki.data.utils.dealUtils.MD5SaltUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-08-28
 */
@Service
public class AnChatRoomServiceImpl extends ServiceImpl<AnChatRoomMapper, AnChatRoom> implements AnChatRoomService {

    @Autowired
    private AnUserService anUserService;
    @Override
    public BasePageResult<AnChatRoom> listRoom(ListChatRoomRequest request) {

        String currentNickname = BaseContext.getCurrentNickname(false);

        IPage<AnChatRoom> anChatRoomIPage = new Page<>(
                request.getCurrent(),
                request.getSize());
        QueryWrapper<AnChatRoom> anChatRoomQueryWrapper = new QueryWrapper<>();
        anChatRoomQueryWrapper.ge(
                null != request.getCreateTimeStart(), "create_time", request.getCreateTimeStart());
        anChatRoomQueryWrapper.le(
                null != request.getCreateTimeEnd(), "create_time", request.getCreateTimeEnd());
        anChatRoomQueryWrapper.orderByDesc("create_time");
        IPage<AnChatRoom> page = page(anChatRoomIPage, anChatRoomQueryWrapper);
        List<AnChatRoom> records = page.getRecords();
        return new BasePageResult<>(records, page.getTotal());
    }

    @Override
    public BasePageResult<AnChatRoom> listRoomForManage(ListChatRoomManageRequest request) {

        IPage<AnChatRoom> anChatRoomIPage = new Page<>(
                request.getCurrent(),
                request.getSize());
        QueryWrapper<AnChatRoom> anChatRoomQueryWrapper = new QueryWrapper<>();
        anChatRoomQueryWrapper.ge(
                null != request.getCreateTimeStart(), "create_time", request.getCreateTimeStart());
        anChatRoomQueryWrapper.le(
                null != request.getCreateTimeEnd(), "create_time", request.getCreateTimeEnd());

        anChatRoomQueryWrapper.eq(
                null != request.getRoomType(), "room_type", request.getRoomType());
        anChatRoomQueryWrapper.eq(
                null != request.getAvatar(), "avatar", request.getAvatar());
        anChatRoomQueryWrapper.eq(
                null != request.getRoomMasterId(), "room_master_id", request.getRoomMasterId());
        anChatRoomQueryWrapper.like(
                StringUtils.isNotBlank(request.getRoomName()), "room_name", request.getRoomName());
        anChatRoomQueryWrapper.like(
                StringUtils.isNotBlank(request.getDescription()), "description", request.getDescription());
        anChatRoomQueryWrapper.eq(
                null != request.getIsTemp(), "is_temp", request.getIsTemp());
        anChatRoomQueryWrapper.eq(
                null != request.getIsEncrypt(), "is_encrypt", request.getIsEncrypt());
        anChatRoomQueryWrapper.eq(
                null != request.getIsPublicForSearch(), "is_public_for_search", request.getIsPublicForSearch());
        anChatRoomQueryWrapper.eq(
                null != request.getEnterType(), "enter_type", request.getEnterType());
        anChatRoomQueryWrapper.eq(
                StringUtils.isNotBlank(request.getStatus()), "status", request.getStatus());

        anChatRoomQueryWrapper.orderByDesc("create_time");
        IPage<AnChatRoom> page = page(anChatRoomIPage, anChatRoomQueryWrapper);
        List<AnChatRoom> records = page.getRecords();
        return new BasePageResult<>(records, page.getTotal());
    }

    @Override
    public Boolean createRoom(CreateChatRoomManageRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        AnChatRoom room = new AnChatRoom();
        Long roomMasterId = request.getRoomMasterId();
        AnUser master = anUserService.getById(roomMasterId);
        if (null==master){
            throw new RuntimeException("管理员不存在");
        }
        BeanUtils.copyProperties(request,room);
        Integer enterType = request.getEnterType();
        String enterPassword = request.getEnterPassword();
        if (EnterType.fromCode(enterType).equals(EnterType.PASSWORD)){
            if (StringUtils.isEmpty(enterPassword)){
                throw new RuntimeException("密码不能为空");
            }
            String encryptedData = MD5SaltUtil.getEncryptedData(enterPassword);
            room.setEnterPassword(encryptedData);
        }

        return save(room);
    }

    @Override
    public List<ListUserForRoomManageResponse> listUserForRoom(ListUserForRoomManageRequest request) {
        String userNameAndUserId = request.getUserNameAndUserId();
        QueryWrapper<AnUser> userQueryWrapper = new QueryWrapper<>();
        if (NumberUtil.isNumeric(userNameAndUserId)) {
            userQueryWrapper.eq("id",Long.parseLong(userNameAndUserId));
        }else {
            userQueryWrapper.like("nickname",userNameAndUserId);
        }

        List<AnUser> users = anUserService.list(userQueryWrapper);
        return com.anakki.data.utils.common.BeanUtils.copyBeanList(users,ListUserForRoomManageResponse.class);
    }

}
