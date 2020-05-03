package com.dw.winter.biz.order.controller;


import com.dw.winter.biz.order.dto.OrderCreateDTO;
import com.dw.winter.biz.order.service.IOrderService;
import com.dw.winter.commom.base.CommonResponse;
import com.google.common.collect.Lists;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
                e.printStackTrace();
            }
        });
        return CommonResponse.success();
    }
}
