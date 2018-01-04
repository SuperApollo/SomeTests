package com.example.administrator.testverticalviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.administrator.testverticalviewpager.widget.RingProgressBar;

/**
 * Created by Administrator on 2017/12/12.
 */

public class SecondActivity extends Activity {

    private ImageView mIvReadyGo;
    private static RingProgressBar mBar1;
    private static RingProgressBar mBar2;
    private static final int UPDATE_PROGRESS = 0;
    private static MyHandler mHandler;
    private static int progress = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mIvReadyGo = findViewById(R.id.iv_ready_go);
        mBar1 = findViewById(R.id.progress_bar_1);
        mBar2 = findViewById(R.id.progress_bar_2);
        mHandler = new MyHandler();
        findViewById(R.id.btn_ready_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyAnim();
                mBar1.setProgress(0);
                mBar2.setProgress(0);
                progress = 0;
                mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 100);
            }
        });
    }

    private void readyAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 3, 0, 3,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.2f);
        AnimationSet set = new AnimationSet(true);
        set.setDuration(700);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mIvReadyGo.setImageResource(R.mipmap.ready);
        mIvReadyGo.setVisibility(View.VISIBLE);
        mIvReadyGo.startAnimation(set);
    }

    private void goAnim() {
        mIvReadyGo.setVisibility(View.GONE);
        mIvReadyGo.setImageResource(R.mipmap.go);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 2, 0, 2,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.2f);
        AnimationSet set = new AnimationSet(true);
        set.setDuration(500);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvReadyGo.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mIvReadyGo.setVisibility(View.VISIBLE);
        mIvReadyGo.startAnimation(set);
    }

    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    mHandler.removeMessages(UPDATE_PROGRESS);
                    if (progress < 100) {
                        progress++;
                        mBar1.setProgress(progress);
                        mBar2.setProgress(progress);
                        mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 100);
                        break;
                    }
            }
        }
    }

}
