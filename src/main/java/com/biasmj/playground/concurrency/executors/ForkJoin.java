package com.biasmj.playground.concurrency.executors;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class ForkJoin {
    static class WordCountTask extends RecursiveTask<Map<String, Integer>> {
        private final int threshold;
        private final String[] words;
        private final int start;
        private final int end;

        public WordCountTask(String[] words, int start, int end, int threshold) {
            this.words = words;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
        }

        @Override
        protected Map<String, Integer> compute() {
            if (end - start <= threshold) return countWordsDirectly();

            int mid = start + (end - start) / 2;

            WordCountTask leftTask = new WordCountTask(words, start, mid, threshold);
            WordCountTask rightTask = new WordCountTask(words, mid, end, threshold);

            leftTask.fork();
            Map<String, Integer> rightResult = rightTask.compute();
            Map<String, Integer> leftResult = leftTask.join();

            return mergeResults(leftResult, rightResult);
        }

        private Map<String, Integer> countWordsDirectly() {
            return Arrays.stream(words, start, end)
                    .collect(Collectors.groupingBy(word -> word, Collectors.summingInt(word -> 1)));
        }

        private Map<String, Integer> mergeResults(Map<String, Integer> left, Map<String, Integer> right) {
            right.forEach((key, value) -> left.merge(key, value, Integer::sum));
            return left;
        }
    }

    public static Map<String, Integer> countWordFrequencies(String text, int threshold) {
        String[] words = text.split("\\s+");
        ForkJoinPool pool = new ForkJoinPool();
        WordCountTask task = new WordCountTask(words, 0, words.length, threshold);
        return pool.invoke(task);
    }
}