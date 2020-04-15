package com.dw.winter.common.exception;

/**
 * 业务异常
 *
 * @author duwen
 * @date 2020/4/15
 */
public final class ServiceException extends RuntimeException {

    private String code;

    private String message;

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
