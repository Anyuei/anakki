package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ManagerToken;
import com.anakki.data.bean.common.UserToken;
import com.anakki.data.entity.AnManager;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.ListManagerResponse;
import com.anakki.data.mapper.AnManagerMapper;
import com.anakki.data.service.AnManagerService;
import com.anakki.data.utils.common.JwtUtil;
import com.anakki.data.utils.dealUtils.MD5SaltUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-15
 */
@Service
public class AnManagerServiceImpl extends ServiceImpl<AnManagerMapper, AnManager> implements AnManagerService {


    @Autowired
    private AnManagerMapper anManagerMapper;

    @Override
    public BasePageResult<ListManagerResponse> listManager(ListManagerRequest listManagerRequest) {
        String mail = listManagerRequest.getMail();
        String nickname = listManagerRequest.getNickname();
        IPage<AnManager> anManagerIPage = new Page<>(
                listManagerRequest.getCurrent(),
                listManagerRequest.getSize());
        QueryWrapper<AnManager> anRecordQueryWrapper = new QueryWrapper<>();
        anRecordQueryWrapper.like(null != mail, "mail", mail);
        anRecordQueryWrapper.like(null != nickname, "nickname", nickname);
        IPage<AnManager> page = page(anManagerIPage, anRecordQueryWrapper);
        List<AnManager> records = page.getRecords();
        List<ListManagerResponse> listManagerResponses = com.anakki.data.utils.common.BeanUtils.copyBeanList(records, ListManagerResponse.class);
        return new BasePageResult<>(listManagerResponses, page.getTotal());
    }

    @Override
    public Boolean createManager(CreateManagerRequest createManagerRequest) {
        AnManager anManager = new AnManager();
        BeanUtils.copyProperties(createManagerRequest, anManager);
        return save(anManager);
    }

    @Override
    public Boolean updateManager(UpdateManagerRequest updateManagerRequest) {
        Long id = updateManagerRequest.getId();
        AnManager anManager = getById(id);
        if (null == anManager) {
            throw new RuntimeException("管理员不存在");
        }
        QueryWrapper<AnManager> anManagerQueryWrapper = new QueryWrapper<>();
        anManagerQueryWrapper.eq(null != updateManagerRequest.getNickname(), "nickname", updateManagerRequest.getNickname());
        AnManager one = getOne(anManagerQueryWrapper);
        if (null != one && !one.getId().equals(id)) {
            throw new RuntimeException("昵称已存在");
        }
        BeanUtils.copyProperties(updateManagerRequest, anManager);
        return updateById(anManager);
    }

    @Override
    public String login(ManagerLoginRequest managerLoginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = managerLoginRequest.getUsername();
        String password = managerLoginRequest.getPassword();
        AnManager manager = getByNickname(username);
        if (!MD5SaltUtil.validData(password,manager.getPassword())) {
            throw new RuntimeException("用户不存在或密码错误");
        }
        ManagerToken managerToken = new ManagerToken();
        managerToken.setNickname(username);
        return JwtUtil.createMangerToken(managerToken);
    }

    @Override
    public AnManager getByNickname(String nickname) {
        QueryWrapper<AnManager> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("nickname", nickname);
        return anManagerMapper.selectOne(userQueryWrapper);
    }
}
