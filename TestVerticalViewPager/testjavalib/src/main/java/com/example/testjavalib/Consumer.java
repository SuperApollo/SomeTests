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
        System.out.println("�����������߳� " + Thread.currentThread().getName());
        Random random = new Random();
        try {
            while (isRunning) {
                System.out.println("���Ӷ���ȡ������...");
                String data = queue.poll(2, TimeUnit.SECONDS);
                if (null != data) {
                    System.out.println("�õ����ݣ�" + data);
                    System.out.println("������������...");
                    Thread.sleep(random.nextInt(DEFAULT_SLEEP));

                } else {
                    //��ʱ2s��û�õ�������Ϊ�����������̶߳����˳����Զ��˳������߳�
                    isRunning = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("�˳��������߳� " + Thread.currentThread().getName());
        }

    }
}
