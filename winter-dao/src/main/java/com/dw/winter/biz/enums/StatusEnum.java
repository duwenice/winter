package com.dw.winter.biz.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author duwen
 * @date 2020/5/13
 */
public enum StatusEnum implements IEnum<Integer> {
    /**
     * move
     */
    MOVE(1, "move"),
    /**
     * check
     */
    CHECK(2, "check");

    private final int value;

    private final String desc;

    StatusEnum(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
