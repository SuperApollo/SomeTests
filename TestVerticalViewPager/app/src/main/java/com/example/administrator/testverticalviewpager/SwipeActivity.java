package com.example.administrator.testverticalviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class SwipeActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mVp;
    List<Fragment> mFragments = new ArrayList<>();
    private SwipePagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        mVp = findViewById(R.id.vp_swipe);
        SwipeFragment fragment = new SwipeFragment();
        SwipeFragment fragment1 = new SwipeFragment();
        SwipeFragment fragment2 = new SwipeFragment();
        SwipeFragment fragment3 = new SwipeFragment();
        mFragments.add(fragment);
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);

        mAdapter = new SwipePagerAdapter(getSupportFragmentManager(), mFragments);
        mVp.setAdapter(mAdapter);
        mVp.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("apollo", "currentPosition: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
