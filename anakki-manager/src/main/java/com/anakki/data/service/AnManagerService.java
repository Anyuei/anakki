package com.anakki.data.service;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.AnManager;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.CreateManagerRequest;
import com.anakki.data.entity.request.ListManagerRequest;
import com.anakki.data.entity.request.ManagerLoginRequest;
import com.anakki.data.entity.response.ListManagerResponse;
import com.anakki.data.entity.request.UpdateManagerRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-15
 */
public interface AnManagerService extends IService<AnManager> {

    BasePageResult<ListManagerResponse> listManager(ListManagerRequest listManagerRequest);

    Boolean createManager(CreateManagerRequest createManagerRequest);

    Boolean updateManager(UpdateManagerRequest updateManagerRequest);

    String login(ManagerLoginRequest managerLoginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    AnManager getByNickname(String nickname);
}
