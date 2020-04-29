package com.dw.winter.biz.order.controller;


import com.dw.winter.biz.order.dto.OrderCreateDTO;
import com.dw.winter.biz.order.service.IOrderService;
import com.dw.winter.commom.base.CommonResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
