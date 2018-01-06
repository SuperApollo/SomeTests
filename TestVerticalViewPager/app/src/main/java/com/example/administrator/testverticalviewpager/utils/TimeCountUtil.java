package com.example.administrator.testverticalviewpager.utils;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.testverticalviewpager.widget.CountDwonView;

/**
 * Created by Apollo on 2018/1/5 16:25.
 */

public class TimeCountUtil {
    private volatile static TimeCountUtil instance;
    private static CountDwonView countDwonView;
    private static final int UPDATE_PROGRESS = 0;
    private static MyHandler mHandler;
    private static float progress = 0;
    private static float timeSeconds = 10;

    private TimeCountUtil(CountDwonView countDwonView) {
        this.countDwonView = countDwonView;
        mHandler = new MyHandler();
    }

    public void updateCountDwonView(CountDwonView countDwonView) {
        this.countDwonView = countDwonView;
    }

    public static TimeCountUtil getInstance(CountDwonView countDwonView) {
        if (instance == null) {
            synchronized (TimeCountUtil.class) {
                if (instance == null) {
                    instance = new TimeCountUtil(countDwonView);
                }
            }
        }
        return instance;
    }

    public void setTimeSeconds(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void startCount() {
        if (countDwonView == null) {
            return;
        }
        countDwonView.setMax((int) (timeSeconds * 1000));
        countDwonView.setProgress(0);
        countDwonView.setTimeSeconds(timeSeconds);
        progress = 0;
        if (mHandler == null) {
            mHandler = new MyHandler();
        }
        mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 100);
    }

    public void stopCount() {
        mHandler.removeMessages(UPDATE_PROGRESS);
    }

    public void cancelCount() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (countDwonView != null) {
            countDwonView = null;
        }
        progress = 0;
        timeSeconds = 10;
    }

    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    mHandler.removeMessages(UPDATE_PROGRESS);
                    if (progress < timeSeconds * 1000) {
                        progress += timeSeconds * 1000 / 360;//步进为总时间的1/360
                        countDwonView.setProgress(progress);
                        mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, (long) (timeSeconds * 1000 / 360));
                        break;
                    }
            }
        }
    }

}
