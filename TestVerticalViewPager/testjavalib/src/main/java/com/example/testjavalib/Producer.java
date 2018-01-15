package com.example.testjavalib;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟生产者线程
 * Created by Apollo on 2018/1/15 14:24.
 */

public class Producer implements Runnable {
    private BlockingQueue queue;
    private volatile boolean isRunning = true;
    private static AtomicInteger count = new AtomicInteger();
    private static final int DEFAULT_SLEEP = 1000;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String data = null;
        Random random = new Random();
        System.out.println("启动线程 " + Thread.currentThread().getName());

        try {
            while (isRunning) {
                System.out.println("正在生产数据...");
                Thread.sleep(random.nextInt(DEFAULT_SLEEP));
                data = "data: " + count.incrementAndGet();
                System.out.println("将数据 " + data + "放入队列...");
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("放入数据失败...");
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("退出生产者线程 " + Thread.currentThread().getName());
        }


    }

    public void stop() {
        isRunning = false;
    }
}
