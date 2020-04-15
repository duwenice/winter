package com.dw.winter.biz.order.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dw.winter.commom.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author duwen
 * @since 2020-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("winter_order")
public class OrderEntity extends BaseEntity {


    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;


    /**
     * 下单人id
     */
    private Long userId;

    /**
     * 订单状态(0:未创建 1:创建成功 2:创建失败 3:创建中)
     */
    private Boolean orderStatus;


}
