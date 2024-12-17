package com.biasmj.playground.concurrency.lockObjects;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeLock {
    static class Resource {
        private final String name;
        private final Lock lock = new ReentrantLock();

        public Resource(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean tryLock() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " trying to lock " + name);
            boolean success = lock.tryLock();
            System.out.println(threadName + (success ? " locked " : " failed to lock ") + name);
            return success;
        }

        public void unlock() {
            System.out.println(Thread.currentThread().getName() + " unlocking " + name);
            lock.unlock();
        }
    }

    static void lockResources(Resource res1, Resource res2, String taskName) {
        while (true) {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " attempting to lock both resources");

            if (res1.tryLock()) {
                try {
                    if (res2.tryLock()) {
                        System.out.println(taskName + ": Locked both " + res1.getName() + " and " + res2.getName());
                        return;
                    } else {
                        System.out.println(threadName + " failed to lock " + res2.getName());
                    }
                } finally {
                    res1.unlock();
                }
            }
            System.out.println(threadName + " failed to lock both resources. Retrying...");
            Thread.yield();
        }
    }
}