package com.dw.winter.biz.order.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.winter.annotation.IntervalLog;
import com.dw.winter.biz.enums.StatusEnum;
import com.dw.winter.biz.order.entity.OrderEntity;
import com.dw.winter.biz.order.mapper.OrderMapper;
import com.dw.winter.biz.order.service.IOrderService;
import com.dw.winter.biz.orderA.entity.OrderAEntity;
import com.dw.winter.biz.orderA.mapper.OrderAMapper;
import com.dw.winter.biz.orderB.entity.OrderBEntity;
import com.dw.winter.biz.orderB.mapper.OrderBMapper;
import com.dw.winter.common.exception.ServiceException;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private OrderAMapper orderAMapper;

    @Resource
    private OrderBMapper orderBMapper;

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

    @Override
    @IntervalLog(value = "move")
    public void move() {
        // 方案二 生产者消费者模式
        ArrayBlockingQueue<OrderAEntity> orderQueue = new ArrayBlockingQueue<>(10000);
        // 生产者
        CompletableFuture.runAsync(() -> {
            int minId = 100001;
            int maxId = 200001;
            while (minId < maxId) {
                int end = minId + 1000 < maxId ? minId + 1000 : maxId;
                List<OrderAEntity> list = orderAMapper.selectList(new LambdaQueryWrapper<OrderAEntity>()
                        .lt(OrderAEntity::getId, end).ge(OrderAEntity::getId, minId));
                list.forEach(orderA -> {
                            try {
                                orderQueue.put(orderA);
                            } catch (InterruptedException e) {
                                throw new ServiceException("001", "supply", e);
                            }
                        }
                );
                minId = end;
            }
        }, new ThreadPoolExecutor(1,
                2,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("supply-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy()
        )).exceptionally(e -> {
            log.error("error", e);
            return null;
        });

        // 消费者
        CompletableFuture.runAsync(() -> {
            OrderAEntity orderA = null;
            try {
                orderA = orderQueue.take();
            } catch (InterruptedException e) {
                throw new ServiceException("002", "consume", e);
            }
            if (Objects.isNull(orderA)) {

            }
        });

    }

    /**
     * 方案一 同步处理
     */
    private void planA() {
        List<OrderAEntity> list = orderAMapper.selectList(new LambdaQueryWrapper<OrderAEntity>()
                .lt(OrderAEntity::getId, 100001).gt(OrderAEntity::getId, 0));
        System.out.println(list.size());
        List<List<OrderAEntity>> partition = Lists.partition(list, 1000);
        partition.forEach(subList ->
                subList.forEach(orderA -> {
                    OrderBEntity orderB = new OrderBEntity();
                    BeanUtils.copyProperties(orderA, orderB);
                    orderBMapper.insert(orderB);
                    OrderAEntity update = new OrderAEntity();
                    update.setId(orderA.getId());
                    update.setOrderStatus(StatusEnum.MOVE);
                    orderAMapper.updateById(update);
                })
        );
    }
}
