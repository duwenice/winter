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

    /**
     * orderA表move到orderB表,并更新orderA表状态
     */
    void move();

    /**
     * method for test transaction required
     */
    void methodForRequired();

    /**
     * method for test transaction require new
     */
    void methodForRequireNew();

    /**
     * method for test transaction nested
     */
    void methodForNested();

    /**
     * method for test transaction supports
     */
    void methodForSupports();

    /**
     * method for test transaction not supports
     */
    void methodForNotSupports();
}
