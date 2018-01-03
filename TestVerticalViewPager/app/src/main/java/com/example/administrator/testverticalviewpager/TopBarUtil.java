package com.example.administrator.testverticalviewpager;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * 顶部广告条工具类
 * Created by Administrator on 2017/12/14.
 */

public class TopBarUtil {
    private static final int DISMISS_POP = 0;
    private static final String TAG = TopBarUtil.class.getCanonicalName();
    private static PopupWindow mPopupWindow;
    private static MyHandler mHandler;

    /**
     * 显示在顶部
     *
     * @param context        上下文，必须是activity
     * @param rootViewLayout 根布局的id
     * @param millis         显示的时间
     */
    public static void show(Context context, int rootViewLayout, long millis) {
        if (!(context instanceof Activity)) {
            Log.e(TAG, "传入的context不是activity类型");
            return;
        }

        if (mHandler == null) {
            mHandler = new MyHandler();
        }
        View popView = LayoutInflater.from(context).inflate(R.layout.topbar_layout, null);
        View rootView = LayoutInflater.from(context).inflate(rootViewLayout, null);
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, dip2px(context, 68), false);
        } else {
            mPopupWindow.setContentView(popView);
        }
        mPopupWindow.setAnimationStyle(R.style.anim_top_bar);
        mPopupWindow.showAtLocation(rootView, Gravity.TOP, 0, 0);

        Message message = Message.obtain();
        message.what = DISMISS_POP;
        mHandler.sendMessageDelayed(message, millis);
    }

    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DISMISS_POP:
                    mPopupWindow.dismiss();
                    mHandler.removeMessages(DISMISS_POP);
                    mHandler.removeCallbacksAndMessages(null);
                    break;
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
