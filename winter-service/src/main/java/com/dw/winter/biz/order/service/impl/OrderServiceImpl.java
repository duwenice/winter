package com.dw.winter.biz.order.service.impl;


import com.dw.winter.biz.order.entity.OrderEntity;
import com.dw.winter.biz.order.mapper.OrderMapper;
import com.dw.winter.biz.order.service.IOrderService;
import com.dw.winter.common.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author duwen
 * @since 2020-04-15
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, OrderEntity> implements IOrderService {

}
