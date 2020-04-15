package com.dw.winter.biz.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author duwen
 * @date 2020/4/15
 */
public enum YesOrNoEnum implements IEnum<Integer> {
    /**
     * 否
     */
    NO(0, "否"),
    /**
     * 是
     */
    YES(1, "是"),
    ;

    private final Integer value;

    private final String desc;

    YesOrNoEnum(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    @JsonValue
    public Integer getValue() {
        return this.value;
    }
}
