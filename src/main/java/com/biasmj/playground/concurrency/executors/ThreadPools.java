package com.biasmj.playground.concurrency.executors;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreadPools {
    private final ExecutorService executorService;

    public ThreadPools(int threadPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public List<String> executeTasks(int taskCount) {
        List<String> results = IntStream.rangeClosed(1, taskCount)
                .mapToObj(taskNumber -> CompletableFuture.supplyAsync(() ->
                        Thread.currentThread().getName() + " executed task " + taskNumber, executorService))
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        executorService.shutdown();
        return results;
    }
}
