package com.anakki.data.controller;

import com.anakki.data.bean.common.ResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***
 * @author pei
 * @date 2023/11/8 16:55
 */

@Slf4j
@RestController
@Api(value = "Homepage", tags = {"Homepage"})
@RequestMapping("/homePage")
public class HomepageController {
    @ApiOperation(value = "获取余华名言")
    @RequestMapping("/yuhua-sentences")
    public ResponseDTO<String> yuhuaSentences() {
        ArrayList<String> sentences = new ArrayList<>();
        sentences.add("没有什么比时间更具有说服力了，因为时间无需通知我们就可以改变一切。 —余华《活着》");
        sentences.add("人生为了活着本身而活着，而不是为了活着之外的任何事物所活着。 —余华《活着》");
        sentences.add("你千万别糊涂，死人都还想活过来，你一个大活人可不能去死。 ——余华《活着》");

        Random random = new Random();
        int randomIndex = random.nextInt(sentences.size());
        String randomSentence = sentences.get(randomIndex);
        return ResponseDTO.succData(randomSentence);
    }

    @ApiOperation(value = "获取余华图片")
    @RequestMapping("/yuhua-image")
    public ResponseDTO<String> yuhuaImage() {
        ArrayList<String> sentences = new ArrayList<>();
        sentences.add("https://x0.ifengimg.com/ucms/2021_09/F8FD7559FF082BDF7692CE174690B3F64F9E12A2_size50_w1080_h567.jpg");
        sentences.add("https://p6.itc.cn/images01/20210412/36288e072a5640469851ba70010ed979.jpeg");
        sentences.add("https://pic.rmb.bdstatic.com/bjh/events/f3c28ef0e714c6e0fa974ab5a59fd0a4.jpeg@h_1280");

        Random random = new Random();
        int randomIndex = random.nextInt(sentences.size());
        String randomSentence = sentences.get(randomIndex);
        return ResponseDTO.succData(randomSentence);
    }
}
