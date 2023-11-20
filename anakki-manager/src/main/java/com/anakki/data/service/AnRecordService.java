package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.request.ChangeRecordRequest;
import com.anakki.data.entity.request.ListRecordRequest;
import com.anakki.data.entity.request.UploadRecordRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Select;

import java.io.IOException;
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

    List<String> getRecordTypes();

    BasePageResult<AnRecord> listRecordRequest(ListRecordRequest listRecordRequest);

    Boolean uploadRecord(UploadRecordRequest uploadRecordRequest);

    void changeRecordStatus(ChangeRecordRequest changeRecordRequest);
}
