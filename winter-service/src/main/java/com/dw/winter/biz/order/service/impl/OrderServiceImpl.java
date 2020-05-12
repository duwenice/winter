package com.dw.winter.biz.order.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.winter.biz.order.entity.OrderEntity;
import com.dw.winter.biz.order.mapper.OrderMapper;
import com.dw.winter.biz.order.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deadLock(Long userId) throws InterruptedException {
        remove(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getUserId, userId).last("limit 1"));
        Thread.sleep(3 * 1000);
        OrderEntity insert = new OrderEntity();
        insert.setUserId(userId);
        save(insert);
    }

    @Override
    public void check() {
        
    }
}
