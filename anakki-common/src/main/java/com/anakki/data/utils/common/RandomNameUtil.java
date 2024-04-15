package com.anakki.data.utils.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anakki.data.bean.common.RandomList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ClassName: RandomNameUtil
 * Description:
 *
 * @author Anakki
 * @date 2024/4/15 7:22
 */
@Component
@Slf4j
public class RandomNameUtil {

    public synchronized static void insertRandomNames(List<String> names, LocalDateTime openTime) {
        String set = RedisUtil.StringOps.get("RandomName");
        List<RandomList> randomLists;
        RandomList randomList = new RandomList();
        randomList.setNames(names);
        randomList.setOpenTime(openTime);
        if (!StringUtils.isEmpty(set)) {
            randomLists = JSONArray.parseArray(set, RandomList.class);
        } else {
            randomLists = new ArrayList<>();
        }
        randomLists.add(randomList);
        String jsonString = JSONArray.toJSONString(randomLists);
        RedisUtil.StringOps.set("RandomName",jsonString);
    }

    public static List<RandomList> getRandomNames() {
        String set = RedisUtil.StringOps.get("RandomName");
        List<RandomList> randomLists;
        if (!StringUtils.isEmpty(set)) {
            randomLists = JSONArray.parseArray(set, RandomList.class);
        } else {
            randomLists = new ArrayList<>();
        }
        return randomLists;
    }
    public static void deleteRandomNames() {
         RedisUtil.KeyOps.delete("RandomName");
    }
    @Scheduled(fixedRate = 1000)
    public void openName(){
        long startTime = System.currentTimeMillis();
        List<RandomList> randomNames = getRandomNames();
        randomNames.forEach(randomList -> {
            if (null!=randomList&&StringUtils.isEmpty(randomList.getName())){
                if (null!=randomList.getOpenTime()&&randomList.getOpenTime().isBefore(LocalDateTime.now())){
                    List<String> names = randomList.getNames();
                    if (names != null && !names.isEmpty()) {
                        Random random = new Random();
                        int randomIndex = random.nextInt(names.size());
                        String randomName = names.get(randomIndex);
                        randomList.setName(randomName);
                        log.info("获奖人：" +randomName);
                    }
                }
            }
        });
        String jsonString = JSONArray.toJSONString(randomNames);
        RedisUtil.StringOps.set("RandomName",jsonString);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
//        log.info("openName方法执行耗时：" + executionTime + " 毫秒");
    }
}
