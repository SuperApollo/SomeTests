package com.example.administrator.testverticalviewpager.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.testverticalviewpager.R;
import com.example.administrator.testverticalviewpager.SwipePagerAdapter;
import com.example.administrator.testverticalviewpager.fragment.SwipeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apollo on 2018/2/9 16:53.
 */

public class MyDialogFragment extends DialogFragment {
    MyDialogListner listner = null;
    private ViewPager dialogContainer;
    private List<Fragment> mFragments = new ArrayList<>();
    private SwipePagerAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listner = (MyDialogListner) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏无标题
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_NoTitleBar_Fullscreen);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.layout_my_dialog_fragment, container);
        view.findViewById(R.id.iv_dialog_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listner != null) {
                    listner.onDismissClicked(v);
                }
            }
        });
        dialogContainer = view.findViewById(R.id.vp_dialog_container);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwipeFragment fragment = new SwipeFragment();
        SwipeFragment fragment1 = new SwipeFragment();
        SwipeFragment fragment2 = new SwipeFragment();
        SwipeFragment fragment3 = new SwipeFragment();
        mFragments.add(fragment);
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);

        mAdapter = new SwipePagerAdapter(getChildFragmentManager(), mFragments);
        dialogContainer.setAdapter(mAdapter);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public interface MyDialogListner {
        void onDismissClicked(View view);
    }
}
