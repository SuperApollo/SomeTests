package com.example.testjavalib;

/**
 * Created by Apollo on 2018/1/24 15:05.
 */

public class XioQiang {
    private ITestListener listener;

    public void setListener(ITestListener listener) {
        this.listener = listener;
    }

    public XioQiang() {
        System.out.println("xiaoqiang is born");
    }

    public void sendMessage(String message) {
        if (listener != null)
            listener.onMessage(message);
    }
}
