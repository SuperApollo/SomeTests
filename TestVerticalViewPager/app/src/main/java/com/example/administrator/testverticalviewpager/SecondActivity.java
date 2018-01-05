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

import com.example.administrator.testverticalviewpager.utils.TimeCountUtil;
import com.example.administrator.testverticalviewpager.widget.CountDwonView;

/**
 * Created by Administrator on 2017/12/12.
 */

public class SecondActivity extends Activity {

    private ImageView mIvReadyGo;
    private static CountDwonView mBar1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mIvReadyGo = findViewById(R.id.iv_ready_go);
        mBar1 = findViewById(R.id.progress_bar_1);
        final TimeCountUtil timeCountUtil = TimeCountUtil.getInstance(mBar1);
        timeCountUtil.setTimeSeconds(30);
        findViewById(R.id.btn_ready_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyAnim();
                timeCountUtil.startCount();
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCountUtil.stopCount();
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


}
