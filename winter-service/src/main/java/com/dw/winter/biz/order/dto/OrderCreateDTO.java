package com.dw.winter.biz.order.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author duwen
 */
@Data
public class OrderCreateDTO {

    @Valid
    @NotNull
    private List<OrderItem> orderItems;

    /**
     * 备注
     */
    private String remark;


    @Data
    @Accessors(chain = true)
    public static class OrderItem {

        /**
         * 商品编号
         */
        @NotNull
        private Integer skuId;
        /**
         * 数量
         */
        @NotNull
        @Max(value = 1000)
        private Integer quantity;
    }
}
