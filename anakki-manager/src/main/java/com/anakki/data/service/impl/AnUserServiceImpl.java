package com.anakki.data.service.impl;

import com.anakki.data.entity.AnUser;
import com.anakki.data.mapper.AnUserMapper;
import com.anakki.data.service.AnUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@Service
public class AnUserServiceImpl extends ServiceImpl<AnUserMapper, AnUser> implements AnUserService {

}
