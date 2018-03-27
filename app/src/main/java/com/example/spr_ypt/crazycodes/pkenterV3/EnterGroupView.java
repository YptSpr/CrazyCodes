package com.example.spr_ypt.crazycodes.pkenterV3;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.R;

/**
 * Created by Spr_ypt on 2018/2/9.
 */

public class EnterGroupView extends FrameLayout {

    private View mRootView;

    private int[] mData;
    private GridLayout mGroupLayout;
    private TextView mGroupTitle;

    public EnterGroupView(Context context) {
        super(context);
        init();
    }

    public EnterGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EnterGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public EnterGroupView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.view_enter_group, this);
        initView();
    }

    public void setData(int[] data) {
        this.mData = data;
        for (int i : data) {
            View item = LayoutInflater.from(getContext()).inflate(R.layout.item_group_normal, this, false);
            item.setTag(i);
            item.getLayoutParams().width=320;
            mGroupLayout.addView(item);
        }

        measure(MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Log.e("spr_ypt", "setData: getWidth=" + mGroupLayout.getMeasuredWidth());
        Log.e("spr_ypt", "setData: getWidth=" + getMeasuredWidth());
    }

    private void initView() {
        mGroupLayout = (GridLayout) findViewById(R.id.group_layout);
        mGroupTitle = (TextView) findViewById(R.id.group_title);
    }
}
