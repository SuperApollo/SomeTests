package com.example.testjavalib;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

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
        testBlockingQueue();
        new Thread(new Runnable() {
            @Override
            public void run() {
                show();
            }
        }).start();
    }

    private static void testBlockingQueue() {
        // 声明一个容量为10的缓存队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);

        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        // 借助Executors
        ExecutorService service = Executors.newCachedThreadPool();
        // 启动线程
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer);

        // 执行10s
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producer1.stop();
        producer2.stop();
        producer3.stop();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 退出Executor
        service.shutdown();
    }

    private static void show() {

        System.out.println(Thread.currentThread().getName() + ":" + sThread.get());
    }
}
