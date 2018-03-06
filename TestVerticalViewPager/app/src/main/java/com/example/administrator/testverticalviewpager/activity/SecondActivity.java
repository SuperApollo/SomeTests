package com.example.administrator.testverticalviewpager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.administrator.testverticalviewpager.R;
import com.example.administrator.testverticalviewpager.utils.TimeCountUtil;
import com.example.administrator.testverticalviewpager.widget.CountDwonView;
import com.example.administrator.testverticalviewpager.widget.MyDialogFragment;

/**
 * Created by Administrator on 2017/12/12.
 */

public class SecondActivity extends FragmentActivity implements MyDialogFragment.MyDialogListner {

    private ImageView mIvReadyGo;
    private static CountDwonView mBar1;
    private MyDialogFragment dialogFragment;
    private LottieAnimationView mHorseRight;
    private LottieAnimationView mHorseLeft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mIvReadyGo = findViewById(R.id.iv_ready_go);
        mBar1 = findViewById(R.id.progress_bar_1);
        findViewById(R.id.btn_ready_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyAnim();
//                timeCountUtil.startCount();
                mBar1.startCount(25);
                mHorseLeft.playAnimation();
                mHorseRight.playAnimation();
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                timeCountUtil.stopCount();
                mBar1.stopCount();
            }
        });
        findViewById(R.id.btn_constraint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, TestContraintLayoutActivity.class));
            }
        });

        findViewById(R.id.btn_vp_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_dialog_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment = new MyDialogFragment();
                dialogFragment.show(SecondActivity.this.getSupportFragmentManager(), "MyDialogFragment");
            }
        });

        mHorseLeft = findViewById(R.id.horse_left);
        mHorseRight = findViewById(R.id.horse_right);
        mHorseLeft.setImageAssetsFolder("images/");
        mHorseRight.setImageAssetsFolder("images/");

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBar1.cancelCount();
    }

    @Override
    public void onDismissClicked(View view) {
        dialogFragment.dismiss();
    }
}
