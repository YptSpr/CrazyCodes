package com.example.spr_ypt.crazycodes.pkArenaBox;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.R;

public class pkArenaBoxView extends FrameLayout {
    private static final int ANIM_DURATION = 300;
    private View mRootView;
    private ImageView mIvBoxIcon;
    private ImageView mIvBoxIconMask;
    private TextView mTvBoxTitle;
    private TextView mTvBoxLine1;
    private TextView mTvBoxLine2;
    private ValueAnimator mChangeAnim;
    private CircleVerticalProgressDrawable mProgressDrawable;

    public pkArenaBoxView(@NonNull Context context) {
        super(context);
        init();
    }

    public pkArenaBoxView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public pkArenaBoxView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public pkArenaBoxView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mRootView = inflate(getContext(), R.layout.hani_view_window_pk_arena_box, this);
        initView();
        resetView();
    }


    private void initView() {
        mIvBoxIcon = (ImageView) mRootView.findViewById(R.id.iv_box_icon);
        mIvBoxIconMask = (ImageView) mRootView.findViewById(R.id.iv_box_icon_mask);
        mTvBoxTitle = (TextView) mRootView.findViewById(R.id.tv_box_title);
        mTvBoxLine1 = (TextView) mRootView.findViewById(R.id.tv_box_line_1);
        mTvBoxLine2 = (TextView) mRootView.findViewById(R.id.tv_box_line_2);
        mProgressDrawable = new CircleVerticalProgressDrawable();
        mIvBoxIconMask.setImageDrawable(mProgressDrawable);
    }

    /**
     * 切换到小图标模式
     */
    public void changeToSmall() {
        if (null != mChangeAnim && mChangeAnim.isRunning()) mChangeAnim.cancel();
        mTvBoxLine1.setVisibility(GONE);
        mTvBoxLine2.setVisibility(GONE);
        int startWidth = getLayoutParams().width;
        int endWidth = (int) getResources().getDimension(R.dimen.demin_36dp);
        mChangeAnim = ObjectAnimator.ofInt(startWidth, endWidth);
        mChangeAnim.setDuration(ANIM_DURATION);
        mChangeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getLayoutParams().width = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        mChangeAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setBackground(null);
                mTvBoxTitle.setVisibility(VISIBLE);
            }
        });
        mChangeAnim.start();

    }

    /**
     * 切换到大图标模式
     */
    public void changeToBig() {
        if (null != mChangeAnim && mChangeAnim.isRunning()) mChangeAnim.cancel();
        mTvBoxTitle.setVisibility(GONE);
        setBackgroundResource(R.drawable.hani_window_view_pk_arena_box_bg);
        int startWidth = getLayoutParams().width;
        int endWidth = (int) getResources().getDimension(R.dimen.demin_130dp);
        mChangeAnim = ObjectAnimator.ofInt(startWidth, endWidth);
        mChangeAnim.setDuration(ANIM_DURATION);
        mChangeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getLayoutParams().width = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        mChangeAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTvBoxLine1.setVisibility(VISIBLE);
                mTvBoxLine2.setVisibility(VISIBLE);
            }
        });
        mChangeAnim.start();

    }

    public void setProgressRate(@FloatRange(from = 0.0f, to = 1.0f) float rate) {
        mProgressDrawable.setRate(rate);
    }


    private void resetView() {
        setBackground(null);
        mTvBoxTitle.setVisibility(VISIBLE);
        mTvBoxLine1.setVisibility(GONE);
        mTvBoxLine2.setVisibility(GONE);
        LayoutParams params;
        if (null != getLayoutParams()) {
            params = (LayoutParams) getLayoutParams();
            params.width = (int) getResources().getDimension(R.dimen.demin_36dp);
            params.height = (int) getResources().getDimension(R.dimen.demin_36dp);
        } else {
            params = new LayoutParams((int) getResources().getDimension(R.dimen.demin_36dp), (int) getResources().getDimension(R.dimen.demin_36dp));
        }

        setLayoutParams(params);
    }


    public void recycle() {
        if (null != mChangeAnim && mChangeAnim.isRunning()) mChangeAnim.cancel();
        mProgressDrawable.recycle();
        resetView();
    }
}
