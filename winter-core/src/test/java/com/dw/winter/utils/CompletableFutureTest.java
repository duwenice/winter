package com.dw.winter.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author duwen
 * @date 2020/5/12
 */
@Slf4j
public class CompletableFutureTest {

    @Test
    public void completedFutureTest() throws Exception {
        String str = "CompletableFuture";
        CompletableFuture<String> future = CompletableFuture.completedFuture(str);
        assertThat(future.get(), is(str));
    }

    @Test
    public void runAsyncTest() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> log.info("thread:[{}]", Thread.currentThread()));
        assertTrue(future.isDone());
    }

    @Test
    public void supplyAsyncTest() throws Exception {
        String str = "CompletableFuture";
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("thread: [{}]", Thread.currentThread());
            return "CompletableFuture";
        });
        assertThat(future.get(), is(str));
    }
}
