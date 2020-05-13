package com.dw.winter.biz.orderA.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.winter.annotation.IntervalLog;
import com.dw.winter.biz.orderA.entity.OrderAEntity;
import com.dw.winter.biz.orderA.mapper.OrderAMapper;
import com.dw.winter.biz.orderA.service.IOrderAService;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * 待核对订单表 服务实现类
 * </p>
 *
 * @author duwen
 * @since 2020-05-11
 */
@Service("orderAService")
public class OrderAServiceImpl extends ServiceImpl<OrderAMapper, OrderAEntity> implements IOrderAService {

    public static final int MAX = 1000000;

    @Override
    @IntervalLog("init")
    public void init() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(6,
                12,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNameFormat("init-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        List<Integer> collect = IntStream.rangeClosed(1, MAX).boxed().collect(Collectors.toList());
        List<List<Integer>> partition = Lists.partition(collect, 10000);
        partition.forEach(subList ->
                executor.execute(
                        () -> {
                            List<OrderAEntity> inserts = Lists.newArrayListWithCapacity(10000);
                            subList.forEach(integer -> {
                                OrderAEntity insert = new OrderAEntity();
                                insert.setOrderNo(UUID.randomUUID().toString())
                                        .setOrderMoney(BigDecimal.valueOf(integer));
                                inserts.add(insert);
                            });
                            saveBatch(inserts);
                        }
                )
        );
    }
}
