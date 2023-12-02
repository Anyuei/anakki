package com.anakki.data.entity.common;

import com.anakki.data.entity.AnSystemConfig;
import com.anakki.data.service.AnSystemConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: SystemConfigConst
 * Description:
 *
 * @author Anakki
 * @date 2023/12/1 13:12
 */
@Component
@Slf4j
public class SystemConfigConst {

    public static String USER_SMS_SEND_LIMIT_FOR_EVERY_24HOURS="USER_SMS_SEND_LIMIT_FOR_EVERY_24HOURS";


    @Resource
    private AnSystemConfigService anSystemConfigService;

    public static HashMap<String ,String> CONFIG_MAP=new HashMap<>();
    @PostConstruct
    public void initConfig(){
        log.info("系统配置初始化中");
        List<AnSystemConfig> list = anSystemConfigService.list();
        for (AnSystemConfig anSystemConfig : list) {
            CONFIG_MAP.put(anSystemConfig.getConfigKey(),anSystemConfig.getConfigValue());
        }
        log.info("系统配置初始化成功");
    }
}
