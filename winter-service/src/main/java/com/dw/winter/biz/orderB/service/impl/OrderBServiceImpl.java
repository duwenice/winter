package com.dw.winter.biz.orderB.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.winter.biz.orderB.entity.OrderBEntity;
import com.dw.winter.biz.orderB.mapper.OrderBMapper;
import com.dw.winter.biz.orderB.service.IOrderBService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 待核对订单表 服务实现类
 * </p>
 *
 * @author duwen
 * @since 2020-05-11
 */
@Service
public class OrderBServiceImpl extends ServiceImpl<OrderBMapper, OrderBEntity> implements IOrderBService {

}
