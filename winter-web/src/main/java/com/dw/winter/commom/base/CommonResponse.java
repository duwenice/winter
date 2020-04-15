package com.dw.winter.commom.base;

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
    }
}
