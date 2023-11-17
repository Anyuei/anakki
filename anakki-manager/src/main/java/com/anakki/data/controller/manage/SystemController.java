package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.SendMailRequest;
import com.anakki.data.utils.common.EmailUtil;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@RestController
@RequestMapping("/system/")
public class SystemController {

    @Autowired
    private EmailUtil emailUtil;

    @ApiOperation(value = "发送邮件")
    @PostMapping("/send-mail")
    public ResponseDTO<Boolean> sendMail(@RequestBody SendMailRequest sendMailRequest) {
        emailUtil.sendMessage(
                sendMailRequest.getTo(),
                sendMailRequest.getSubject(),
                sendMailRequest.getContent());
        return ResponseDTO.succData(true);
    }

    @PostMapping("/verify")
    @ResponseBody
    //code是前端验证码的name名
    public String verify(String code, HttpServletRequest request){
        //Verification Captcha
        boolean flag = HappyCaptcha.verification(request,code,true);
        //清除session信息(生成的验证码)
        HappyCaptcha.remove(request);
        if(flag){
            System.out.println("yes");
            return "yes";
        }
        return "no";
    }
    @GetMapping("/captcha")
    public void happyCaptcha(HttpServletRequest request, HttpServletResponse response){
        //1、默认选择静态图片
        //HappyCaptcha.require(request,response).build().finish();
        //2、默认选择动态图片
        HappyCaptcha.require(request,response)
                .style(CaptchaStyle.IMG)
                .build().finish();
        //3、自定义样式
//        HappyCaptcha.require(request,response)
//                .style(CaptchaStyle.ANIM)			//设置展现样式为动画
//                .type(CaptchaType.CHINESE)			//设置验证码内容为汉字
//                .length(6)							//设置字符长度为6
//                .width(220)							//设置动画宽度为220
//                .height(80)							//设置动画高度为80
//                .font(Fonts.getInstance().zhFont())	//设置汉字的字体
//                .build().finish();
    }
}
