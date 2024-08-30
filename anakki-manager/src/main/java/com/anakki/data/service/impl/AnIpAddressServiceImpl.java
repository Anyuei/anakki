package com.anakki.data.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.request.ListIpAddressRequest;
import com.anakki.data.mapper.AnIpAddressMapper;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.utils.IPUtils;
import com.anakki.data.utils.common.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public BasePageResult<AnIpAddress> listIpAddress(ListIpAddressRequest listIpAddressRequest) {
        String ip = listIpAddressRequest.getIp();
        String prov = listIpAddressRequest.getProv();
        String city = listIpAddressRequest.getCity();
        String state = listIpAddressRequest.getState();
        String district = listIpAddressRequest.getDistrict();
        String contentJson = listIpAddressRequest.getContentJson();
        String country = listIpAddressRequest.getCountry();
        LocalDateTime createTimeStart = listIpAddressRequest.getCreateTimeStart();
        LocalDateTime createTimeEnd = listIpAddressRequest.getCreateTimeEnd();

        IPage<AnIpAddress> anIpAddressIPage = new Page<>(
                listIpAddressRequest.getCurrent(),
                listIpAddressRequest.getSize());
        QueryWrapper<AnIpAddress> anIpAddressQueryWrapper = new QueryWrapper<>();
        anIpAddressQueryWrapper.eq(null != ip, "ip", ip);
        anIpAddressQueryWrapper.like(null != prov, "prov", prov);
        anIpAddressQueryWrapper.like(null != city, "city", city);
        anIpAddressQueryWrapper.like(null != country, "country", country);
        anIpAddressQueryWrapper.like(null!= contentJson,"content_json",contentJson);
        anIpAddressQueryWrapper.like(null != state, "state", state);
        anIpAddressQueryWrapper.like(null != district, "district", district);
        anIpAddressQueryWrapper.ge(null != createTimeStart, "create_time", createTimeStart);
        anIpAddressQueryWrapper.le(null != createTimeEnd, "create_time", createTimeEnd);
        anIpAddressQueryWrapper.orderByDesc("create_time");
        IPage<AnIpAddress> page = page(anIpAddressIPage, anIpAddressQueryWrapper);
        return new BasePageResult<>(page.getRecords(), page.getTotal());

    }



    @Override
    public synchronized AnIpAddress getAddressByIp(String ip) {
        if (IPUtils.isPrivateIPAddress(ip)||ip.equals("119.45.1.61")){
            return new AnIpAddress();
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
        QueryWrapper<AnIpAddress> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("state","0");
        List<AnIpAddress> ipAddresses = list(objectQueryWrapper);
        ipAddresses.forEach(ipAddress ->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
                HashMap<String, String> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("key","koAO6jUwkei2qVUvnQ4EpoSQL81qcG6YGZTSnM8XgJJJhLKAtEPYZBDEnYS2iTmZ");
                objectObjectHashMap.put("ip",ipAddress.getIp());
                try{

                    String result = HttpUtil.sendGetRequest("https://api.ipplus360.com/ip/geo/v1/district", objectObjectHashMap);
                    JSONObject resultJsonString = JSONObject.parseObject(result);
                    JSONObject data = resultJsonString.getJSONObject("data");
                    ipAddress.setCountry(data.getString("country"));
                    ipAddress.setCity(data.getString("city"));
                    ipAddress.setProv(data.getString("prov"));
                    ipAddress.setDistrict(data.getString("district"));
                    ipAddress.setContentJson(JSONObject.toJSONString(data));
                    ipAddress.setState("1");
                    updateById(ipAddress);
                }catch (Exception e){
                    log.error("地域获取异常："+e.getMessage());
                }

        });
    }
}
