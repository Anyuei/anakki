package com.anakki.data.controller.api;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.WXYYMessage;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.CreateCommentsRequest;
import com.anakki.data.service.AnFriendsCommentService;
import com.anakki.data.service.AnUserService;
import com.anakki.data.service.impl.WxyyServiceImpl;
import com.anakki.data.utils.common.WXYYUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static net.sf.jsqlparser.parser.feature.Feature.comment;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@RestController
@RequestMapping("/api/anakki/wxxy")
public class AnWxxyController {

    @Autowired
    private AnUserService  anUserService;

    @Autowired
    private AnFriendsCommentService anFriendsCommentService;

    @Autowired
    private WxyyServiceImpl wxyyServicel;
    @ApiOperation(value = "发送聊天内容")
    @GetMapping("/message")
    public ResponseDTO<List<WXYYMessage>> message(String message) {
        String currentNickname = BaseContext.getCurrentNickname();
        List<WXYYMessage> reMessages = wxyyServicel.message(currentNickname, message);
        return ResponseDTO.succData(reMessages);
    }
}
