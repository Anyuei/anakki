package com.anakki.data.service;

import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.common.Address;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-29
 */
public interface AnIpAddressService extends IService<AnIpAddress> {

    AnIpAddress getAddressByIp(String ip);
}
