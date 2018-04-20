package com.example.spr_ypt.crazycodes.rankedGame.score.imp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.spr_ypt.crazycodes.rankedGame.RankedGameUtils;
import com.example.spr_ypt.crazycodes.rankedGame.RoundRectDrawableFactory;
import com.example.spr_ypt.crazycodes.rankedGame.score.AbsScoreContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreContainerType;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreViewHolder;

import java.util.ArrayList;
import java.util.List;

public class EndContainer extends AbsScoreContainer {

    private ValueAnimator mAnim;

    public EndContainer(ScoreViewHolder holder) {
        super(holder);
    }

    @Override
    public void onDataChanged() {

        mViewHolder.mTvSuccessFlag.setText("三连胜");
        mViewHolder.mTvSuccessInfo.setText("获取胜利啦啦啦啦");

        resetScoreInfo();

        mViewHolder.mTvAnchorScorePercent.setText("+"+"3"+"%");

    }

    private void resetScoreInfo() {
        mViewHolder.mTvAnchorScore.setBackgroundDrawable(RoundRectDrawableFactory.getLeftBottomDrawable(24, RankedGameUtils.parseColor("#cdc9ce")));
        mViewHolder.mTvOpponentScore.setBackgroundDrawable(RoundRectDrawableFactory.getRightBottomDrawable(24, RankedGameUtils.parseColor("#ffc108")));
        mViewHolder.mTvAnchorScore.setText(RankedGameUtils.getScoreTxt(300000000));
        mViewHolder.mTvOpponentScore.setText(RankedGameUtils.getScoreTxt(3000));


//        mViewHolder.mTvScoreCountdown.setVisibility(View.GONE);
        mViewHolder.mTvScoreCountdown.setBackgroundDrawable(RoundRectDrawableFactory.getDrawable(24, Color.WHITE));
        mViewHolder.mTvScoreCountdown.setText("0");
    }


    @Override
    public void changeAnim(List<View> views, View rootView) {
        RankedGameUtils.setViewsVisible(View.VISIBLE, getShowViews());
//        mViewHolder.mTvScoreCountdown.setVisibility(View.GONE);
        if (TextUtils.isEmpty(mViewHolder.mTvSuccessFlag.getText()))
            mViewHolder.mTvSuccessFlag.setVisibility(View.GONE);

        mViewHolder.mIvBg.setImageDrawable(RoundRectDrawableFactory.getDrawable(24, RankedGameUtils.parseColor("#4c000000")));
        mViewHolder.mVRotateRay.setVisibility(View.VISIBLE);
        mViewHolder.mVRotateRay.start();

        createProgressAnim(views, rootView);
        mAnim.removeAllListeners();
        mAnim.start();
    }

    private void createProgressAnim(final List<View> views, final View rootView) {
        if (null == mAnim) {
            mAnim = ObjectAnimator.ofFloat(0f, 1f);
            mAnim.setDuration(200);
            mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        mAnim.removeAllUpdateListeners();
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float transY = rootView.getHeight() * (1 - (float) animation.getAnimatedValue());
                for (View v : views) {
                    v.setTranslationY(transY);
                }
            }
        });
    }

    @Override
    public void dropAnim(final List<View> views, View rootView) {
        if (null != mAnim && mAnim.isRunning()) {
            mAnim.cancel();
            for (View v : getShowViews()) {
                v.setTranslationY(0f);
            }
        }

        createProgressAnim(views, rootView);

        mAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                RankedGameUtils.setViewsVisible(View.GONE, views);
                for (View v : views) {
                    v.setTranslationY(0f);
                }
            }
        });
        mAnim.reverse();
    }

    @Override
    public int getContainerType() {
        return ScoreContainerType.STATE_END;
    }

    @Override
    public void recycle() {
        if (null != mAnim) {
            mAnim.removeAllUpdateListeners();
            mAnim.removeAllListeners();
            mAnim.cancel();
        }

    }

    @Override
    public List<View> getShowViews() {
        List<View> views = new ArrayList<>();
        views.addAll(mViewHolder.successViews);
        views.addAll(mViewHolder.scoreViews);
        views.add(mViewHolder.mTvAnchorScorePercent);
        return views;
    }
}
