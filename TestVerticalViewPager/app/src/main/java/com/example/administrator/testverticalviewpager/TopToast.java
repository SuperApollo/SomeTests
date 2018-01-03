package com.example.administrator.testverticalviewpager;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/12/14.
 */

public class TopToast extends Toast {
    private static Toast mToast;

    public TopToast(Context context) {
        super(context);
    }

    public static Toast getToast(Context context, int layout, int duration) {
        if (mToast == null) {
            mToast = new Toast(context);
        } else {
            mToast.cancel();
            mToast = new Toast(context);
        }
        //获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获得屏幕的宽度
        int width = wm.getDefaultDisplay().getWidth();
        //由layout文件创建一个View对象
        try {
            View view = inflater.inflate(layout, null);
            mToast.setView(view);
            mToast.setGravity(Gravity.TOP, 0, 0);
            mToast.setDuration(duration);


            Object mTN = null;
            mTN = getField(mToast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.width = width;
                    params.height = DensityUtil.dip2px(context, 68);
                    params.windowAnimations = R.style.anim_top_bar;
                }
            }

            return mToast;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 反射字段
     *
     * @param object    要反射的对象
     * @param fieldName 要反射的字段名称
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }
}
