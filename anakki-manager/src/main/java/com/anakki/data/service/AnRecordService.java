package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.request.*;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drew.imaging.ImageProcessingException;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-08
 */
public interface AnRecordService extends IService<AnRecord> {

    BasePageResult<AnRecord> flow(GetContentRequest getContentRequest,String ipAddr);

    boolean removeById(Serializable id);
    List<String> getRecordTypes();

    BasePageResult<AnRecord> listRecordRequest(ListRecordRequest listRecordRequest);

    Boolean uploadRecord(UploadRecordRequest uploadRecordRequest) throws IOException, ImageProcessingException;

    Boolean uploadRecordForManage(UploadRecordForManageRequest uploadRecordRequest) throws IOException, ImageProcessingException;

    void changeRecordStatus(ChangeRecordRequest changeRecordRequest);

    void increaseViewCount(Long recordId);

    Object userOperate(Long id, String ipAddr, String operateType);
}
