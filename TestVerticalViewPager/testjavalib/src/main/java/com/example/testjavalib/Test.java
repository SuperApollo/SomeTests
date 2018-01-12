package com.example.testjavalib;

/**
 * 在Android studio中写纯java代码：
 * 1.file-->new-->new module-->java library-->your class
 * Created by Apollo on 2018/1/2 18:58.
 */

public class Test {
    //ThreadLocal相当于map集合，它的键是当前线程，值是自己设置的值，如果主线程set值，子线程是get不到的
    private static ThreadLocal<String> sThread;

    public static void main(String[] args) {
        System.out.println("test java on studio");
        sThread = new ThreadLocal<>();
        sThread.set("test threadlocal");
        show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                show();
            }
        }).start();
    }

    private static void show() {
        System.out.println(sThread.get());
    }
}
