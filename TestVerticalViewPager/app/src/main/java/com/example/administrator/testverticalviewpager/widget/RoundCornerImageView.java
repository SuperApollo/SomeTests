package com.example.administrator.testverticalviewpager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.testverticalviewpager.R;


/**
 * 带圆角的imageview
 * Created by Administrator on 2017/12/20.
 */

public class RoundCornerImageView extends ImageView {
    private int width;
    private int height;
    private int mRadius;
    private int mRadiusX;

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);
        mRadius = typedArray.getInteger(R.styleable.RoundCornerImageView_corner_radius, 12);
        mRadiusX = dip2px(context, mRadius);
        typedArray.recycle();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (width > mRadiusX && height > mRadiusX) {
            Path path = new Path();
            path.moveTo(mRadiusX, 0);
            path.lineTo(width - mRadiusX, 0);
            path.quadTo(width, 0, width, mRadiusX);
            path.lineTo(width, height - mRadiusX);
            path.quadTo(width, height, width - mRadiusX, height);
            path.lineTo(mRadiusX, height);
            path.quadTo(0, height, 0, height - mRadiusX);
            path.lineTo(0, mRadiusX);
            path.quadTo(0, 0, mRadiusX, 0);
            canvas.clipPath(path);
        }

        super.onDraw(canvas);
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
