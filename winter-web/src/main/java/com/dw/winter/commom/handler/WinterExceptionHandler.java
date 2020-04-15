package com.dw.winter.commom.handler;

import com.dw.winter.commom.base.CommonResponse;
import com.dw.winter.common.exception.ServiceException;
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

    /**
     * 统一处理业务异常
     *
     * @param ex 业务异常
     * @return CommonResponse
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public CommonResponse commonExceptionHandle(ServiceException ex) {
        return CommonResponse.fail(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResponse runTimeExceptionHandle(Exception ex) {
        log.error(ex.getMessage(), ex);
        return CommonResponse.fail();
    }
}
