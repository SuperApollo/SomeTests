package com.example.administrator.testverticalviewpager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.testverticalviewpager.R;
import com.example.administrator.testverticalviewpager.bean.User;

import java.util.List;

/**
 * 折叠效果的view
 * Created by Apollo on 2017/12/21 15:19.
 */

public class MyStackView extends RelativeLayout {
    private TextView mTvStackPeople;
    private RoundImageView mIvStackLeft;
    private RoundImageView mIvStackCenter;
    private RoundImageView mIvStackRightTop;
    private RoundImageView mIvStackRightCenter;
    private RoundImageView mIvStackRightBottom;
    private List<User> mUsers;
    OnItemClickListener onItemClickListener;

    public List<User> getmUsers() {
        return mUsers;
    }

    public void setmUsers(List<User> mUsers) {
        this.mUsers = mUsers;
        updateView();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyStackView(Context context) {
        super(context);
        initView(context);
    }

    public MyStackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.stack_layout, this, true);
        mTvStackPeople = findViewById(R.id.tv_stack_people);
        mIvStackLeft = findViewById(R.id.iv_stack_left);
        mIvStackCenter = findViewById(R.id.iv_stack_center);
        mIvStackRightTop = findViewById(R.id.iv_stack_right_top);
        mIvStackRightCenter = findViewById(R.id.iv_stack_right_center);
        mIvStackRightBottom = findViewById(R.id.iv_stack_right_bottom);

        updateView();

    }

    /**
     * mUsers集合有变化要调用此方法更新视图
     */
    public void updateView() {
        if (mUsers != null && mUsers.size() > 0) {
            int size = mUsers.size();
            if (size < 6) {
                switch (mUsers.size()) {
                    case 1:
                        ViewVisible(mIvStackLeft);
                        mIvStackRightBottom.clearAnimation();
                        ViewGone(mIvStackRightBottom);
                        mIvStackRightCenter.clearAnimation();
                        ViewGone(mIvStackRightCenter);
                        mIvStackRightTop.clearAnimation();
                        ViewGone(mIvStackRightTop);
                        mIvStackCenter.clearAnimation();
                        ViewGone(mIvStackCenter);
                        mIvStackCenter.setVisibility(GONE);
                        break;
                    case 2:
                        ViewVisible(mIvStackLeft);
                        ViewVisible(mIvStackCenter);
                        mIvStackRightBottom.clearAnimation();
                        ViewGone(mIvStackRightBottom);
                        mIvStackRightCenter.clearAnimation();
                        ViewGone(mIvStackRightCenter);
                        mIvStackRightTop.clearAnimation();
                        ViewGone(mIvStackRightTop);
                        break;
                    case 3:
                        ViewVisible(mIvStackLeft);
                        ViewVisible(mIvStackCenter);
                        ViewVisible(mIvStackRightTop);
                        mIvStackRightBottom.clearAnimation();
                        ViewGone(mIvStackRightBottom);
                        mIvStackRightCenter.clearAnimation();
                        ViewGone(mIvStackRightCenter);
                        break;
                    case 4:
                        ViewVisible(mIvStackLeft);
                        ViewVisible(mIvStackCenter);
                        ViewVisible(mIvStackRightTop);
                        ViewVisible(mIvStackRightCenter);
                        mIvStackRightBottom.clearAnimation();
                        ViewGone(mIvStackRightBottom);
                        break;
                    case 5:
                        ViewVisible(mIvStackLeft);
                        ViewVisible(mIvStackCenter);
                        ViewVisible(mIvStackRightTop);
                        ViewVisible(mIvStackRightCenter);
                        ViewVisible(mIvStackRightBottom);
                        break;
                }
            } else {

                ViewVisible(mIvStackLeft);
                ViewVisible(mIvStackCenter);
                ViewVisible(mIvStackRightTop);
                ViewVisible(mIvStackRightCenter);
                ViewVisible(mIvStackRightBottom);
            }
            setClickListenerByNumber(size);
        } else {

            mIvStackRightBottom.clearAnimation();
            ViewGone(mIvStackRightBottom);
            mIvStackRightCenter.clearAnimation();
            ViewGone(mIvStackRightCenter);
            mIvStackRightTop.clearAnimation();
            ViewGone(mIvStackRightTop);
            mIvStackCenter.clearAnimation();
            ViewGone(mIvStackCenter);
            mIvStackLeft.clearAnimation();
            ViewGone(mIvStackLeft);
            setClickListenerByNumber(0);
        }
    }

    /**
     * 手动模拟隐藏
     *
     * @param imageView
     */
    private void ViewGone(RoundImageView imageView) {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
        layoutParams.width = 0;
        layoutParams.height = 0;
        imageView.setLayoutParams(layoutParams);

    }

    /**
     * 手动显示
     *
     * @param imageView
     */
    private void ViewVisible(RoundImageView imageView) {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        imageView.setLayoutParams(layoutParams);
    }

    /**
     * 根据user人数来决定设置监听和图片
     *
     * @param number mUsers中的人数
     */
    private void setClickListenerByNumber(int number) {
        mTvStackPeople.setText(number + "人围观" + (number - 1) + "号机");
        if (number == 0)
            return;

        mIvStackLeft.setImageResource(Integer.parseInt(mUsers.get(0).getAvatar()));
        mIvStackLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(mUsers.get(0));
                }
            }
        });
        if (number == 1)//拦截
            return;

        mIvStackCenter.setImageResource(Integer.parseInt(mUsers.get(1).getAvatar()));
        mIvStackCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(mUsers.get(1));
                }
            }
        });
        if (number == 2)//拦截
            return;

        mIvStackRightTop.setImageResource(Integer.parseInt(mUsers.get(2).getAvatar()));
        mIvStackRightTop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(mUsers.get(2));
                }
            }
        });
        if (number == 3)//拦截
            return;

        mIvStackRightCenter.setImageResource(Integer.parseInt(mUsers.get(3).getAvatar()));
        mIvStackRightCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
//                    onItemClickListener.onItemClick(mUsers.get(3));//叠加的图标不能点击
                }
            }
        });
        if (number == 4)//拦截
            return;

        mIvStackRightBottom.setImageResource(Integer.parseInt(mUsers.get(4).getAvatar()));
        mIvStackRightBottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
//                    onItemClickListener.onItemClick(mUsers.get(4));//叠加的图标不能点击
                }
            }
        });

        //5时到底，不拦截
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }
}
