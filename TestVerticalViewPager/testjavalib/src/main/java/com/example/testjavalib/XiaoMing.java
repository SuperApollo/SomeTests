package com.example.testjavalib;

/**
 * Created by Apollo on 2018/1/24 15:04.
 */

public class XiaoMing implements ITestListener{
    public XiaoMing() {
        System.out.println("xiaoming is born");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("xiaoming get the message: "+message);
    }
}
