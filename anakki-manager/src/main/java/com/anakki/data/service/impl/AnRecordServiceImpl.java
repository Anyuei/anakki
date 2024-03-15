package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.enums.StatisticEnum;
import com.anakki.data.bean.constant.CosBucketNameConst;
import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.bean.constant.CosPathConst;
import com.anakki.data.entity.AnStatisticLog;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.ChangeRecordRequest;
import com.anakki.data.entity.request.GetContentRequest;
import com.anakki.data.entity.request.ListRecordRequest;
import com.anakki.data.entity.request.UploadRecordRequest;
import com.anakki.data.mapper.AnRecordMapper;
import com.anakki.data.service.*;
import com.anakki.data.utils.common.COSUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcloud.cos.auth.BasicSessionCredentials;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    @Override
    public BasePageResult<AnRecord> flow(GetContentRequest getContentRequest,String ipAddr) {
        IPage<AnRecord> anRecordIPage = new Page<>(
                getContentRequest.getCurrent(),
                getContentRequest.getSize());
        String type = getContentRequest.getImageType();

        QueryWrapper<AnRecord> anRecordQueryWrapper = new QueryWrapper<>();
        anRecordQueryWrapper.eq("type",type);
        anRecordQueryWrapper.eq("status","COMMON");
        anRecordQueryWrapper.orderByDesc("photo_time");
        IPage<AnRecord> page = page(anRecordIPage, anRecordQueryWrapper);
        List<AnRecord> records = page.getRecords();
        //统计模块访问数
        anStatisticService.increaseByName(type,ipAddr);

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
        anRecordQueryWrapper.orderByDesc("update_time");
        IPage<AnRecord> page = page(anRecordIPage, anRecordQueryWrapper);
        return new BasePageResult<>(page.getRecords(), page.getTotal());

    }

    @Override
    public Boolean uploadRecord(UploadRecordRequest uploadRecordRequest) throws IOException {
        for (MultipartFile multipartFile : uploadRecordRequest.getFile()) {
            AnRecord anRecord = new AnRecord();
            BeanUtils.copyProperties(uploadRecordRequest, anRecord);
            BasicSessionCredentials sessionCredential = COSUtil.getSessionCredential();

            String url = COSUtil.uploadObject(
                    multipartFile,
                    sessionCredential,
                    COSUtil.region,
                    CosBucketNameConst.BUCKET_NAME_IMAGES,
                    CosPathConst.BUCKET_NAME_IMAGES,
                    uploadRecordRequest.getIsRaw());
            anRecord.setImgUrl(url);
            anRecord.setFileSize(multipartFile.getSize() / 1024);
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

}
