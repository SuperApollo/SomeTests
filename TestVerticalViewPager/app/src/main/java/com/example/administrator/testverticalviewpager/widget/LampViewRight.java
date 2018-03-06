package com.example.administrator.testverticalviewpager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.testverticalviewpager.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apollo on 2017/12/28 11:52.
 * 直接在一个界面使用两个lampview会有问题，handler为同一个，暂时解决不了，
 * 采取笨方法，重新copy一份lampview，重命名为LapmpViewRight
 */

public class LampViewRight extends LinearLayout {
    private List<ImageView> lights = new ArrayList<>();
    private List<ImageView> lightsRight = new ArrayList<>();
    public static final int SHOW_STYLE_LEFT = 0;
    public static final int SHOW_STYLE_RIGHT = 1;
    private static MyHandler mMyHandler;
    public static final int LOOP_TYPE_SINGLE = 2;//只亮一个，跑马灯
    public static final int LOOP_TYPE_ADD = 3;//逐个点亮
    public static final int LOOP_TYPE_EXCHANGE = 4;//跑马灯
    private static final int LIGHT_REST = 5;//所有灯恢复到灭掉状态

    private static long timeMillis;
    private boolean isRunning;
    private int picH;
    private int picW;
    private static int show_style;
    private WeakReference<Context> contextWeakReference;
    private static boolean isEven = false;//是否为偶数

    public int getShow_style() {
        return show_style;
    }

    public void setShow_style(int show_style) {
        this.show_style = show_style;
    }

    public LampViewRight(Context context) {
        super(context);
        init(context, null);
    }

    public LampViewRight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LampViewRight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWH(widthMeasureSpec, 100, 0);
        int height = measureWH(heightMeasureSpec, 100, 1);
        Log.d("apollo", "width: " + width + ",height: " + height);
        setMeasuredDimension(width, height);

        initView(height, widthMeasureSpec, heightMeasureSpec);


    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int count = getChildCount();
//        //记录当前的高度位置
//        int curHeight = t;
//        //将子View逐个摆放
//        for (int i = 0; i < count; i++) {
//            View child = getChildAt(i);
//            int height = child.getMeasuredHeight();
//            int width = child.getMeasuredWidth();
//            //摆放子View，参数分别是子View矩形区域的左、上、右、下边
//            child.layout(l, curHeight, l + width, curHeight + height);
//            curHeight += height;
//        }
//    }

    /**
     * 测量宽高
     * type 0宽，1高
     */
    private int measureWH(int measureSpec, int defaultValue, int type) {
        int model = MeasureSpec.getMode(measureSpec);//获得当前空间值的一个模式
        int size = MeasureSpec.getSize(measureSpec);//获得当前空间值的推荐值
        int mySize = defaultValue;

        switch (model) {
            case MeasureSpec.EXACTLY://当你的控件设置了一个精确的值或者为match_parent时, 为这种模式
                Log.d("apollo", "EXACTLY");
                mySize = size;
                break;
            case MeasureSpec.AT_MOST://当你的控件设置为wrap_content时，为这种模式
                Log.d("apollo", "AT_MOST");
                if (type == 0) {//宽
                    mySize = picW;
                } else if (type == 1) {//高
                    mySize = size;
                }
                break;
            case MeasureSpec.UNSPECIFIED://任意值
                mySize = defaultValue;
                break;
        }
        return mySize;
    }

    /**
     * 初始化，测量图片宽高
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        contextWeakReference = new WeakReference<Context>(context);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.light_red_left);
        picH = bitmap.getHeight();
        picW = bitmap.getWidth();
        bitmap.recycle();
        bitmap = null;
        System.gc();

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LampView);
            show_style = a.getInteger(R.styleable.LampView_show_style, 0);
            a.recycle();
        }

    }

    /**
     * 根据控件高度动态设置灯的数量
     *
     * @param height            控件高度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    private void initView(int height, int widthMeasureSpec, int heightMeasureSpec) {
        LampViewRight.this.setOrientation(VERTICAL);
        if (lights.size() > 0) {
            LampViewRight.this.removeAllViews();
            lights.clear();
        }

        int size = height / picH;
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(getContext());
            LayoutParams params = new LayoutParams(picW, picH);
            imageView.setLayoutParams(params);
            if (show_style == SHOW_STYLE_LEFT) {
                if (i % 2 == 0)
                    imageView.setImageResource(R.mipmap.light_red_left);
                else
                    imageView.setImageResource(R.mipmap.light_yellow_left);
            } else if (show_style == SHOW_STYLE_RIGHT) {
                if (i % 2 == 0)
                    imageView.setImageResource(R.mipmap.light_red_right);
                else
                    imageView.setImageResource(R.mipmap.light_yellow_right);
            }

            imageView.setAlpha(0.2f);
            LampViewRight.this.addView(imageView, params);
            lights.add(imageView);
        }

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //这个很重要，没有就不显示,addview后要重新测量子view
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }

        if (mMyHandler == null)
            mMyHandler = new MyHandler(lights);
        else
            mMyHandler.setImageViews(lights);

    }


    /**
     * 开启跑马灯
     *
     * @param millis   间隔时间
     * @param loopType 循环的类型
     */
    public void startLight(long millis, int loopType) {
        if (LampViewRight.this.getVisibility() == GONE || LampViewRight.this.getVisibility() == INVISIBLE) {
            LampViewRight.this.setVisibility(VISIBLE);
        }
        if (loopType < LOOP_TYPE_SINGLE || loopType > LOOP_TYPE_EXCHANGE) {//判断类型，要在正确的类型范围内
            Context context = contextWeakReference.get();
            if (context != null)
                Toast.makeText(context, "请传入正确的循环类型", Toast.LENGTH_SHORT).show();
            return;
        }
        timeMillis = millis;
        if (mMyHandler != null && !isRunning) {
            mMyHandler.sendEmptyMessage(loopType);
            isRunning = true;
        }
    }

    /**
     * 停止跑马灯
     */
    public void stopLight() {
        if (mMyHandler != null) {
//            mMyHandler.removeMessages(LOOP_TYPE_SINGLE);
//            mMyHandler.removeMessages(LOOP_TYPE_ADD);
            mMyHandler.removeCallbacksAndMessages(null);
            isRunning = false;
        }
    }

    private static class MyHandler extends Handler {
        private List<ImageView> imageViews;
        private int currentLight = 0;//当前亮灯的编号标识
        private int speed = 1;//轮播速度倍数

        public MyHandler(List<ImageView> lights) {
            imageViews = lights;
        }

        public void setImageViews(List<ImageView> imageViews) {
            this.imageViews = imageViews;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOOP_TYPE_SINGLE:
                    mMyHandler.removeMessages(LOOP_TYPE_SINGLE);
                    for (int i = 0; i < imageViews.size(); i++) {
                        if (i == currentLight) {
                            imageViews.get(i).setAlpha(1f);
                        } else {
                            imageViews.get(i).setAlpha(0.2f);
                        }
                    }
                    currentLight++;
                    if (currentLight == imageViews.size()) {
                        Message message = Message.obtain();
                        message.what = LIGHT_REST;
                        message.obj = LOOP_TYPE_SINGLE;//携带过去的obj标识自己，作为下次发送的what
                        mMyHandler.sendMessageDelayed(message, timeMillis);
                    } else {
                        mMyHandler.sendEmptyMessageDelayed(LOOP_TYPE_SINGLE, timeMillis);
                    }


                    break;
                case LOOP_TYPE_ADD:
                    mMyHandler.removeMessages(LOOP_TYPE_ADD);
                    imageViews.get(currentLight).setAlpha(1f);
                    currentLight++;
                    if (currentLight == imageViews.size()) {
                        Message message = Message.obtain();
                        message.what = LIGHT_REST;
                        message.obj = LOOP_TYPE_ADD;
                        mMyHandler.sendMessageDelayed(message, timeMillis);
                    } else {
                        mMyHandler.sendEmptyMessageDelayed(LOOP_TYPE_ADD, timeMillis);
                    }
                    break;
                case LIGHT_REST:
                    for (ImageView iv : imageViews) {
                        iv.setAlpha(0.2f);
                    }
                    currentLight = 0;
                    int what = (int) msg.obj;
                    mMyHandler.sendEmptyMessageDelayed(what, timeMillis);
                    break;
                case LOOP_TYPE_EXCHANGE:
                    for (int i = 0; i < imageViews.size(); i++) {
                        imageViews.get(i).setAlpha(1f);
                        if (i % 2 == 0) {//偶数
                            if (isEven) {
                                imageViews.get(i).setImageResource(show_style == SHOW_STYLE_LEFT ? R.mipmap.light_red_left : R.mipmap.light_red_right);
                            } else {
                                imageViews.get(i).setImageResource(show_style == SHOW_STYLE_LEFT ? R.mipmap.light_yellow_left : R.mipmap.light_yellow_right);
                            }
                        } else {//奇数
                            if (isEven) {
                                imageViews.get(i).setImageResource(show_style == SHOW_STYLE_LEFT ? R.mipmap.light_yellow_left : R.mipmap.light_yellow_right);
                            } else {
                                imageViews.get(i).setImageResource(show_style == SHOW_STYLE_LEFT ? R.mipmap.light_red_left : R.mipmap.light_red_right);
                            }
                        }
                    }
                    mMyHandler.sendEmptyMessageDelayed(LOOP_TYPE_EXCHANGE, timeMillis);
                    isEven = !isEven;
                    break;
            }
        }
    }
}
