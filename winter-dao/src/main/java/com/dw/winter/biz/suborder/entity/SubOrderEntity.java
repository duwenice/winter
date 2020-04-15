package com.dw.winter.biz.suborder.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dw.winter.commom.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 子订单表
 * </p>
 *
 * @author duwen
 * @since 2020-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("winter_sub_order")
public class SubOrderEntity extends BaseEntity {


    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 子订单号
     */
    private String subOrderNo;

    /**
     * sku id
     */
    private Long skuId;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 子订单金额
     */
    private BigDecimal subOrderMoney;


}
