package com.example.spr_ypt.crazycodes.rankedGame;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.R;

public class RankedGameMatchingView extends LinearLayout {
    private View mRootView;
    private TextView mBtnCancel;

    public RankedGameMatchingView(Context context) {
        super(context);
        init();
    }

    public RankedGameMatchingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RankedGameMatchingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RankedGameMatchingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.hani_view_ranked_game_matching, this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mBtnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
            }
        });
    }

    private void initView() {
        mBtnCancel = (TextView) findViewById(R.id.btn_cancel);
    }
}
