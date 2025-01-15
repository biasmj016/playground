package com.biasmj.playground.classLoader;

public class ClassLoaderEx2 extends ClassLoader {

    public ClassLoaderEx2(ClassLoader parent) {
        super(parent != null ? parent : ClassLoader.getSystemClassLoader());  // 부모가 null이면 시스템 클래스 로더 사용
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
           System.out.println("1. 요청된 클래스가 이미 로드되어 있는지 확인");
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                try {
                    System.out.println("2. 부모 로더의 loadClass 호출 (부모가 null이면 부트스트랩 클래스 로더 사용)");
                    c = getParent().loadClass(name);
                } catch (ClassNotFoundException e) {
                    System.out.println("부모 로더가 클래스를 못 찾음");
                    c = findClass(name);
                }
            }
            if (resolve) resolveClass(c);
            return c;
        }
    }

    public static void main(String[] args) {
        try {
            ClassLoaderEx2 loader = new ClassLoaderEx2(ClassLoader.getSystemClassLoader());
            Class<?> clazz = loader.loadClass("java.lang.String", true);
            System.out.println("Loaded class: " + clazz.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
