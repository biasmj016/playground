package com.biasmj.playground.compiler;

public class BackedgeCounterTest {
    public static void main(String[] args) {
        testLoop(10_000_000);
    }

    public static void testLoop(int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        System.out.println("Sum: " + sum);
    }
}
//-XX:+PrintCompilation으로 루프정보 출력 확인
