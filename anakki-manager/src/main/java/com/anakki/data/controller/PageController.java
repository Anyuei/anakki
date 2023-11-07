package com.anakki.data.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * @author pei
 * @date 2023/11/7 17:42
 */
@Slf4j
@Controller
@Api(value = "主页", tags = {"主页"})
@RequestMapping("/")
public class PageController {

}
