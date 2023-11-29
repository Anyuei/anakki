package com.anakki.data.service.impl;

import com.anakki.data.bean.common.enums.CommentStateEnum;
import com.anakki.data.bean.common.enums.StatisticEnum;
import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.AnStatistic;
import com.anakki.data.entity.AnStatisticLog;
import com.anakki.data.mapper.AnStatisticMapper;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnStatisticLogService;
import com.anakki.data.service.AnStatisticService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-29
 */
@Service
public class AnStatisticServiceImpl extends ServiceImpl<AnStatisticMapper, AnStatistic> implements AnStatisticService {
    @Autowired
    private AnStatisticLogService anStatisticLogService;
    @Autowired
    private AnIpAddressService anIpAddressService;

    @Override
    public Boolean increaseByName(String name,String ipAddr) {
        if (StatisticEnum.isValidEnumName(name)){
            StatisticEnum statisticEnum = StatisticEnum.valueOf(name);
            QueryWrapper<AnStatistic> anStatisticQueryWrapper = new QueryWrapper<>();
            anStatisticQueryWrapper.eq("statistic_name", statisticEnum.name());
            AnStatistic one = getOne(anStatisticQueryWrapper);
            if (null==one){
                one=new AnStatistic();
                one.setStatisticName(name);
                one.setStatisticValue("1");
                one.setStatisticDescription(statisticEnum.getDescription());
                save(one);
            }else{
                one.setStatisticValue(String.valueOf(Long.parseLong(one.getStatisticValue())+1));
                updateById(one);
            }
            AnIpAddress addressByIp = anIpAddressService.getAddressByIp(ipAddr);
            //统计访问日志
            if (null!=addressByIp){
                AnStatisticLog anStatisticLog = new AnStatisticLog();
                BeanUtils.copyProperties(addressByIp,anStatisticLog,"id","create_time","updateTime");
                anStatisticLog.setStatisticName(name);
                anStatisticLog.setClientIp(ipAddr);
                anStatisticLog.setStatisticDescription(statisticEnum.getDescription());
                anStatisticLogService.save(anStatisticLog);
            }
            return true;
        }
        return false;
    }
}
