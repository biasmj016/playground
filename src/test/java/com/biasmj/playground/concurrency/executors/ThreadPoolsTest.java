package com.biasmj.playground.concurrency.executors;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ThreadPoolsTest {
    private static final int TASK_COUNT = 10;

    @Test
    void testExecuteTasks() throws InterruptedException {
        ThreadPools example = new ThreadPools(3);
        List<String> results = example.executeTasks(TASK_COUNT);

        Thread.sleep(2000);
        assertAll(
                () -> assertEquals(TASK_COUNT, results.size(), "All tasks should be completed."),
                () -> IntStream.rangeClosed(1, TASK_COUNT)
                        .forEach(i -> assertTrue(results.stream()
                                        .anyMatch(result -> result.contains("executed task " + i)),
                                "Task " + i + " should be completed."))
        );
    }
}