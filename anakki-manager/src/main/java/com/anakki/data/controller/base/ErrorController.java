package com.anakki.data.controller.base;

import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.SendMailRequest;
import com.anakki.data.service.AnRecordService;
import com.anakki.data.service.AnSystemConfigService;
import com.anakki.data.utils.common.EmailUtil;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@Controller
@RequestMapping("/")
public class ErrorController {

    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private AnSystemConfigService anSystemConfigService;

    @Autowired
    private AnRecordService anRecordService;
    @ApiOperation(value = "错误页面")
    @PostMapping("/error")
    public String error() {
        return "static/error.html";
    }

}
