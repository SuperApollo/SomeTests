package com.example.testjavalib;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Apollo on 2018/1/15 14:36.
 */

public class Consumer implements Runnable {
    private BlockingQueue<String> queue;
    private static final int DEFAULT_SLEEP = 1000;
    private volatile boolean isRunning = true;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("启动消费者线程 " + Thread.currentThread().getName());
        Random random = new Random();
        try {
            while (isRunning) {
                System.out.println("正从队列取出数据...");
                String data = queue.poll(2, TimeUnit.SECONDS);
                if (null != data) {
                    System.out.println("拿到数据：" + data);
                    System.out.println("正在消费数据...");
                    Thread.sleep(random.nextInt(DEFAULT_SLEEP));

                } else {
                    //超时2s还没拿到数据认为所有生产者线程都已退出，自动退出消费线程
                    isRunning = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("退出消费者线程 " + Thread.currentThread().getName());
        }

    }
}
