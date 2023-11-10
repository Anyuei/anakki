package com.anakki.data.controller;

import com.anakki.data.bean.common.ResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: MainController
 * Description:
 *
 * @author Anakki
 * @date 2023/5/26 1:10
 */
@Slf4j
@RestController
@Api(value = "根访问", tags = {"根访问"})
@RequestMapping("/")
public class MainController {
    @ApiOperation(value = "健康检查")
    @RequestMapping("/checkHeath")
    public ResponseDTO<Boolean> checkHeath() {
        return ResponseDTO.succData(true);
    }
}
