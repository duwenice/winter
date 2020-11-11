package com.dw.winter.biz.fund;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 基金类型
 *
 * @author duwen
 * @date 2020/11/11 17:53:45
 */
@AllArgsConstructor
@Getter
public enum FundTypeEnum {

    /**
     * 股票型基金
     */
    EQUITY_FUND(1, "股票型基金"),

    /**
     * 混合型基金
     */
    HYBRID_FUND(3, "混合型基金"),

    /**
     * 债券型基金
     */
    BOND_FUND(2, "债券型基金"),

    /**
     * 指数型基金
     */
    INDEX_FUND(5, "指数型基金"),
    ;

    private final int value;

    private final String desc;
}
