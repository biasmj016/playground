package com.biasmj.playground.classLoader;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderEx {
    public static void main(String[] args) throws Exception {
        ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf('.') + 1) + ".class";
                try (InputStream is = getClass().getResourceAsStream(fileName)) {
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = is.readAllBytes();
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name, e);
                }
            }
        };
        Object object = loader.loadClass("com.biasmj.playground.classLoader.ClassLoaderEx").getDeclaredConstructor().newInstance();
        System.out.println(object.getClass());
        System.out.println(object instanceof com.biasmj.playground.classLoader.ClassLoaderEx);
    }
}