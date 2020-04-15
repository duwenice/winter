package com.dw.winter.biz.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.winter.biz.order.entity.OrderEntity;
import com.dw.winter.biz.order.mapper.OrderMapper;
import com.dw.winter.biz.order.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author duwen
 * @since 2020-04-15
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements IOrderService {

}
