package com.dw.winter.biz.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dw.winter.biz.order.entity.OrderEntity;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author duwen
 * @since 2020-04-15
 */
public interface OrderMapper extends BaseMapper<OrderEntity> {


    List<OrderEntity> selectList1();
}
