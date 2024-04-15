package com.anakki.data.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anakki.data.bean.common.RandomList;
import com.anakki.data.entity.AnGiftLog;
import com.anakki.data.utils.common.RedisUtil;
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
    public static List<RandomList> randomLists;


    public static synchronized List<RandomList> getRandomNames() {
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

}
