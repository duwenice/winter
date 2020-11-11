package com.dw.winter.common.handler;

import com.dw.winter.common.base.CommonResponse;
import com.dw.winter.common.exception.ServiceException;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

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


    /**
     * validate 校验异常
     *
     * @param ex ex
     * @return CommonResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResponse validateExceptionHandle(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errors = StringUtils.EMPTY;
        if (bindingResult.hasErrors()) {
            errors = Joiner.on(",").join(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
        }
        return CommonResponse.fail(errors);
    }
}
