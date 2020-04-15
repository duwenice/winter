package com.dw.winter.common.exception;

/**
 * 业务异常
 *
 * @author duwen
 * @date 2020/4/15
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
