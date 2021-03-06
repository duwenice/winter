package com.dw.winter.biz.order.controller;


import com.dw.winter.biz.order.dto.OrderCreateDTO;
import com.dw.winter.biz.order.entity.OrderEntity;
import com.dw.winter.biz.order.mapper.OrderMapper;
import com.dw.winter.biz.order.service.IOrderService;
import com.dw.winter.biz.orderA.service.IOrderAService;
import com.dw.winter.common.base.CommonResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author duwen
 * @since 2020-04-15
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Api(tags = "order")
public class OrderController implements MeterBinder {

    @Resource
    private IOrderService orderService;

    @Resource
    private IOrderAService orderAService;

    private Counter orderCounter = null;

    @PostMapping("/create")
    @ApiOperation("create")
    public CommonResponse create(@RequestBody @Validated OrderCreateDTO dto) {
        orderCounter.increment();
        return CommonResponse.success();
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.orderCounter = meterRegistry.counter("order.count");
    }

    @GetMapping("/deadLock")
    @ApiOperation("deadLock")
    public CommonResponse deadLock() {
        List<Long> userIds = LongStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());
        userIds.parallelStream().forEach(userId -> {
            try {
                orderService.deadLock(userId);
            } catch (InterruptedException e) {
                log.error("error", e);
            }
        });
        return CommonResponse.success();
    }

    @GetMapping("/initOrderA")
    public void initOrderA() {
        orderAService.init();
    }

    @GetMapping("/move")
    public void move() {
        orderService.move();
    }

    @GetMapping("/insert")
    public void insert(int month) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCreateTime(LocalDateTime.of(2020, month, 1, 0, 0, 0));
        orderService.save(orderEntity);
    }

    @Resource
    private OrderMapper orderMapper;
    @GetMapping("/list")
    public CommonResponse<List<OrderEntity>> list() {
        return CommonResponse.success(orderMapper.selectList(null));
    }
}
