package com.example.administrator.testverticalviewpager.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.testverticalviewpager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 快抓fragment载体activity
 * Created by Administrator on 2017/12/15.
 */

public class QuickGrabActivity extends Activity {

    private final String TAG = QuickGrabActivity.class.getSimpleName();
    private View mRootView;
    private ViewPager mVpGrab;
    private List<Fragment> mFragments;
    private OnWindowFocusChangeListner mOnWindowFocusChangeListner;
    private OnActivityRestartListner mOnActivityRestartListner;
    private LocalActivityManager mLocalActivityManager;
    private String[] mlistTag = {"one", "two"}; //activity标识
    private List<View> mAcvitityList = new ArrayList<>();//activity容器

    public void setmOnWindowFocusChangeListner(OnWindowFocusChangeListner mOnWindowFocusChangeListner) {
        this.mOnWindowFocusChangeListner = mOnWindowFocusChangeListner;
    }

    public void setmOnActivityRestartListner(OnActivityRestartListner mOnActivityRestartListner) {
        this.mOnActivityRestartListner = mOnActivityRestartListner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRootView = LayoutInflater.from(this).inflate(R.layout.activity_quick_grab, null);
        setContentView(mRootView);

        mLocalActivityManager = new LocalActivityManager(this, true);
        mLocalActivityManager.dispatchCreate(savedInstanceState);//参数arg0为oncreate方法的形参
        initView();
//        initFragments();
        initActivities();


    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocalActivityManager.dispatchPause(true);//传入true
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }


    private void initView() {
        mVpGrab = findViewById(R.id.vp_container_grab);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mOnWindowFocusChangeListner != null) {
            mOnWindowFocusChangeListner.onWindowFocusChanged(hasFocus);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mOnWindowFocusChangeListner != null) {
            mOnActivityRestartListner.onRestart();
        }
    }

    /**
     * 将父activity的方法回调给fragment
     */
    public interface OnWindowFocusChangeListner {
        void onWindowFocusChanged(boolean hasFocus);
    }

    public interface OnActivityRestartListner {
        void onRestart();
    }


    private void initActivities() {


        Intent intent0 = new Intent(getApplicationContext(), OneActivity.class);
        View v0 = getView(mlistTag[0], intent0);
        Intent intent1 = new Intent(getApplicationContext(), TwoActivity.class);
        View v1 = getView(mlistTag[1], intent1);
        mAcvitityList.add(v0);
        mAcvitityList.add(v1);

        mVpGrab.setAdapter(new MyPagerAdapter(mAcvitityList));
        mVpGrab.setCurrentItem(0);
        mVpGrab.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 通过activity获取视图
     *
     * @param id
     * @param intent
     * @return
     */
    private View getView(String id, Intent intent) {
        return mLocalActivityManager.startActivity(id, intent).getDecorView();
    }

    /**
     * Pager适配器
     */
    public class MyPagerAdapter extends PagerAdapter {

        List<View> listview = new ArrayList<>();

        public MyPagerAdapter(List<View> list) {
            this.listview = list;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            position = position % (listview.size());
            container.removeView(listview.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % (listview.size());

            ViewGroup parent = (ViewGroup) listview.get(position).getParent();
            if (parent != null) {
//                parent.removeView(listview.get(position));
                parent.removeAllViews();
            }


            container.addView(listview.get(position), 0);
            return listview.get(position);
        }

        @Override
        public int getCount() {
            return listview.size() <= 1 ? listview.size() : Short.MAX_VALUE;
//            return listview.size();
        }

        @Override
        public boolean isViewFromObject(View v, Object obj) {
            return v == obj;
        }
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            int pos = 0;//记录hsv_column滚动位置
            Log.i("onPageSelected", "position=" + arg0);
//            loadCurActivity(arg0);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    /**
     * @param arg0:页面位置
     * @function:调用子Activity中的方法
     */
    private void loadCurActivity(int arg0) {
        Activity curActivity = mLocalActivityManager.getActivity(mlistTag[arg0]);
        switch (arg0) {
            case 0:
                if (curActivity != null && curActivity instanceof OneActivity) {
                    Log.d(TAG, "oneActivityChek");
                }
                break;
            case 1:
                if (curActivity != null && curActivity instanceof TwoActivity) {
                    Log.d(TAG, "twoActivityChek");
                }
                break;
        }
    }
}
