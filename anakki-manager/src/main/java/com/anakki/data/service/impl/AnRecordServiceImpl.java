package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.ListRecordRequest;
import com.anakki.data.entity.request.UploadRecordRequest;
import com.anakki.data.entity.response.ListUserResponse;
import com.anakki.data.mapper.AnRecordMapper;
import com.anakki.data.service.AnRecordService;
import com.anakki.data.utils.common.COSUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.tencent.cloud.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.bouncycastle.asn1.iana.IANAObjectIdentifiers.mail;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-08
 */
@Service
public class AnRecordServiceImpl extends ServiceImpl<AnRecordMapper, AnRecord> implements AnRecordService {


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

        IPage<AnRecord> page = page(anRecordIPage, anRecordQueryWrapper);
        return new BasePageResult<>(page.getRecords(), page.getTotal());

    }

    @Override
    public Boolean uploadRecord(UploadRecordRequest uploadRecordRequest)  {
        for (MultipartFile multipartFile : uploadRecordRequest.getFile()) {
            AnRecord anRecord = new AnRecord();
            BeanUtils.copyProperties(uploadRecordRequest,anRecord);
            BasicSessionCredentials sessionCredential = COSUtil.getSessionCredential();

            String url = COSUtil.uploadObject(multipartFile, sessionCredential, COSUtil.region, "anakki-1258150206");
            anRecord.setImgUrl(url);
            save(anRecord);
        }
        return true;
    }
}
