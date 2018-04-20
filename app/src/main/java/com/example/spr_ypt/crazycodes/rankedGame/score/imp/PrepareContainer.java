package com.example.spr_ypt.crazycodes.rankedGame.score.imp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.spr_ypt.crazycodes.rankedGame.RankedGameUtils;
import com.example.spr_ypt.crazycodes.rankedGame.RoundRectDrawableFactory;
import com.example.spr_ypt.crazycodes.rankedGame.score.AbsScoreContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreContainerType;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreViewHolder;

import java.util.List;

public class PrepareContainer extends AbsScoreContainer {
    private ValueAnimator mAnim;
    public PrepareContainer(ScoreViewHolder holder) {
        super(holder);
    }

    @Override
    public void onDataChanged() {
        RankedGameUtils.resetTxtTimer(520, mViewHolder.mTvEggsCountdown, "%s秒后");
        mViewHolder.mTvEggsInfo.setText("老子即将闪亮登场");
    }

    @Override
    public void changeAnim(final List<View> views,View rootView) {
        RankedGameUtils.setViewsVisible(View.VISIBLE, getShowViews());
        mViewHolder.mIvBg.setImageDrawable(RoundRectDrawableFactory.getDrawable(24, RankedGameUtils.parseColor("#4c000000")));
        mViewHolder.mVRotateRay.setVisibility(View.VISIBLE);
        mViewHolder.mVRotateRay.start();

        //动画
        if (null == mAnim) {
            mAnim = ObjectAnimator.ofFloat(0f, 1f);
            mAnim.setInterpolator(new OvershootInterpolator());
            mAnim.setDuration(300);
            mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float progress = (float) animation.getAnimatedValue();
                    for (View v : views) {
                        v.setScaleX(progress);
                        v.setScaleY(progress);
                    }
                }
            });
        }
        mAnim.start();
    }

    @Override
    public void dropAnim(List<View> views,View rootView) {
        RankedGameUtils.setViewsVisible(View.GONE, views);
        if (null != mAnim && mAnim.isRunning()) {
            mAnim.cancel();
            for (View v : getShowViews()) {
                v.setScaleX(1f);
                v.setScaleY(1f);
            }
        }
    }

    @Override
    public int getContainerType() {
        return ScoreContainerType.STATE_PREPARE;
    }

    @Override
    public void recycle() {
        if (null==mAnim) return;
        mAnim.removeAllUpdateListeners();
        mAnim.removeAllListeners();
        if (mAnim.isRunning()) mAnim.cancel();
    }

    @Override
    public List<View> getShowViews() {
        return mViewHolder.prepareViews;
    }
}
