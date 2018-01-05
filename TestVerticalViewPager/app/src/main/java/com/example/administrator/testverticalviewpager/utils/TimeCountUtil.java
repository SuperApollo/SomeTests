package com.example.administrator.testverticalviewpager.utils;

/**
 * Created by Apollo on 2018/1/5 16:25.
 */

public class TimeCountUtil {
    private volatile static TimeCountUtil instance;

    private TimeCountUtil() {
    }

    public static TimeCountUtil getInstance() {
        if (instance == null) {
            synchronized (TimeCountUtil.class) {
                if (instance == null) {
                    instance = new TimeCountUtil();
                }
            }
        }
        return instance;
    }
}
