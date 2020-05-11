package com.dw.winter.biz.orderB.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dw.winter.commom.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 待核对订单表
 * </p>
 *
 * @author duwen
 * @since 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("winter_order_b")
public class OrderBEntity extends BaseEntity {


    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 订单状态(0:待核对 1:核对一致 2:核对不一致)
     */
    private Boolean orderStatus;


}
