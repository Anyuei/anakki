package com.anakki.data.handler;

import com.anakki.data.bean.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * ClassName: GlobalExceptionHandler
 * Description:
 *
 * @author Anakki
 * @date 2023/10/5 21:48
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseDTO<String> handleException(Exception e){
        log.error("### GlobalExceptionHandler.handleException", e);
        return ResponseDTO.errorData(e.getMessage());
    }
}
