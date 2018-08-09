package com.example.spr_ypt.crazycodes.pkArenaBox;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
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
    private BoxRotateRayView mVRotateRay;
    private Handler mDelayHandler = new Handler();

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
        mVBoxBg = (PkArenaBoxBgView) mRootView.findViewById(R.id.v_box_bg);
        mVRotateRay = (BoxRotateRayView) mRootView.findViewById(R.id.v_rotate_ray);
    }

    public void showBox() {
        reset();
        setVisibility(View.VISIBLE);

        mTvBoxInfo.setText(Html.fromHtml("差<font color='#f8e71c'>10000</font>星光值开启宝箱!"));//4test

        mAnim = ObjectAnimator.ofFloat(0f, 1f);
        mAnim.setDuration(300);
        mAnim.setInterpolator(new OvershootInterpolator());
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                setScaleX(scale);
                setScaleY(scale);
            }
        });
        mAnim.start();

        mVRotateRay.start();

        mDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeToSmall();
            }
        }, 3000);
    }

    public void changeToSmall() {
        mVRotateRay.stop();
        mVRotateRay.setVisibility(GONE);
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
                mTvBoxInfo.setSelected(true);
            }
        });

        mAnim.start();

    }

    public void setInfoText(String text) {
        mAnim = ObjectAnimator.ofInt(0, mTvBoxInfo.getHeight());
        mAnim.setRepeatMode(ValueAnimator.REVERSE);
        mAnim.setRepeatCount(1);
        mAnim.setDuration(100);
        ScrollAnimListener animListener = new ScrollAnimListener(mTvBoxInfo, text);
        mAnim.addUpdateListener(animListener);
        mAnim.addListener(animListener);
        mAnim.start();
    }

    public void openChestAnim() {
        if (mIvBoxIcon.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) mIvBoxIcon.getDrawable()).start();
        }
    }

    private void changeBgView(float index) {
        mVBoxBg.setIndex(index);
    }

    private void changeIconView(float index) {
        if (null != mIvBoxIcon.getLayoutParams()) {
            mIvBoxIcon.getLayoutParams().width = (int) (getResources().getDimension(R.dimen.demin_36dp) + index * getResources().getDimension(R.dimen.demin_114dp));
            mIvBoxIcon.getLayoutParams().height = (int) (getResources().getDimension(R.dimen.demin_36dp) + index * getResources().getDimension(R.dimen.demin_114dp));
            if (mIvBoxIcon.getLayoutParams() instanceof MarginLayoutParams) {
                ((MarginLayoutParams) mIvBoxIcon.getLayoutParams()).setMargins(0, (int) (index * getResources().getDimension(R.dimen.demin_15dp)), 0, (int) getResources().getDimension(R.dimen.demin_45dp));
            }
        }

    }

    public void reset() {
        mDelayHandler.removeCallbacksAndMessages(null);
        mVRotateRay.setVisibility(VISIBLE);
        mTvBoxTitle.setVisibility(VISIBLE);
        mTvBoxDesc.setVisibility(VISIBLE);
        mTvBoxInfo.setTranslationY(0f);
        mTvBoxInfo.setVisibility(INVISIBLE);
        changeIconView(1);
        changeBgView(1);
        requestLayout();
        mIvBoxIcon.setImageResource(R.drawable.hani_pk_arena_chest_box_anim);
        setVisibility(INVISIBLE);
    }


    private class ScrollAnimListener extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private TextView tv;
        private String text;
        private int indexY = 1;

        public ScrollAnimListener(TextView mTvBoxInfo, String text) {
            this.tv = mTvBoxInfo;
            this.text = text;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int translateY = indexY * (int) animation.getAnimatedValue();
            tv.setTranslationY(translateY);
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            super.onAnimationRepeat(animation);
            tv.setText(Html.fromHtml(text));
            indexY = -1;
        }
    }
}
