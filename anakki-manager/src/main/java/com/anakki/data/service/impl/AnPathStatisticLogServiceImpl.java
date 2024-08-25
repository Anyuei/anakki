package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.AnPathStatisticLog;
import com.anakki.data.entity.request.ListPathStatisticLogRequest;
import com.anakki.data.entity.response.DailyVisitCountResponse;
import com.anakki.data.mapper.AnPathStatisticLogMapper;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnPathStatisticLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public BasePageResult<AnPathStatisticLog> listPathStatisticLog(ListPathStatisticLogRequest listIpAddressRequest) {
        String ip = listIpAddressRequest.getClientIp();
        String path = listIpAddressRequest.getPath();
        String prov = listIpAddressRequest.getProv();
        String city = listIpAddressRequest.getCity();
        String district = listIpAddressRequest.getDistrict();
        String country = listIpAddressRequest.getCountry();
        LocalDateTime createTimeStart = listIpAddressRequest.getCreateTimeStart();
        LocalDateTime createTimeEnd = listIpAddressRequest.getCreateTimeEnd();

        IPage<AnPathStatisticLog> anPathStatisticLogIPage = new Page<>(
                listIpAddressRequest.getCurrent(),
                listIpAddressRequest.getSize());
        QueryWrapper<AnPathStatisticLog> anPathStatisticLogQueryWrapper = new QueryWrapper<>();
        anPathStatisticLogQueryWrapper.eq(null != ip, "client_ip", ip);
        anPathStatisticLogQueryWrapper.like(null != prov, "prov", prov);
        anPathStatisticLogQueryWrapper.like(null != city, "city", city);
        anPathStatisticLogQueryWrapper.like(null != path, "path", path);
        anPathStatisticLogQueryWrapper.like(null != country, "country", country);
        anPathStatisticLogQueryWrapper.like(null != district, "district", district);
        anPathStatisticLogQueryWrapper.ge(null != createTimeStart, "create_time", createTimeStart);
        anPathStatisticLogQueryWrapper.le(null != createTimeEnd, "create_time", createTimeEnd);
        anPathStatisticLogQueryWrapper.orderByDesc("create_time");
        IPage<AnPathStatisticLog> page = page(anPathStatisticLogIPage, anPathStatisticLogQueryWrapper);
        return new BasePageResult<>(page.getRecords(), page.getTotal());
    }
}
