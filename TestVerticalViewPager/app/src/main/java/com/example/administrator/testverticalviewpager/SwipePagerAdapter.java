package com.example.administrator.testverticalviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class SwipePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public SwipePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    public List<Fragment> getmFragments() {
        return mFragments;
    }

    public void setmFragments(List<Fragment> mFragments) {
        this.mFragments = mFragments;
    }

    @Override
    public int getCount() {
        return Short.MAX_VALUE;
    }

    @Override
    public Fragment getItem(int position) {
        position = position % mFragments.size();
        Log.d("apollo","position: "+position);
        return mFragments.get(position);
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        position = position % mFragments.size();
//        Log.d("apollo","position: "+position);
//        return super.instantiateItem(container, position);
//    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
