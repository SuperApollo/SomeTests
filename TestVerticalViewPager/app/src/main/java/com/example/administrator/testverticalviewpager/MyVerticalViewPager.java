package com.example.administrator.testverticalviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;


/**
 * Created by Administrator on 2017/12/7.
 */

public class MyVerticalViewPager extends ViewPager {
    private boolean loopEnable;
    private MyHandler myHandler;
    private final int SHOW_NEXT = 0;
    private WeakReference<Context> contextWeakReference;

    public MyVerticalViewPager(Context context) {
        this(context, null);
    }

    public MyVerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        contextWeakReference = new WeakReference<>(context);
        initSpeed();
        setPageTransformer(false, new DefaultTransformer());
        myHandler = new MyHandler();
    }

    /**
     * 设置切换速度
     */
    private void initSpeed() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(contextWeakReference.get());
            scroller.setDuration(1000);
            mScroller.set(MyVerticalViewPager.this, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

    /**
     * 将横向滑动改为纵向
     *
     * @param event
     * @return
     */
    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float swappedX = (event.getY() / height) * width;
        float swappedY = (event.getX() / width) * height;

        event.setLocation(swappedX, swappedY);

        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(event));
        //If not intercept, touch event should not be swapped.
        swapTouchEvent(event);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapTouchEvent(ev));
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_NEXT:
                    int currentItem = getCurrentItem();
                    currentItem++;
                    if (currentItem == Integer.MAX_VALUE) {
                        currentItem = 0;
                    }
                    setCurrentItem(currentItem);
                    if (loopEnable) {
                        startLoop();
                    }
                    break;
            }

        }
    }

    public void startLoop() {
        if (myHandler == null) {
            myHandler = new MyHandler();
        }
        loopEnable = true;
        Message message = Message.obtain();
        message.what = SHOW_NEXT;
        myHandler.sendMessageDelayed(message, 1000);
    }

    public void stopLoop() {
        loopEnable = false;
        if (myHandler != null) {
            myHandler.removeMessages(SHOW_NEXT);
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
        }

    }

    /**
     * 自定义scroller控制viewpager滑动速度
     */
    private class ViewPagerScroller extends Scroller {
        private int mDuration;

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public void setDuration(int mDuration) {
            this.mDuration = mDuration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, this.mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.mDuration);
        }

    }

}
