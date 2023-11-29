package com.anakki.data.service.impl;

import com.anakki.data.bean.common.WXYYMessage;
import com.anakki.data.entity.AnUser;
import com.anakki.data.service.AnUserService;
import com.anakki.data.utils.common.WXYYUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@Service
@Slf4j
public class WxyyServiceImpl{
    @Autowired
    private AnUserService anUserService;
    public List<WXYYMessage> message(String currentNickname ,String message){
        WXYYMessage wxyyMessage = new WXYYMessage();
        wxyyMessage.setRole("user");
        wxyyMessage.setContent(message);

        AnUser user = anUserService.getByNickname(currentNickname);
        List<WXYYMessage> wxyyMessages;
        if (WXYYUtil.userWXYYMessageMap.containsKey(user.getId().toString())) {
            wxyyMessages = WXYYUtil.userWXYYMessageMap.get(user.getId().toString());
        }else{
            wxyyMessages=new ArrayList<>();
        }
        wxyyMessages.add(wxyyMessage);
        try {
            wxyyMessages = WXYYUtil.sendMessage(user.getId().toString(), wxyyMessages);
            WXYYUtil.userWXYYMessageMap.put(user.getId().toString(),wxyyMessages);
            return wxyyMessages;
        } catch (IOException e) {
            WXYYUtil.userWXYYMessageMap.remove(user.getId().toString());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException("文心一言会话失效"+e);
        }

    }
}