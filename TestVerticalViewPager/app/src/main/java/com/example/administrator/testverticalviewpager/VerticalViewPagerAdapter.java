package com.example.administrator.testverticalviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class VerticalViewPagerAdapter extends PagerAdapter {
    List<BroadcustData> dataList;
    LayoutInflater mInflater;
    Context mContext;
    OnPagerItemClickListner onPagerItemClickListner;
    String avatar = "https://images.qn.rgbvr.com/misc/1502894024145image_20170816223344";
    String doll = "https://images.qn.rgbvr.com/misc/1502894024145image_20170816223344";

    public void setOnPagerItemClickListner(OnPagerItemClickListner onPagerItemClickListner) {
        this.onPagerItemClickListner = onPagerItemClickListner;
    }

    public VerticalViewPagerAdapter(Context mContext, List<BroadcustData> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (dataList != null && dataList.size() > 0) {
            position = position % dataList.size();
            BroadcustData broadcustData = dataList.get(position);
            final int clickedPosition = position;
            View view = mInflater.inflate(R.layout.vertical_vp_item, null);
            ImageView ivAvatar = view.findViewById(R.id.iv_avatar);
            TextView tvMsg = view.findViewById(R.id.tv_msg);
            ImageView ivDoll = view.findViewById(R.id.iv_doll);
            Glide.with(mContext)
                    .load(avatar)
                    .placeholder(R.drawable.ic_launcher_background)
                    .crossFade()
                    .into(ivAvatar);
//            ivAvatar.setImageResource(R.drawable.ic_launcher_foreground);

            tvMsg.setText(broadcustData.getBroadCastContent());
            Glide.with(mContext)
                    .load(doll)
                    .placeholder(R.drawable.ic_launcher_background)
                    .crossFade()
                    .into(ivDoll);
//            ivDoll.setImageResource(R.drawable.ic_launcher_background);
            container.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPagerItemClickListner != null) {
                        onPagerItemClickListner.onPagerItemClick(clickedPosition);
                    }
                }
            });

            return view;
        } else {
            return super.instantiateItem(container, position);
        }


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    public interface OnPagerItemClickListner {
        void onPagerItemClick(int postion);
    }
}
