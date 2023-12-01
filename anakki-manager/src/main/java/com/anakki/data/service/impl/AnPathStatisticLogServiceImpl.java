package com.anakki.data.service.impl;

import com.anakki.data.bean.common.enums.StatisticEnum;
import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.AnPathStatisticLog;
import com.anakki.data.entity.AnStatistic;
import com.anakki.data.entity.AnStatisticLog;
import com.anakki.data.mapper.AnPathStatisticLogMapper;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnPathStatisticLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @since 2023-12-01
 */
@Service
public class AnPathStatisticLogServiceImpl extends ServiceImpl<AnPathStatisticLogMapper, AnPathStatisticLog> implements AnPathStatisticLogService {
    @Autowired
    private AnIpAddressService anIpAddressService;
    @Override
    public Boolean log(String path, String ipAddr) {
            AnIpAddress addressByIp = anIpAddressService.getAddressByIp(ipAddr);
            //统计访问日志
            if (null!=addressByIp){
                AnPathStatisticLog anPathStatisticLog = new AnPathStatisticLog();
                BeanUtils.copyProperties(addressByIp,anPathStatisticLog,"id","create_time","updateTime");
                anPathStatisticLog.setPath(path);
                anPathStatisticLog.setClientIp(ipAddr);
                save(anPathStatisticLog);
            }
            return true;
    }
}
