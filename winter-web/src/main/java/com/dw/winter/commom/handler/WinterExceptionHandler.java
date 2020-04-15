package com.dw.winter.commom.handler;

import com.dw.winter.commom.base.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author duwen
 * @date 2020/4/15
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.dw.winter"}, annotations = Controller.class)
public class WinterExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResponse commonExceptionHandle(Exception ex) {

        return null;
    }
}
