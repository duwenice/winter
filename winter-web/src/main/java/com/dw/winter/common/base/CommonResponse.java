package com.dw.winter.common.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.util.Strings;

/**
 * 通用返回
 *
 * @author duwen
 * @date 2020/4/15
 */
@Data
@Accessors(chain = true)
public class CommonResponse<T> {

    /**
     * code
     */
    private String code;

    /**
     * message
     */
    private String message;

    /**
     * data
     */
    private T data;

    public static CommonResponse success() {
        return new CommonResponse().setCode(Constants.SUCCESS).setMessage(Strings.EMPTY);
    }

    public static <V> CommonResponse<V> success(V v) {
        return new CommonResponse().setCode(Constants.SUCCESS).setMessage(Strings.EMPTY).setData(v);
    }

    public static CommonResponse fail() {
        return new CommonResponse().setCode(Constants.FAIL).setMessage(Constants.SYSTEM_ERROR);
    }

    public static CommonResponse fail(String message) {
        return new CommonResponse().setCode(Constants.FAIL).setMessage(message);
    }

    public static CommonResponse fail(String code, String message) {
        return new CommonResponse().setCode(code).setMessage(message);
    }

    /**
     * 常量
     */
    public interface Constants {
        /**
         * 成功
         */
        String SUCCESS = "0";

        /**
         * 失败
         */
        String FAIL = "-1";

        /**
         * 系统异常
         */
        String SYSTEM_ERROR = "系统异常";
    }
}
