package com.biasmj.playground.concurrency.executors;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ForkJoinTest {


    @Test
    void test() {
        String text = "test count test";
        int threshold = 10;
        Map<String, Integer> wordFrequencies = ForkJoin.countWordFrequencies(text, threshold);
        assertAll(
                () -> assertEquals(2, wordFrequencies.get("test")),
                () -> assertEquals(1, wordFrequencies.get("count"))
        );
    }

    @Test
    void test_low() {
        String text = """
                The fork/join framework is an implementation of the ExecutorService interface that helps you take advantage of multiple processors. It is designed for work that can be broken into smaller pieces recursively. The goal is to use all the available processing power to enhance the performance of your application.
                As with any ExecutorService implementation, the fork/join framework distributes tasks to worker threads in a thread pool. The fork/join framework is distinct because it uses a work-stealing algorithm. Worker threads that run out of things to do can steal tasks from other threads that are still busy.
                The center of the fork/join framework is the ForkJoinPool class, an extension of the AbstractExecutorService class. ForkJoinPool implements the core work-stealing algorithm and can execute ForkJoinTask processes.
                """;
        int threshold = 1;
        Map<String, Integer> wordFrequencies = ForkJoin.countWordFrequencies(text, threshold);
        assertEquals(4, wordFrequencies.get("fork/join"));
    }

}