package com.dw.winter.commom.exception;

/**
 * DAO层异常
 *
 * @author duwen
 * @date 2020/4/15
 */
public class DAOException extends RuntimeException {

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
