package com.anakki.data.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.mapper.AnIpAddressMapper;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.utils.IPUtils;
import com.anakki.data.utils.common.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-29
 */
@Service
@Slf4j
public class AnIpAddressServiceImpl extends ServiceImpl<AnIpAddressMapper, AnIpAddress> implements AnIpAddressService {

    @Override
    public AnIpAddress getAddressByIp(String ip) {
        if (IPUtils.isPrivateIPAddress(ip)){
            return null;
        }
        QueryWrapper<AnIpAddress> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("ip",ip);
        AnIpAddress one = getOne(objectQueryWrapper);
        if (null==one){
            HashMap<String, String> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("ip",ip);
            try{
                AnIpAddress anIpAddress = new AnIpAddress();
                String result = HttpUtil.sendGetRequest("https://qifu-api.baidubce.com/ip/geo/v1/district", objectObjectHashMap);
                JSONObject resultJsonString = JSONObject.parseObject(result);
                JSONObject data = resultJsonString.getJSONObject("data");
                anIpAddress.setIp(ip);
                anIpAddress.setCountry(data.getString("country"));
                anIpAddress.setProv(data.getString("prov"));
                anIpAddress.setCity(data.getString("city"));
                anIpAddress.setDistrict(data.getString("district"));
                anIpAddress.setContentJson(JSONObject.toJSONString(data));
                        save(anIpAddress);
                        return anIpAddress;
            }catch (Exception e){
                log.error("地域获取异常："+e.getMessage());
            }
            return null;
        }else{
            return one;
        }
    }
}
