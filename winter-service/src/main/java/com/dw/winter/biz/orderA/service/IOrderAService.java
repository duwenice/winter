package com.dw.winter.biz.orderA.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dw.winter.biz.orderA.entity.OrderAEntity;

/**
 * <p>
 * 待核对订单表 服务类
 * </p>
 *
 * @author duwen
 * @since 2020-05-11
 */
public interface IOrderAService extends IService<OrderAEntity> {

    /**
     * init
     */
    void init();

}
