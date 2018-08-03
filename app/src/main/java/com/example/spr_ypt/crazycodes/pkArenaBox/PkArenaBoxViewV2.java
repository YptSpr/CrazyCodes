package com.example.spr_ypt.crazycodes.pkArenaBox;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.R;

public class PkArenaBoxViewV2 extends FrameLayout {
    private View mRootView;
    private ImageView mIvBoxIcon;
    private TextView mTvBoxTitle;
    private TextView mTvBoxDesc;
    private TextView mTvBoxInfo;
    private PkArenaBoxBgView mVBoxBg;
    private ValueAnimator mAnim;

    public PkArenaBoxViewV2(@NonNull Context context) {
        super(context);
        init();
    }

    public PkArenaBoxViewV2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PkArenaBoxViewV2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PkArenaBoxViewV2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mRootView = inflate(getContext(), R.layout.hani_view_window_pk_arena_box_v2, this);
        initView();
    }

    private void initView() {
        mIvBoxIcon = (ImageView) mRootView.findViewById(R.id.iv_box_icon);
        mTvBoxTitle = (TextView) mRootView.findViewById(R.id.tv_box_title);
        mTvBoxDesc = (TextView) mRootView.findViewById(R.id.tv_box_desc);
        mTvBoxInfo = (TextView) mRootView.findViewById(R.id.tv_box_info);
        mVBoxBg = (PkArenaBoxBgView) findViewById(R.id.v_box_bg);
    }

    public void changeToSmall() {
        mTvBoxTitle.setVisibility(GONE);
        mTvBoxDesc.setVisibility(GONE);
        mAnim = ObjectAnimator.ofFloat(1, 0);
        mAnim.setDuration(500);
        mAnim.setInterpolator(new OvershootInterpolator(1));
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float index = (float) animation.getAnimatedValue();
                changeIconView(index);
                changeBgView(index);
                requestLayout();
            }
        });

        mAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTvBoxInfo.setVisibility(VISIBLE);
                mTvBoxInfo.requestFocus();
                mTvBoxInfo.requestFocusFromTouch();
            }
        });

        mAnim.start();

    }

    private void changeBgView(float index) {
        mVBoxBg.setIndex(index);
    }

    private void changeIconView(float index) {
        if (null != mIvBoxIcon.getLayoutParams()) {
            mIvBoxIcon.getLayoutParams().width = (int) (getResources().getDimension(R.dimen.demin_36dp) + index * getResources().getDimension(R.dimen.demin_114dp));
            mIvBoxIcon.getLayoutParams().height = (int) (getResources().getDimension(R.dimen.demin_36dp) + index * getResources().getDimension(R.dimen.demin_114dp));
            if (mIvBoxIcon.getLayoutParams() instanceof MarginLayoutParams) {
                ((MarginLayoutParams) mIvBoxIcon.getLayoutParams()).setMargins(0, (int) (index * getResources().getDimension(R.dimen.demin_15dp)), 0, (int) getResources().getDimension(R.dimen.demin_40dp));
            }
        }

    }

    public void reset() {
        mTvBoxTitle.setVisibility(VISIBLE);
        mTvBoxDesc.setVisibility(VISIBLE);
        mTvBoxInfo.setVisibility(GONE);
        changeIconView(1);
        changeBgView(1);
        requestLayout();

    }
}
