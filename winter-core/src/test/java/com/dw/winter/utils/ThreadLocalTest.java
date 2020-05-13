package com.dw.winter.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author duwen
 * @date 2020/5/13
 */
@Slf4j
public class ThreadLocalTest {

    @Test
    public void threadLocalTest() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
                1,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("ThreadLocal-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                ThreadLocal<Long> threadLocal = ThreadLocal.withInitial(() -> 0L);
                threadLocal.set(System.currentTimeMillis());
                log.info("{},{}", Thread.currentThread().getName(), threadLocal.get());
            });
        }

        Thread.sleep(5 * 1000);
    }
}
