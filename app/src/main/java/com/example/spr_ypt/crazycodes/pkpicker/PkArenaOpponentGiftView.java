package com.example.spr_ypt.crazycodes.pkpicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.spr_ypt.crazycodes.R;

/**
 * Created by Spr_ypt on 2017/12/15.
 */

public class PkArenaOpponentGiftView extends RelativeLayout {
    private View mRootView;
    private ImageView mIvOpponentGift;
    private TextView mTvMultiple;
    private LottieAnimationView mLavStars;
    private static final int ANIM_SPEED_INDEX = 1;

    public PkArenaOpponentGiftView(Context context) {
        super(context);
        init();
    }

    public PkArenaOpponentGiftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PkArenaOpponentGiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PkArenaOpponentGiftView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mRootView = inflate(getContext(), R.layout.hani_view_window_pk_arena_opponent_gift_view, this);
        initView();
    }

    private void initView() {
        mIvOpponentGift = (ImageView) mRootView.findViewById(R.id.iv_opponent_gift);
        mTvMultiple = (TextView) mRootView.findViewById(R.id.tv_multiple);
        mLavStars = (LottieAnimationView) mRootView.findViewById(R.id.lav_stars);
    }

    public void setData(String imgUrl, float multiple) {
        if (multiple > 1) {
            mTvMultiple.setVisibility(VISIBLE);
            mTvMultiple.setText("x" + multiple);
        } else {
            mTvMultiple.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(imgUrl)) mIvOpponentGift.setImageURI(Uri.parse(imgUrl));

    }

    public void endAnim() {
        setOnAnimEndListener(null);
    }

    public void startAnim(final boolean hasMultiple) {
        setOnAnimEndListener(new OnAnimEndListener() {
            @Override
            public void animEnd() {
                startAnim(true);
            }
        });
        setVisibility(VISIBLE);
        ValueAnimator animator = ObjectAnimator.ofFloat(0f, 1f);
        animator.setDuration(500);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIvOpponentGift.setScaleX((Float) animation.getAnimatedValue());
                mIvOpponentGift.setScaleY((Float) animation.getAnimatedValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animStep2(400);
            }
        });
        animator.start();

        if (hasMultiple) animStep1();

    }


    private void animStep1() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ValueAnimator anim = ObjectAnimator.ofArgb(Color.parseColor("#ff2d55"), Color.WHITE, Color.parseColor("#ff2d55"));
            anim.setDuration(300 * ANIM_SPEED_INDEX);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.hani_pk_arena_window_opponent_gift_multiple_bg);
                    drawable.setColor((Integer) animation.getAnimatedValue());
                    mTvMultiple.setBackgroundDrawable(drawable);
                }
            });
            anim.start();
        }

        ValueAnimator anim1 = ObjectAnimator.ofFloat(1.5f, 1f);
        anim1.setDuration(300 * ANIM_SPEED_INDEX);
        anim1.setInterpolator(new OvershootInterpolator(3f));
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            boolean isOver;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTvMultiple.setScaleX((Float) animation.getAnimatedValue());
                mTvMultiple.setScaleY((Float) animation.getAnimatedValue());
                if ((Float) animation.getAnimatedValue() <= 1f && !isOver) {
                    isOver = true;
                    mLavStars.playAnimation();
                }
            }
        });
        anim1.start();


    }

    private void animStep2(int startDelay) {
        animate().alpha(0f).setDuration(100 * ANIM_SPEED_INDEX).setStartDelay(startDelay * ANIM_SPEED_INDEX)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setAlpha(1f);
                        setVisibility(GONE);
                        if (null != mListener) mListener.animEnd();
                    }
                }).start();
    }

    private OnAnimEndListener mListener;

    public void setOnAnimEndListener(OnAnimEndListener listener) {
        this.mListener = listener;
    }

    public interface OnAnimEndListener {
        void animEnd();
    }
}
