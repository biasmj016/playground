package com.biasmj.playground.dynamicDispatch;

public class DynamicDispatch {
    static abstract class Human{
        protected abstract void sayHello();
    }

    static class Man extends Human {
        @Override
        protected void sayHello() {
            System.out.println("Hello, gentleman!");
        }
    }

    static class Woman extends Human {
        @Override
        protected void sayHello() {
            System.out.println("Hello, lady!");
        }
    }
}
