package com.anakki.data.service.impl;

import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.AnPathStatisticLog;
import com.anakki.data.entity.response.DailyVisitCountResponse;
import com.anakki.data.mapper.AnPathStatisticLogMapper;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnPathStatisticLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    @Autowired
    private AnPathStatisticLogMapper anPathStatisticLogMapper;
    @Override
    public synchronized Boolean log(String path, String ipAddr) {
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

    @Override
    public List<DailyVisitCountResponse> getDailyVisitCounts(LocalDate startDate, LocalDate endDate) {
        return anPathStatisticLogMapper.findDailyVisitCounts(startDate, endDate);
    }
}
