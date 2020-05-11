package com.dw.winter.biz.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dw.winter.biz.order.entity.OrderEntity;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author duwen
 * @since 2020-04-15
 */
public interface IOrderService extends IService<OrderEntity> {

    /**
     * 重现deadLock
     *
     * @param userId userId
     * @throws InterruptedException ex
     */
    void deadLock(Long userId) throws InterruptedException;

    /**
     * orderA 和 orderB 进行核对
     */
    void check();
}
