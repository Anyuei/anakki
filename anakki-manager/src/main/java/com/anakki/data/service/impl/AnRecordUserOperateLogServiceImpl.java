package com.anakki.data.service.impl;

import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.AnPathStatisticLog;
import com.anakki.data.entity.AnRecordUserOperateLog;
import com.anakki.data.mapper.AnRecordUserOperateLogMapper;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnRecordUserOperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-12-04
 */
@Service
public class AnRecordUserOperateLogServiceImpl extends ServiceImpl<AnRecordUserOperateLogMapper, AnRecordUserOperateLog> implements AnRecordUserOperateLogService {


    @Autowired
    private AnIpAddressService anIpAddressService;


    @Override
    public Boolean log(Long recordId, Long userId, String ipAddr,String operateType) {
        AnIpAddress addressByIp = anIpAddressService.getAddressByIp(ipAddr);
        //统计访问日志
        if (null!=addressByIp){
            AnRecordUserOperateLog anPathStatisticLog = new AnRecordUserOperateLog();
            BeanUtils.copyProperties(addressByIp,anPathStatisticLog,"id","create_time","updateTime");
            anPathStatisticLog.setRecordId(recordId);
            anPathStatisticLog.setUserId(userId);
            anPathStatisticLog.setOperateType(operateType);
            save(anPathStatisticLog);
        }
        return true;
    }
}
