package com.biasmj.playground.concurrency.lockObjects;

import org.junit.jupiter.api.Test;

import static com.biasmj.playground.concurrency.lockObjects.SafeLock.Resource;
import static com.biasmj.playground.concurrency.lockObjects.SafeLock.lockResources;

class SafeLockTest {

    @Test
    void testLockResources() {
        Resource resource1 = new Resource("Resource1");
        Resource resource2 = new Resource("Resource2");

        Runnable task1 = () -> lockResources(resource1, resource2, "Task1");
        Runnable task2 = () -> lockResources(resource2, resource1, "Task2");

        Thread thread1 = new Thread(task1, "Thread-1");
        Thread thread2 = new Thread(task2, "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Test interrupted: " + e.getMessage());
        }
    }
}
