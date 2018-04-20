package com.example.spr_ypt.crazycodes.rankedGame.score.imp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.spr_ypt.crazycodes.R;
import com.example.spr_ypt.crazycodes.rankedGame.RankedGameUtils;
import com.example.spr_ypt.crazycodes.rankedGame.RoundRectDrawableFactory;
import com.example.spr_ypt.crazycodes.rankedGame.score.AbsScoreContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreContainerType;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreViewHolder;

import java.util.ArrayList;
import java.util.List;

public class OpenContainer extends AbsScoreContainer {
    public OpenContainer(ScoreViewHolder holder) {
        super(holder);
    }

    @Override
    public void onDataChanged() {

        mViewHolder.mIvStartAnchorIcon.setImageResource(R.drawable.hani_pk_arena_popup_random_scroll_icon_2);
        mViewHolder.mTvStartAnchorName.setText("望山1234567890");
        mViewHolder.mTvStartAnchorInfo.setText("是大腿");


        mViewHolder.mIvStartOppIcon.setImageResource(R.drawable.hani_pk_arena_popup_random_scroll_icon_3);
        mViewHolder.mTvStartOppName.setText("观海1234567890");
        mViewHolder.mTvStartOppInfo.setText("很神秘");

    }

    @Override
    public void changeAnim(List<View> views, final View rootView) {
        RankedGameUtils.setViewsVisible(View.VISIBLE, getShowViews());
        mViewHolder.mIvBg.setImageDrawable(RoundRectDrawableFactory.getDrawable(24, RankedGameUtils.parseColor("#4c000000")));
        mViewHolder.mVRotateRay.setVisibility(View.GONE);
        mViewHolder.mVRotateRay.stop();
        mViewHolder.mTvStartTimer.setBackgroundDrawable(RoundRectDrawableFactory.getDrawable(24, Color.WHITE));
        mViewHolder.mIvStartAnchorBg.setImageDrawable(RoundRectDrawableFactory.getRightCotDrawable(24, 0.185f, Color.parseColor("#ff2d55")));
        mViewHolder.mIvStartOppBg.setImageDrawable(RoundRectDrawableFactory.getLeftCotDrawable(24, 0.185f, Color.parseColor("#408aec")));

        if (null == mAnim) {
            mAnim = ObjectAnimator.ofFloat(0f, 1f);
            mAnim.setInterpolator(new AccelerateInterpolator());
            mAnim.setDuration(300);
            mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float index = (float) animation.getAnimatedValue();
                    mViewHolder.mTvStartTimer.setScaleX(index);
                    mViewHolder.mTvStartTimer.setScaleY(index);

                    for (View v : mViewHolder.pkStartLeftViews) {
                        v.setTranslationX(-rootView.getWidth() * (1 - index) / 2);
                    }

                    for (View v : mViewHolder.pkStartRightViews) {
                        v.setTranslationX(rootView.getWidth() * (1 - index) / 2);
                    }
                }
            });
        }
        mAnim.removeAllListeners();
        mAnim.start();

    }

    private ValueAnimator mAnim;

    @Override
    public void dropAnim(final List<View> views, View rootView) {
        mAnim.removeAllListeners();
        mAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                RankedGameUtils.setViewsVisible(View.GONE, views);
            }
        });
        mAnim.reverse();


    }

    @Override
    public int getContainerType() {
        return ScoreContainerType.STATE_OPEN;
    }

    @Override
    public void recycle() {

    }

    @Override
    public List<View> getShowViews() {
        List<View> views = new ArrayList<>();
        views.addAll(mViewHolder.pkStartViews);
        return views;
    }
}
