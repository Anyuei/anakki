package com.anakki.data.entity.common;

import com.anakki.data.entity.AnSystemConfig;
import com.anakki.data.service.AnSystemConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: ExpKeyConst
 * Description:
 *
 * @author Anakki
 * @date 2023/11/25 2:47
 */
@Component
@Slf4j
public class ExpKeyConst {

    @Resource
    private AnSystemConfigService anSystemConfigService;

    //登录经验值
    public static String EXP_LOGIN="EXP_LOGIN";

    public static HashMap<String ,Long> EXP_CONFIG_MAP=new HashMap<>();
    @PostConstruct
    public void initExpConfig(){
        log.info("经验值配置初始化成功");
        QueryWrapper<AnSystemConfig> anSystemConfigQueryWrapper = new QueryWrapper<>();
        anSystemConfigQueryWrapper.likeRight("config_key","EXP_");
        List<AnSystemConfig> list = anSystemConfigService.list(anSystemConfigQueryWrapper);
        for (AnSystemConfig anSystemConfig : list) {
            EXP_CONFIG_MAP.put(anSystemConfig.getConfigKey(),Long.parseLong(anSystemConfig.getConfigValue()));
        }
    }
}
