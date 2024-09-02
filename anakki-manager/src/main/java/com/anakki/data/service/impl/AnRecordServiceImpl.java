package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.constant.CosBucketNameConst;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.request.AvatarImgListRequest;
import com.anakki.data.entity.response.AvatarImgListResponse;
import com.anakki.data.mapper.AnRecordMapper;
import com.anakki.data.service.*;
import com.anakki.data.utils.common.COSUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drew.imaging.ImageProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-08
 */
@Service
public class AnRecordServiceImpl extends ServiceImpl<AnRecordMapper, AnRecord> implements AnRecordService {

    @Autowired
    private AnStatisticService anStatisticService;

    @Autowired
    private AnRecordUserOperateLogService anRecordUserOperateLogService;

    @Autowired
    private AnUserService anUserService;

    @Autowired
    private AnRecordMapper anRecordMapper;
    @Override
    public BasePageResult<AnRecord> flow(GetContentRequest getContentRequest,String ipAddr) {
        IPage<AnRecord> anRecordIPage = new Page<>(
                getContentRequest.getCurrent(),
                getContentRequest.getSize());
        String type = getContentRequest.getImageType();
        String tag = getContentRequest.getTag();
        QueryWrapper<AnRecord> anRecordQueryWrapper = new QueryWrapper<>();
        anRecordQueryWrapper.eq("type",type);
        anRecordQueryWrapper.eq("status","COMMON");
        anRecordQueryWrapper.eq(!ObjectUtils.isEmpty(tag),"tag",tag);
        anRecordQueryWrapper.orderByDesc("photo_time","id");
        IPage<AnRecord> page = page(anRecordIPage, anRecordQueryWrapper);
        List<AnRecord> records = page.getRecords();
        //统计模块访问数
        anStatisticService.increaseByName(type,ipAddr);

        return new BasePageResult<>(records, page.getTotal());
    }
    @Override
    public BasePageResult<AnRecord> flowMedia(GetMediaRequest getContentRequest, String ipAddr) {
        IPage<AnRecord> anRecordIPage = new Page<>(
                getContentRequest.getCurrent(),
                getContentRequest.getSize());
        String tag = getContentRequest.getTag();
        QueryWrapper<AnRecord> anRecordQueryWrapper = new QueryWrapper<>();
        anRecordQueryWrapper.eq("type","MAINPAGE_FLOW");
        anRecordQueryWrapper.eq(!StringUtils.isBlank(tag),"tag",tag);
        anRecordQueryWrapper.eq("status","COMMON");
        anRecordQueryWrapper.orderByDesc("photo_time","id");
        IPage<AnRecord> page = page(anRecordIPage, anRecordQueryWrapper);
        List<AnRecord> records = page.getRecords();
        //统计模块访问数
        anStatisticService.increaseByName("MAINPAGE_FLOW",ipAddr);

        return new BasePageResult<>(records, page.getTotal());
    }

    @Override
    public boolean removeById(Serializable id) {
        AnRecord byId = getById(id);
        if (null==byId){
            throw new RuntimeException("记录不存在");
        }
        String imgUrl = byId.getImgUrl();
        COSUtil.deleteObject(CosBucketNameConst.BUCKET_NAME_IMAGES,imgUrl);
        return super.removeById(id);
    }

    @Override
    public List<String> getRecordTypes() {
        return baseMapper.getRecordTypes();
    }

    @Override
    public BasePageResult<AnRecord> listRecordRequest(ListRecordRequest listRecordRequest) {
        String type = listRecordRequest.getType();
        String description = listRecordRequest.getDescription();
        String location = listRecordRequest.getLocation();
        String title = listRecordRequest.getTitle();
        String photoBy = listRecordRequest.getPhotoBy();
        LocalDateTime createTimeStart = listRecordRequest.getCreateTimeStart();
        LocalDateTime createTimeEnd = listRecordRequest.getCreateTimeEnd();
        LocalDateTime photoTimeStart = listRecordRequest.getPhotoTimeStart();
        LocalDateTime photoTimeEnd = listRecordRequest.getPhotoTimeEnd();

        IPage<AnRecord> anRecordIPage = new Page<>(
                listRecordRequest.getCurrent(),
                listRecordRequest.getSize());
        QueryWrapper<AnRecord> anRecordQueryWrapper = new QueryWrapper<>();
        anRecordQueryWrapper.eq(null != type, "type", type);
        anRecordQueryWrapper.like(null != description, "description", description);
        anRecordQueryWrapper.like(null != location, "location", location);
        anRecordQueryWrapper.like(null != type, "type", type);
        anRecordQueryWrapper.like(null != title, "title", title);
        anRecordQueryWrapper.like(null != photoBy, "photo_by", photoBy);
        anRecordQueryWrapper.ge(null != createTimeStart, "create_time", createTimeStart);
        anRecordQueryWrapper.le(null != createTimeEnd, "create_time", createTimeEnd);
        anRecordQueryWrapper.ge(null != photoTimeStart, "photo_time", photoTimeStart);
        anRecordQueryWrapper.le(null != photoTimeEnd, "photo_time", photoTimeEnd);
        anRecordQueryWrapper.orderByDesc("create_time");
        IPage<AnRecord> page = page(anRecordIPage, anRecordQueryWrapper);
        return new BasePageResult<>(page.getRecords(), page.getTotal());

    }

    @Override
    public Boolean uploadRecord(UploadRecordRequest uploadRecordRequest) throws IOException, ImageProcessingException {
        for (String url : uploadRecordRequest.getFileUrls()) {
            AnRecord anRecord = new AnRecord();
            BeanUtils.copyProperties(uploadRecordRequest, anRecord);
            anRecord.setImgUrl(url);
            anRecord.setFileSize(0L);
            save(anRecord);
        }
        return true;
    }

    @Override
    public Boolean uploadRecordForManage(UploadRecordForManageRequest uploadRecordRequest) throws IOException, ImageProcessingException {
        for (FileInfoRequest fileInfoRequest : uploadRecordRequest.getFiles()) {
            AnRecord anRecord = new AnRecord();
            BeanUtils.copyProperties(uploadRecordRequest, anRecord);
            BeanUtils.copyProperties(fileInfoRequest, anRecord);
            anRecord.setImgUrl(fileInfoRequest.getUrl());
            anRecord.setFileSize(fileInfoRequest.getSize()/1024);
            anRecord.setTitle(fileInfoRequest.getName());
            anRecord.setStatus(uploadRecordRequest.getStatus()?"COMMON":"INVALID");
            save(anRecord);
        }
        return true;
    }


    @Override
    public void changeRecordStatus(ChangeRecordRequest changeRecordRequest) {
        Long id = changeRecordRequest.getId();
        AnRecord byId = getById(id);
        if (changeRecordRequest.getIsChecked()) {
            byId.setStatus("COMMON");
        } else {
            byId.setStatus("INVALID");
        }
        updateById(byId);
    }

    @Override
    public synchronized void increaseViewCount(Long recordId) {
        String currentNickname = BaseContext.getCurrentNickname(false);
        if (null==currentNickname){
            currentNickname="未登录访客";
        }
        AnRecord byId = getById(recordId);
        long count = byId.getViewCount() + 1;
        byId.setViewCount(count);
        updateById(byId);
    }

    @Override
    public synchronized Object userOperate(Long id, String ipAddr, String operateType) {
        String currentNickname = BaseContext.getCurrentNickname(false);
        AnUser user = anUserService.getByNickname(currentNickname);
        AnRecord record = getById(id);
        Long userId=null==user?0L:user.getId();
        if (null==record){
            throw new RuntimeException("记录不存在");
        }
        if ("LIKE".equals(operateType)){
            long currentCount = record.getLikeCount() + 1;
            record.setLikeCount(currentCount);
            anRecordUserOperateLogService.log(id,userId,ipAddr,"LIKE");
            updateById(record);
            return currentCount;
        }else if ("UNLIKE".equals(operateType)){
            long currentCount = record.getUnlikeCount() + 1;
            record.setUnlikeCount(currentCount);
            anRecordUserOperateLogService.log(id,userId,ipAddr,"UNLIKE");
            updateById(record);
            return currentCount;
        }
        return null;
    }


    @Override
    public  BasePageResult<AvatarImgListResponse> listAvatars(AvatarImgListRequest avatarImgListRequest){
        LocalDateTime createTimeStart = avatarImgListRequest.getCreateTimeStart();
        LocalDateTime createTimeEnd = avatarImgListRequest.getCreateTimeEnd();
        String searchString = avatarImgListRequest.getSearchString();
        IPage<AnRecord> anRecordIPage = new Page<>(
                avatarImgListRequest.getCurrent(),
                avatarImgListRequest.getSize());
        QueryWrapper<AnRecord> anRecordQueryWrapper = new QueryWrapper<>();
        anRecordQueryWrapper.eq( "type", "AVATAR");
        anRecordQueryWrapper.like(null != searchString, "description", searchString);
        anRecordQueryWrapper.ge(null != createTimeStart, "create_time", createTimeStart);
        anRecordQueryWrapper.le(null != createTimeEnd, "create_time", createTimeEnd);
        anRecordQueryWrapper.orderByDesc("create_time","id");
        anRecordQueryWrapper.eq("avatar_user_id",-1L);
        IPage<AnRecord> page = page(anRecordIPage, anRecordQueryWrapper);
        List<AnRecord> records = page.getRecords();
        List<AvatarImgListResponse> avatarImgListResponses = com.anakki.data.utils.common.BeanUtils.copyBeanList(records, AvatarImgListResponse.class);
        return new BasePageResult<>(avatarImgListResponses, page.getTotal());
    }

    @Override
    public void removeByAvatarUserId(Long id) {
        QueryWrapper<AnRecord> anRecordQueryWrapper = new QueryWrapper<>();
        anRecordQueryWrapper.eq("avatar_user_id",id);
// 获取符合条件的记录
        AnRecord record = getOne(anRecordQueryWrapper);
        if (record != null) {
            // 将 avatar_user_id 字段置空
            record.setAvatarUserId(-1L);
            // 更新记录
            anRecordMapper.updateById(record);
        }
    }
}
