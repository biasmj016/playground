package com.biasmj.playground.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy {
    interface Hello {
        void sayHello();
    }
    static class HelloImpl implements Hello {
        @Override
        public void sayHello() {
            System.out.println("Hello, world!");
        }
    }
    static class DynamicProxyImpl implements InvocationHandler {
        private Object originalObj;

        Object bind(Object originalObj) {
            this.originalObj = originalObj;
            return Proxy.newProxyInstance(
                    originalObj.getClass().getClassLoader(),
                    originalObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Welcome");
            return method.invoke(originalObj, args);
        }
    }
    public static void main(String[] args) {
        Hello hello = (Hello) new DynamicProxyImpl().bind(new HelloImpl());
        hello.sayHello();
    }
}
