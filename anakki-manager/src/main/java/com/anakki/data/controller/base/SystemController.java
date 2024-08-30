package com.anakki.data.controller.base;

import com.anakki.data.bean.common.RandomList;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.AnGiftLog;
import com.anakki.data.entity.request.DeviceInfoRequest;
import com.anakki.data.entity.request.RandomNameRequest;
import com.anakki.data.entity.request.SendMailRequest;
import com.anakki.data.entity.response.DailyVisitCountResponse;
import com.anakki.data.service.*;
import com.anakki.data.utils.IPUtils;
import com.anakki.data.utils.common.EmailUtil;
import com.anakki.data.util.RandomNameUtil;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-14
 */
@RestController
@RequestMapping("/base/system/")
public class SystemController {

    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private AnSystemConfigService anSystemConfigService;

    @Autowired
    private AnRecordService anRecordService;

    @Autowired
    private AnStatisticService anStatisticService;

    @Autowired
    private AnGiftLogService anGiftLogService;

    @Autowired
    private AnPathStatisticLogService anPathStatisticLogService;
    @ApiOperation(value = "发送邮件")
    @PostMapping("/send-mail")
    public ResponseDTO<Boolean> sendMail(@RequestBody SendMailRequest sendMailRequest) {
        emailUtil.sendMessage(
                sendMailRequest.getTo(),
                sendMailRequest.getSubject(),
                sendMailRequest.getContent());
        return ResponseDTO.succData(true);
    }
    @ApiOperation(value = "发送邮件")
    @GetMapping("/healthy")
    public ResponseDTO<String> healthy() {
        return ResponseDTO.succData("ok");
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

    @ApiOperation(value = "获取图文类型")
    @GetMapping("/record-type")
    public ResponseDTO<List<String>> recordType() {
        List<String> recordTypes = anRecordService.getRecordTypes();
        return ResponseDTO.succData(recordTypes);
    }

    @ApiOperation(value = "获取配置(数值配置)")
    @GetMapping("/getNumberConfigValue")
    public ResponseDTO<Long>  getNumberConfigValue(String key) {
        Long numberConfigValue = anSystemConfigService.getNumberConfigValue(key);
        return ResponseDTO.succData(numberConfigValue);
    }
    @ApiOperation(value = "获取配置(字符配置)")
    @GetMapping("/getStringConfigValue")
    public ResponseDTO<String>  getStringConfigValue(String key) {
        String s = anSystemConfigService.getStringConfigValue(key);
        return ResponseDTO.succData(s);
    }

    @ApiOperation(value = "网页访问")
    @GetMapping("/view")
    public ResponseDTO<Boolean> view(String moduleType,HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);
        //统计模块访问数
        anStatisticService.increaseByName(moduleType,ipAddr);
        return ResponseDTO.succData(true);
    }




    @ApiOperation(value = "随机名字开奖")
    @GetMapping("/getRandomName")
    public ResponseDTO<List<AnGiftLog>> getRandomName() {
        List<AnGiftLog> randomNames = anGiftLogService.getRandomNames();
        return ResponseDTO.succData(randomNames);
    }

    @ApiOperation(value = "网页访问量统计")
    @GetMapping("/siteViewData")
    public ResponseDTO<Boolean> getSiteViewData() {
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "网页访问量总计")
    @GetMapping("/siteViewCount")
    public ResponseDTO<Integer> siteViewCount() {
        return ResponseDTO.succData(anPathStatisticLogService.count());
    }

    @ApiOperation(value = "网页访问量总计")
    @PostMapping("/device-info")
    public ResponseDTO<Boolean> deviceInfo(@RequestBody DeviceInfoRequest deviceInfoRequest,HttpServletRequest request) {
        anPathStatisticLogService.log("/base/system/device-info",IPUtils.getIpAddr(request),deviceInfoRequest.getJson());
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "每日访问量")
    @GetMapping("/daily-visit-counts")
    public ResponseDTO<List<DailyVisitCountResponse>> getDailyVisitCounts(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        return ResponseDTO.succData(anPathStatisticLogService.getDailyVisitCounts(startDate, endDate));
    }

    @ApiOperation(value = "主页背景")
    @GetMapping("/main-cover")
    public ResponseDTO<String> mainCover(String page) {
        return ResponseDTO.succData(anSystemConfigService.getStringConfigValue(page));
    }
}
