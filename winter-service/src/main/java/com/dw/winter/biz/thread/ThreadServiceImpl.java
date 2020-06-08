package com.dw.winter.biz.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author duwen
 */
@Service
@Slf4j
public class ThreadServiceImpl {

    public void thread() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        IntStream.rangeClosed(1, 5).mapToObj(i ->
                new Thread(() -> {
                    IntStream.rangeClosed(1, 20).forEach(j -> atomicInteger.getAndIncrement());
                    log.info("threadName:{}", Thread.currentThread().getName());
                    countDownLatch.countDown();
                })
        ).forEach(Thread::start);
        countDownLatch.await();
        log.info(String.valueOf(atomicInteger.get()));
    }

    public void threadPool() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                6,
                12,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNameFormat("increase-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        IntStream.rangeClosed(1, 5).forEach(i -> {
            threadPoolExecutor.execute(() -> {
                        IntStream.rangeClosed(1, 20).forEach(j -> atomicInteger.getAndIncrement());
                        log.info("threadName:{}", Thread.currentThread().getName());
                    }
            );
        });
        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);
        log.info(String.valueOf(atomicInteger.get()));
    }

    public void forkJoinPool() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ForkJoinPool forkJoinPool = new ForkJoinPool(5);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 5).parallel().forEach(i -> {
                    IntStream.rangeClosed(1, 20).forEach(j -> atomicInteger.getAndIncrement());
                    log.info("threadName:{}", Thread.currentThread().getName());
                }
                )
        );
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(10, TimeUnit.SECONDS);
        log.info(String.valueOf(atomicInteger.get()));
    }

    public void parallel() {
        AtomicInteger atomicInteger = new AtomicInteger();
        IntStream.rangeClosed(1, 5).parallel().forEach(i -> {
                    IntStream.rangeClosed(1, 20).forEach(j -> atomicInteger.getAndIncrement());
                    log.info("threadName:{}", Thread.currentThread().getName());
                }
        );
        log.info(String.valueOf(atomicInteger.get()));
    }

    public void completableFuture() throws ExecutionException, InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                6,
                12,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNameFormat("completableFuture-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        CompletableFuture.runAsync(() -> {
                    IntStream.rangeClosed(1, 5).forEach(i -> {
                                IntStream.rangeClosed(1, 20).forEach(j -> atomicInteger.getAndIncrement());
                                log.info("threadName:{}", Thread.currentThread().getName());
                            }
                    );
                }, threadPoolExecutor
        ).get();
        log.info(String.valueOf(atomicInteger.get()));
    }
}
