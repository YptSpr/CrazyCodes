package com.example.spr_ypt.crazycodes.rankedGame.score.imp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.spr_ypt.crazycodes.R;
import com.example.spr_ypt.crazycodes.rankedGame.RankedGameUtils;
import com.example.spr_ypt.crazycodes.rankedGame.RoundRectDrawableFactory;
import com.example.spr_ypt.crazycodes.rankedGame.score.AbsScoreContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreContainerType;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RankContainer extends AbsScoreContainer {
    private ValueAnimator mRankAnim;

    public RankContainer(ScoreViewHolder holder) {
        super(holder);
    }

    @Override
    public void onDataChanged() {

        mViewHolder.mIvRank.setImageResource(R.drawable.car);
        mViewHolder.mTvRankInfo.setTextColor(Color.parseColor("#fff80e"));
        mViewHolder.mTvRankInfo.setText("世界第一摇摆王~");

        resetScoreInfo();
    }

    private void resetScoreInfo() {
        mViewHolder.mTvAnchorScore.setBackgroundDrawable(RoundRectDrawableFactory.getLeftBottomDrawable(24, RankedGameUtils.parseColor("#cdc9ce")));
        mViewHolder.mTvOpponentScore.setBackgroundDrawable(RoundRectDrawableFactory.getRightBottomDrawable(24, RankedGameUtils.parseColor("#ffc108")));
        mViewHolder.mTvAnchorScore.setText(RankedGameUtils.getScoreTxt(300000000));
        mViewHolder.mTvOpponentScore.setText(RankedGameUtils.getScoreTxt(3000));
        RankedGameUtils.resetTxtTimer(520, mViewHolder.mTvScoreCountdown, "%s");
    }

    @Override
    public void changeAnim(List<View> views, View rootView) {
        RankedGameUtils.setViewsVisible(View.VISIBLE, mViewHolder.rankViews, mViewHolder.scoreViews);
        mViewHolder.mIvBg.setImageDrawable(RoundRectDrawableFactory.getDrawable(24, RankedGameUtils.parseColor("#4c000000")));
        mViewHolder.mVRotateRay.setVisibility(View.GONE);
        mViewHolder.mVRotateRay.stop();
        mViewHolder.mTvScoreCountdown.setBackgroundDrawable(RoundRectDrawableFactory.getDrawable(24, Color.WHITE));


        createRankAnim(views, rootView);
        mRankAnim.removeAllListeners();
        mRankAnim.start();
    }

    private void createRankAnim(final List<View> views, final View rootView) {
        if (null == mRankAnim) {
            mRankAnim = ObjectAnimator.ofFloat(0f, 1f);
            mRankAnim.setDuration(200);
            mRankAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        mRankAnim.removeAllUpdateListeners();
        mRankAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
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
        if (null != mRankAnim && mRankAnim.isRunning()) {
            mRankAnim.cancel();
            for (View v : mViewHolder.rankViews) {
                v.setTranslationY(0f);
            }
            for (View v : mViewHolder.scoreViews) {
                v.setTranslationY(0f);
            }
        }

        createRankAnim(views, rootView);
        mRankAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                RankedGameUtils.setViewsVisible(View.GONE, views);
                for (View v : views) {
                    v.setTranslationY(0f);
                }
            }
        });
        mRankAnim.reverse();
    }

    @Override
    public int getContainerType() {
        return ScoreContainerType.STATE_RANK;
    }

    @Override
    public void recycle() {

    }

    @Override
    public List<View> getShowViews() {
        List<View> views = new ArrayList<>();
        views.addAll(mViewHolder.rankViews);
        views.addAll(mViewHolder.scoreViews);
        return views;
    }
}
