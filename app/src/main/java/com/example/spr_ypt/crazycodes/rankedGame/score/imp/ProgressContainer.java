package com.example.spr_ypt.crazycodes.rankedGame.score.imp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.spr_ypt.crazycodes.R;
import com.example.spr_ypt.crazycodes.rankedGame.GameProgressDrawable;
import com.example.spr_ypt.crazycodes.rankedGame.RankedGameUtils;
import com.example.spr_ypt.crazycodes.rankedGame.RoundRectDrawableFactory;
import com.example.spr_ypt.crazycodes.rankedGame.score.AbsScoreContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreContainerType;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ProgressContainer extends AbsScoreContainer {

    private GameProgressDrawable progressDrawable;

    private ValueAnimator mProgressAnim;

    public ProgressContainer(ScoreViewHolder holder) {
        super(holder);
    }

    @Override
    public void onDataChanged() {
        mViewHolder.mTvProgress.setText("斩杀");
        mViewHolder.mIvProgressIcon.setImageResource(R.drawable.hani_ranked_game_score_progress_icon);
        if (mViewHolder.mIvProgressIcon.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) mViewHolder.mIvProgressIcon.getLayoutParams()).leftMargin = 20;
            mViewHolder.mIvProgressIcon.invalidate();
        }

        mViewHolder.mTvProgressTxt.setText("520/1000");

        resetScoreInfo();

        resetProgressDrawable();
    }

    private void resetScoreInfo() {
        mViewHolder.mTvAnchorScore.setBackgroundDrawable(RoundRectDrawableFactory.getLeftBottomDrawable(24, RankedGameUtils.parseColor("#cdc9ce")));
        mViewHolder.mTvOpponentScore.setBackgroundDrawable(RoundRectDrawableFactory.getRightBottomDrawable(24, RankedGameUtils.parseColor("#ffc108")));
        mViewHolder.mTvAnchorScore.setText(RankedGameUtils.getScoreTxt(3000));
        mViewHolder.mTvOpponentScore.setText(RankedGameUtils.getScoreTxt(3000));
        RankedGameUtils.resetTxtTimer(520, mViewHolder.mTvScoreCountdown, "%s");
    }

    private void resetProgressDrawable() {
        if (null != progressDrawable) {
            progressDrawable.setFrontColor(RankedGameUtils.parseColor("#cc00ef"));
            progressDrawable.setRateNoAnim(1f);
            progressDrawable.setRate(0f, 5000);
        }
    }

    @Override
    public void changeAnim(List<View> views, View rootView) {
        RankedGameUtils.setViewsVisible(View.VISIBLE, mViewHolder.progressViews, mViewHolder.scoreViews);
        //只有切换container才走，这里可以布置一些当前container不需要变动的数据
        mViewHolder.mIvBg.setImageDrawable(RoundRectDrawableFactory.getDrawable(24, RankedGameUtils.parseColor("#4c000000")));
        mViewHolder.mVRotateRay.setVisibility(View.GONE);
        mViewHolder.mVRotateRay.stop();
        mViewHolder.mTvScoreCountdown.setBackgroundDrawable(RoundRectDrawableFactory.getDrawable(24, Color.WHITE));

        progressDrawable = new GameProgressDrawable(1f, RankedGameUtils.parseColor("#33ffffff"), RankedGameUtils.parseColor("#cc00ef"));
        mViewHolder.mIvProgress.setImageDrawable(progressDrawable);
        progressDrawable.setRate(0f, 5000);


        createProgressAnim(views, rootView);
        mProgressAnim.removeAllListeners();
        mProgressAnim.start();
    }

    private void createProgressAnim(final List<View> views, final View rootView) {
        if (null == mProgressAnim) {
            mProgressAnim = ObjectAnimator.ofFloat(0f, 1f);
            mProgressAnim.setDuration(200);
            mProgressAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        mProgressAnim.removeAllUpdateListeners();
        mProgressAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
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
        if (null != mProgressAnim && mProgressAnim.isRunning()) {
            mProgressAnim.cancel();
            for (View v : mViewHolder.progressViews) {
                v.setTranslationY(0f);
            }
            for (View v : mViewHolder.scoreViews) {
                v.setTranslationY(0f);
            }
        }

        if (null != progressDrawable) progressDrawable.recycle();

        createProgressAnim(views, rootView);

        mProgressAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                RankedGameUtils.setViewsVisible(View.GONE, views);
                for (View v : views) {
                    v.setTranslationY(0f);
                }
            }
        });
        mProgressAnim.reverse();
    }

    @Override
    public int getContainerType() {
        return ScoreContainerType.STATE_PROGRESS;
    }

    @Override
    public void recycle() {
        mProgressAnim.removeAllListeners();
        mProgressAnim.removeAllUpdateListeners();

        if (null != mProgressAnim && mProgressAnim.isRunning()) {
            mProgressAnim.cancel();
            for (View v : mViewHolder.progressViews) {
                v.setTranslationY(0f);
            }
            for (View v : mViewHolder.scoreViews) {
                v.setTranslationY(0f);
            }
        }

        if (null != progressDrawable) progressDrawable.recycle();
    }

    @Override
    public List<View> getShowViews() {
        List<View> views = new ArrayList<>();
        views.addAll(mViewHolder.progressViews);
        views.addAll(mViewHolder.scoreViews);
        return views;
    }
}
