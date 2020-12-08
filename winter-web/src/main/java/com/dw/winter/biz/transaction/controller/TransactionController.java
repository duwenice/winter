package com.dw.winter.biz.transaction.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dw.winter.biz.order.entity.OrderEntity;
import com.dw.winter.biz.order.service.IOrderService;
import com.dw.winter.common.base.CommonResponse;
import com.dw.winter.common.exception.ServiceException;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author duwen
 */
@RestController
@RequestMapping("/trans")
@Slf4j
public class TransactionController {

    @Resource
    private IOrderService orderService;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(
            6,
            12,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100),
            new ThreadFactoryBuilder().setNameFormat("pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    @GetMapping
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse<?> trans() throws InterruptedException {
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("transactionName:{}", currentTransactionName);

        orderService.save(new OrderEntity().setOrderNo("2020060804"));

        log.info("开始注册事务提交后动作");
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                executor.execute(() -> {
                            OrderEntity one = orderService.getOne(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getOrderNo, "2020060804"));
                            log.info("after commit, entity :{}", one);
                        }
                );
            }
        });
        log.info("注册事务提交后动作完成");
        Thread.sleep(10 * 1000);
        return CommonResponse.success();
    }

    @GetMapping("/test")
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse<?> test() throws InterruptedException {
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("transactionName:{}", currentTransactionName);

        List<Integer> ids = Lists.newArrayList(1, 2, 3);
        log.info("开始注册事务提交后动作");
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                ids.forEach(System.out::println);
            }
        });
        log.info("注册事务提交后动作完成");
        Thread.sleep(10 * 1000);
        return CommonResponse.success();
    }

    @GetMapping("/required")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CommonResponse<?> required() {
        // 存在事务则加入，不存在则新建
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("transactionName:{}", currentTransactionName);
        orderService.save(new OrderEntity().setOrderNo("requiredOut"));
        try {
            orderService.methodForRequired();
        } catch (Exception e) {
            log.info("子事务异常");
        }
        return CommonResponse.success();
    }


    @GetMapping("/requireNew")
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse<?> requireNew() {
        // required new 独立事务子操作，比如说记录操作记录，但是影响性能
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("transactionName:{}", currentTransactionName);
        orderService.methodForRequireNew();
        throw new RuntimeException();
    }

    @GetMapping("/nested")
    @Transactional(rollbackFor = Error.class)
    public CommonResponse<?> nested() {
        // nested 外部无事务则新建，外部有事务 嵌套并且内部事务也能单独回滚
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("transactionName:{}", currentTransactionName);
        orderService.save(new OrderEntity().setOrderNo("methodForNestedOut"));
        orderService.methodForNested();
        throw new RuntimeException();
    }

    @GetMapping("/mandatory")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public CommonResponse<?> mandatory() {
        // 存在则加入 不存在事务则抛出异常
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("transactionName:{}", currentTransactionName);
        return CommonResponse.success();
    }

    @GetMapping("/supports")
    public CommonResponse<?> supports() {
        // 存在则加入，不存在则非事务
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("transactionName:{}", currentTransactionName);
        orderService.methodForSupports();
        return CommonResponse.success();
    }

    @GetMapping("/notSupports")
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse<?> notSupports() {
        // 非事务
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("transactionName:{}", currentTransactionName);
        orderService.methodForNotSupports();
        return CommonResponse.success();
    }

    @GetMapping("/never")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NEVER)
    public CommonResponse<?> never() {
        // 存在则异常
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("transactionName:{}", currentTransactionName);
        return CommonResponse.success();
    }
}
