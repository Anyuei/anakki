package com.anakki.data.service;

import com.anakki.data.entity.AnRecordUserOperateLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2023-12-04
 */
public interface AnRecordUserOperateLogService extends IService<AnRecordUserOperateLog> {

    Boolean log(Long recordId, Long userId, String ipAddr,String operateType);
}
