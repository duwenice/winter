package com.dw.winter.biz.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author duwen
 */
@Service
public class ThreadServiceImpl {

    public void thread() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(6);
        IntStream.rangeClosed(1, 5).mapToObj(i ->
                new Thread(() -> {
                    IntStream.rangeClosed(1, 20).forEach(j -> atomicInteger.getAndIncrement());
                    countDownLatch.countDown();
                })
        ).forEach(Thread::start);
        countDownLatch.await();
        System.out.println(atomicInteger.get());
    }

    public void threadPool() {
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
            threadPoolExecutor.execute(() ->
                    IntStream.rangeClosed(1, 20).forEach(j -> atomicInteger.getAndIncrement())
            );
        });
        threadPoolExecutor.shutdown();
        System.out.println(atomicInteger.get());
    }

    public void forkJoinPool() {
        AtomicInteger atomicInteger = new AtomicInteger();
        ForkJoinPool forkJoinPool = new ForkJoinPool(5);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 5).parallel().forEach(i ->
                        IntStream.rangeClosed(1, 20).forEach(j -> atomicInteger.getAndIncrement())
                )
        );
        forkJoinPool.shutdown();
        System.out.println(atomicInteger.get());
    }

    public void parallel() {
        AtomicInteger atomicInteger = new AtomicInteger();
        IntStream.rangeClosed(1, 5).parallel().forEach(i ->
                IntStream.rangeClosed(1, 20).forEach(j -> atomicInteger.getAndIncrement())
        );
        System.out.println(atomicInteger.get());
    }

    public void completableFuture() throws ExecutionException, InterruptedException {
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
        CompletableFuture.runAsync(() -> {
                    IntStream.rangeClosed(1, 5).forEach(i ->
                            IntStream.rangeClosed(1, 20).forEach(j -> atomicInteger.getAndIncrement())
                    );
                }, threadPoolExecutor
        ).get();
        System.out.println(atomicInteger.get());
    }
}
