package com.dw.winter.biz.thread.controller;

import com.dw.winter.biz.thread.ThreadServiceImpl;
import com.dw.winter.commom.base.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @author duwen
 */
@RestController
@RequestMapping("/thread")
public class ThreadController {

    @Resource
    private ThreadServiceImpl threadService;

    @GetMapping("/thread")
    public CommonResponse thread() throws InterruptedException {
        threadService.thread();
        return CommonResponse.success();
    }

    @GetMapping("/threadPool")
    public CommonResponse threadPool() throws InterruptedException {
        threadService.threadPool();
        return CommonResponse.success();
    }

    @GetMapping("/forkJoin")
    public CommonResponse forkJoin() throws InterruptedException {
        threadService.forkJoinPool();
        return CommonResponse.success();
    }

    @GetMapping("parallel")
    public CommonResponse parallel() {
        threadService.parallel();
        return CommonResponse.success();
    }

    @GetMapping("/completableFuture")
    public CommonResponse completableFuture() throws ExecutionException, InterruptedException {
        threadService.completableFuture();
        return CommonResponse.success();
    }
}
