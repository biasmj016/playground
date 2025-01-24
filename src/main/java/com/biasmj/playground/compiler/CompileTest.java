package com.biasmj.playground.compiler;

public class CompileTest {
    public static final int NUM = 15000;

    public static int doubleValue(int x) {
        for (int i = 0; i < 100000; i++);
        return x * 2;
    }
    public static long calcSum() {
        long sum = 0;
        for (int i = 0; i < 100000; i++){
            sum += doubleValue(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {
            calcSum();
        }
    }
}
