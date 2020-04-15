package com.dw.winter.biz.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 订单状态
 *
 * @author duwen
 * @date 2020/4/15
 */
public enum OrderStatusEnum implements IEnum<Integer> {
    /**
     * 未创建
     */
    NOT_CREATE(0, "未创建"),
    /**
     * 创建成功
     */
    CREATE_SUCCESS(1, "创建成功"),
    /**
     * 创建失败
     */
    CREATE_FAIL(2, "创建失败"),
    /**
     * 创建中
     */
    CREATING(3, "创建中");


    private final Integer value;

    private final String desc;

    OrderStatusEnum(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    @JsonValue
    public Integer getValue() {
        return this.value;
    }
}
