package com.example.spr_ypt.crazycodes.inviteFriendDemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.R;

/**
 * Created by Spr_ypt on 2017/12/7.
 */

public class TabChildView extends RelativeLayout {
    private View mRootView;
    private TextView mTvTabName;
    private View mVTabPoint;

    public TabChildView(@NonNull Context context) {
        super(context);
        init();
    }

    public TabChildView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabChildView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TabChildView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mRootView = inflate(getContext(), R.layout.view_invite_tab, this);
        setBackgroundResource(R.drawable.tab_child_bg);
        initView();
    }


    private void initView() {
        mTvTabName = mRootView.findViewById(R.id.tv_tab_name);
        mVTabPoint = mRootView.findViewById(R.id.v_tab_point);
    }

    public TabChildView setData(String name) {
        mTvTabName.setText(name);
        return this;
    }

    public void setPointVisible(int visible) {
        mVTabPoint.setVisibility(visible);
    }

    public void setTextColor(int color){
        mTvTabName.setTextColor(color);
    }
}
