package com.anakki.data.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.RandomList;
import com.anakki.data.entity.AnGiftLog;
import com.anakki.data.mapper.AnGiftLogMapper;
import com.anakki.data.service.AnGiftLogService;
import com.anakki.data.utils.common.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-04-15
 */
@Service
@Slf4j
public class AnGiftLogServiceImpl extends ServiceImpl<AnGiftLogMapper, AnGiftLog> implements AnGiftLogService {


    @Override
    public  boolean  insertRandomNames(
            List<String> names,
            LocalDateTime openTime,
            String gift
    ) {
        String currentNickname = BaseContext.getCurrentNickname();
        AnGiftLog anGiftLog = new AnGiftLog();
        anGiftLog.setGiftName(gift);
        anGiftLog.setUsers(JSONObject.toJSONString(names));
        anGiftLog.setOpenTime(openTime);
        anGiftLog.setInitiator(currentNickname);
        save(anGiftLog);
        return true;
    }

    @Override
    public List<AnGiftLog> getRandomNames() {
        QueryWrapper<AnGiftLog> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.orderByDesc("create_time");
        return list(objectQueryWrapper);
    }
    //@Scheduled(fixedRate = 10000)
    public void openName(){
        long startTime = System.currentTimeMillis();
        List<AnGiftLog> anGiftLogs = getRandomNames();
        if (anGiftLogs.isEmpty()){
            return;
        }
        anGiftLogs.forEach(anGiftLog -> {
            if (null!=anGiftLog&& StringUtils.isEmpty(anGiftLog.getWinners())){
                if (null!=anGiftLog.getOpenTime()&&anGiftLog.getOpenTime().isBefore(LocalDateTime.now())){
                    List<String> users = JSONObject.parseArray(anGiftLog.getUsers(),String.class);
                    if (users != null && !users.isEmpty()) {
                        Random random = new Random();
                        int randomIndex = random.nextInt(users.size());
                        String randomName = users.get(randomIndex);
                        anGiftLog.setWinners(randomName);
                        log.info("获奖人：" +randomName);
                        updateById(anGiftLog);
                    }
                }
            }
        });
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
//        log.info("openName方法执行耗时：" + executionTime + " 毫秒");
    }
}
