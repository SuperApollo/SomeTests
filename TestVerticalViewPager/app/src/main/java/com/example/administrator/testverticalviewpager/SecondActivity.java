package com.example.administrator.testverticalviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/12/12.
 */

public class SecondActivity extends Activity {

    private ImageView mIvReadyGo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mIvReadyGo = findViewById(R.id.iv_ready_go);
        findViewById(R.id.btn_ready_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyAnim();
            }
        });
    }

    private void readyAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 3, 0, 3,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.2f);
        AnimationSet set = new AnimationSet(true);
        set.setDuration(800);
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
