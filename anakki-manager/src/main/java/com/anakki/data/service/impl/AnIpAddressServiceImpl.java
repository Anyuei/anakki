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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

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
    public synchronized AnIpAddress getAddressByIp(String ip) {
        if (IPUtils.isPrivateIPAddress(ip)||ip.equals("119.45.1.61")){
            return null;
        }
        QueryWrapper<AnIpAddress> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("ip",ip);
        AnIpAddress one = getOne(objectQueryWrapper);
        if (null==one){
            HashMap<String, String> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("key","koAO6jUwkei2qVUvnQ4EpoSQL81qcG6YGZTSnM8XgJJJhLKAtEPYZBDEnYS2iTmZ");
            objectObjectHashMap.put("ip",ip);
            try{
                AnIpAddress anIpAddress = new AnIpAddress();
                String result = HttpUtil.sendGetRequest("https://api.ipplus360.com/ip/geo/v1/district", objectObjectHashMap);
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

    @Override
    public void updateAddressByIp() {
        List<AnIpAddress> ipAddresses = list();
        ipAddresses.forEach(ipAddress ->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (StringUtils.isEmpty(ipAddress.getCountry())||StringUtils.isEmpty(ipAddress.getCity())){
                HashMap<String, String> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("key","koAO6jUwkei2qVUvnQ4EpoSQL81qcG6YGZTSnM8XgJJJhLKAtEPYZBDEnYS2iTmZ");
                objectObjectHashMap.put("ip",ipAddress.getIp());
                try{

                    String result = HttpUtil.sendGetRequest("https://api.ipplus360.com/ip/geo/v1/district", objectObjectHashMap);
                    JSONObject resultJsonString = JSONObject.parseObject(result);
                    JSONObject data = resultJsonString.getJSONObject("data");
                    ipAddress.setCountry(data.getString("country"));
                    ipAddress.setCity(data.getString("city"));
                    ipAddress.setDistrict(data.getString("district"));
                    ipAddress.setContentJson(JSONObject.toJSONString(data));
                    updateById(ipAddress);
                }catch (Exception e){
                    log.error("地域获取异常："+e.getMessage());
                }
            }
        });

    }

}
