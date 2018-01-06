package com.example.administrator.testverticalviewpager.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.testverticalviewpager.R;

/**
 * 自定义圆形、饼状进度条
 * Created by Apollo on 2018/1/4 16:32.
 */

public class CountDwonView extends View {
    //画笔对象
    private Paint paint;

    //View宽度
    private int width;

    //View高度
    private int height;

    //默认宽高值
    private int result = 0;

    //默认padding值
    private float padding = 0;

    //圆环的颜色
    private int ringColor;

    //圆环进度颜色
    private int ringProgressColor;

    //文字颜色
    private int textColor;

    //文字大小
    private float textSize;

    //圆环宽度
    private float ringWidth;

    //最大值
    private float max;

    //进度值
    private float progress;

    //是否显示文字
    private boolean textIsShow;

    //圆环进度条的样式
    private int style;

    //空心样式
    public static final int STROKE = 0;

    //实心样式
    public static final int FILL = 1;

    //进度回调接口
    private OnProgressListener mOnProgressListener;

    // 圆环中心
    private float center;

    // 圆环半径
    private float radius;

    //总倒计时时长
    private float timeSeconds;

    //扇形起始角度
    private float startAngle = -90;
    private RectF strokeOval;
    private RectF fillOval;
    private MyHandler mHandler;
    private final int UPDATE_PROGRESS = 0;


    public CountDwonView(Context context) {

        this(context, null);
    }


    public CountDwonView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }


    public CountDwonView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        //初始化画笔
        paint = new Paint();
        //打开抗锯齿
        paint.setAntiAlias(true);

        //初始化默认宽高值
        result = dp2px(100);

        //初始化属性
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDwonView);

        ringColor = mTypedArray.getColor(R.styleable.CountDwonView_ringColor, Color.BLACK);
        ringProgressColor = mTypedArray.getColor(R.styleable.CountDwonView_ringProgressColor,
                Color.WHITE);
        textColor = mTypedArray.getColor(R.styleable.CountDwonView_textColor, Color.BLACK);
        textSize = mTypedArray.getDimension(R.styleable.CountDwonView_textSize, 16);
        ringWidth = mTypedArray.getDimension(R.styleable.CountDwonView_ringWidth, 5);
        max = mTypedArray.getFloat(R.styleable.CountDwonView_max, 100);
        textIsShow = mTypedArray.getBoolean(R.styleable.CountDwonView_textIsShow, true);
        style = mTypedArray.getInt(R.styleable.CountDwonView_style, 0);
        progress = mTypedArray.getFloat(R.styleable.CountDwonView_progress, 0);
        padding = mTypedArray.getDimension(R.styleable.CountDwonView_ringPadding, 5);

        mTypedArray.recycle();

        strokeOval = new RectF();
        fillOval = new RectF();

        mHandler = new MyHandler();

    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        center = Math.min(getWidth() / 2, getHeight() / 2);
        radius = (center - ringWidth);//外层圆环的半径

        //绘制外层圆
        drawCircle(canvas);
        //绘制进度条
        drawProgress(canvas);
        //绘制文本内容
        drawTextContent(canvas);
    }


    /**
     * 绘制外层圆环
     */
    private void drawCircle(Canvas canvas) {
        //设置画笔颜色
        paint.setColor(ringColor);
        //设置画笔样式
        paint.setStyle(Paint.Style.STROKE);
        //设置stroke的宽度
        paint.setStrokeWidth(ringWidth);
        //绘制圆形
        canvas.drawCircle(center, center, radius, paint);
    }


    /**
     * 绘制进度文本
     */
    private void drawTextContent(Canvas canvas) {
        //设置stroke的宽度
        paint.setStrokeWidth(0);
        //设置文字的颜色
        paint.setColor(textColor);
        //设置文字的大小
        paint.setTextSize(textSize);
        //设置文字的style
        paint.setTypeface(Typeface.DEFAULT);
        //设置进度值
        int percent = (int) (((float) progress / (float) max) * 100);
        String seconds = (int) ((100f - percent) * timeSeconds / 100) + "s";
        //获取文字的宽度 用于绘制文本内容
//        float textWidth = paint.measureText(percent + "%");
        float textWidth = paint.measureText(seconds);
        //绘制文本 会根据设置的是否显示文本的属性&是否是Stroke的样式进行判断
        if (textIsShow && percent != 0) {//&& style == STROKE
            canvas.drawText(seconds, center - textWidth / 2, center + textSize / 2, paint);
        }
    }


    /**
     * 绘制进度条
     */
    private void drawProgress(Canvas canvas) {
        //绘制进度 根据设置的样式进行绘制
        paint.setStrokeWidth(0);//x线宽，这里要设置为0，否则起始角度时夹角不锐
        paint.setColor(ringProgressColor);

        //Stroke样式
        strokeOval.set(center - radius, center - radius, center + radius,
                center + radius);
        //FIll样式
        float left = center - radius + ringWidth + padding;
        float top = center - radius + ringWidth + padding;
        float right = center + radius - ringWidth - padding;
        float bottom = center + radius - ringWidth - padding;

        fillOval.set(left, top, right, bottom);

        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                canvas.drawArc(strokeOval, -90, 360 * progress / max, false, paint);
                break;
            }
            case FILL: {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                if (progress != 0) {
                    //绘制圆弧
                    float sweepAngle = 360 * progress / max;
                    canvas.drawArc(fillOval, startAngle, sweepAngle, true, paint);
                }
                break;
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高的mode和size
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //测量宽度
        if (widthMode == MeasureSpec.AT_MOST) {
            width = result;
        } else {
            width = widthSize;
        }

        //测量高度
        if (heightMode == MeasureSpec.AT_MOST) {
            height = result;
        } else {
            height = heightSize;
        }

        //设置测量的宽高值
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        //确定View的宽高
        width = w;
        height = h;
    }


    /**
     * 获取当前的最大进度值
     */
    public synchronized float getMax() {

        return max;
    }


    /**
     * 设置最大进度值
     */
    public synchronized void setMax(int max) {

        if (max < 0) {
            throw new IllegalArgumentException("The max progress of 0");
        }
        this.max = max;
    }


    /**
     * 获取进度值
     */
    public synchronized float getProgress() {

        return progress;
    }


    /**
     * 设置进度值 根据进度值进行View的重绘刷新进度
     */
    public synchronized void setProgress(float progress) {

        if (progress < 0) {
            throw new IllegalArgumentException("The progress of 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }
        if (progress == max) {
            if (mOnProgressListener != null) {
                mOnProgressListener.progressToComplete();
            }
        }
    }


    /**
     * 获取圆环的颜色
     */
    public int getRingColor() {

        return ringColor;
    }


    /**
     * 设置圆环的颜色
     */
    public void setRingColor(int ringColor) {

        this.ringColor = ringColor;
    }


    /**
     * 获取圆环进度的颜色
     */
    public int getRingProgressColor() {

        return ringProgressColor;
    }


    /**
     * 设置圆环进度的颜色
     */
    public void setRingProgressColor(int ringProgressColor) {

        this.ringProgressColor = ringProgressColor;
    }


    /**
     * 获取文字的颜色
     */
    public int getTextColor() {

        return textColor;
    }


    /**
     * 设置文字颜色
     */
    public void setTextColor(int textColor) {

        this.textColor = textColor;
    }


    /**
     * 获取文字的大小
     */
    public float getTextSize() {

        return textSize;
    }


    /**
     * 设置文字的大小
     */
    public void setTextSize(float textSize) {

        this.textSize = textSize;
    }


    /**
     * 获取圆环的宽度
     */
    public float getRingWidth() {

        return ringWidth;
    }


    /**
     * 设置圆环的宽度
     */
    public void setRingWidth(float ringWidth) {

        this.ringWidth = ringWidth;
    }


    /**
     * dp转px
     */
    public int dp2px(int dp) {

        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }


    /**
     * 进度完成回调接口
     */
    public interface OnProgressListener {

        void progressToComplete();
    }


    public void setTimeSeconds(float timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void setOnProgressListener(OnProgressListener mOnProgressListener) {

        this.mOnProgressListener = mOnProgressListener;
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    mHandler.removeMessages(UPDATE_PROGRESS);
                    if (progress < timeSeconds * 1000) {
                        progress += timeSeconds * 1000 / 360;//步进为总时间的1/360
                        CountDwonView.this.setProgress(progress);
                        mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, (long) (timeSeconds * 1000 / 360));
                        break;
                    }
            }
        }
    }

    /**
     * 开始倒计时
     *
     * @param timeSeconds 倒计时时间
     */
    public void startCount(float timeSeconds) {
        if (progress == max) {
            progress = 0;
        }
        setMax((int) (timeSeconds * 1000));
        setTimeSeconds(timeSeconds);
        mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 100);
    }

    /**
     * 停止倒计时
     */
    public void stopCount() {
        mHandler.removeMessages(UPDATE_PROGRESS);
    }

    /**
     * 取消倒计时,activity销毁时调用
     */
    public void cancelCount() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        progress = 0;
        timeSeconds = 10;
    }
}
