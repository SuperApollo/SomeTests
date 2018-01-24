package com.example.testjavalib;

/**
 * Created by Apollo on 2018/1/24 15:07.
 */

public class TestSendMessage implements ITestListener{
    public static void main(String[] args) {
        XiaoMing xiaoMing = new XiaoMing();
        final XioQiang xioQiang = new XioQiang();
        xioQiang.setListener(xiaoMing);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                xioQiang.sendMessage("xiaoming i am xiaoqiang");
            }
        }).start();

    }

    @Override
    public void onMessage(String message) {
        System.out.println("message: "+message);
    }
}
