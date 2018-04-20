package com.example.spr_ypt.crazycodes.rankedGame.score.imp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.OvershootInterpolator;


import com.example.spr_ypt.crazycodes.R;
import com.example.spr_ypt.crazycodes.rankedGame.BottomLineProgressDrawable;
import com.example.spr_ypt.crazycodes.rankedGame.GameProgressDrawable;
import com.example.spr_ypt.crazycodes.rankedGame.RankedGameUtils;
import com.example.spr_ypt.crazycodes.rankedGame.RoundRectDrawableFactory;
import com.example.spr_ypt.crazycodes.rankedGame.score.AbsScoreContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreContainerType;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreViewHolder;

import java.util.ArrayList;
import java.util.List;

public class EggsContainer extends AbsScoreContainer {

    private BottomLineProgressDrawable bottomLineProgressDrawable;
    private GameProgressDrawable anchorDrawable;
    private GameProgressDrawable opponentDrawable;
    private ValueAnimator mEggsAnim;

    public EggsContainer(ScoreViewHolder holder) {
        super(holder);
    }

    @Override
    public void onDataChanged() {
            mViewHolder.mIvEggsAnchorIcon.setImageResource(R.drawable.hani_pk_arena_popup_random_scroll_icon_0);
            mViewHolder.mIvEggsOpponentIcon.setImageResource(R.drawable.hani_pk_arena_popup_random_scroll_icon_1);
            mViewHolder.mTvEggsAnchorScore.setText("520/1000");
            mViewHolder.mTvEggsOpponentScore.setText("520/1000");
      
            float anchorRate = 0.5f;
            if (null != anchorDrawable) anchorDrawable.setRate(anchorRate);
       
            float oppRate = 0.5f;
            if (null != opponentDrawable) opponentDrawable.setRate(oppRate);
        

                mViewHolder.mTvEggsScore.setText("领先\n" +"100万");
                mViewHolder.mTvEggsScore.setText("落后\n" +"100万");
        


    }

   

    @Override
    public void changeAnim(List<View> views, final View rootView) {
        if (null == views || views.size() == 0) return;

        RankedGameUtils.setViewsVisible(View.VISIBLE, getShowViews());
        //设置背景
        bottomLineProgressDrawable = new BottomLineProgressDrawable(RankedGameUtils.parseColor("#33000000"),
                RankedGameUtils.parseColor("#ff7446"), 24, 0.05f);
        mViewHolder.mIvBg.setImageDrawable(bottomLineProgressDrawable);
        //设置背光效果
        mViewHolder.mVRotateRay.setVisibility(View.GONE);
        mViewHolder.mVRotateRay.stop();
        //处理buffs
        //加载分数区域背景
        mViewHolder.mTvEggsScore.setBackgroundDrawable(RoundRectDrawableFactory.getRightDrawable(24, Color.parseColor("#0c000000")));
        //设置进度条
        bottomLineProgressDrawable.startAnim(0.8f,5000);


        anchorDrawable = new GameProgressDrawable(0.1f, RankedGameUtils.parseColor("#33ffffff"), RankedGameUtils.parseColor("#ff2d55"));
        mViewHolder.mIvEggsAnchorProgress.setImageDrawable(anchorDrawable);

        opponentDrawable = new GameProgressDrawable(0.1f, RankedGameUtils.parseColor("#33ffffff"), RankedGameUtils.parseColor("#408aec"));
        mViewHolder.mIvEggsOpponentProgress.setImageDrawable(opponentDrawable);
        //设置动画
        if (null == mEggsAnim) {
            mEggsAnim = ObjectAnimator.ofFloat(0f, 1f);
            mEggsAnim.setInterpolator(new OvershootInterpolator());
            mEggsAnim.setDuration(300);
            mEggsAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float anchorX = -rootView.getWidth() * (1 - (float) animation.getAnimatedValue());
                    float opponentX = anchorX * 2f;
                    for (View v : mViewHolder.eggsAnchorViews) {
                        v.setTranslationX(anchorX);
                    }
                    for (View v : mViewHolder.eggsOpponentViews) {
                        v.setTranslationX(opponentX);
                    }
                }
            });
        }
        mEggsAnim.start();
    }

    @Override
    public void dropAnim(List<View> views, View rootView) {
        RankedGameUtils.setViewsVisible(View.GONE, views);
        if (null != mEggsAnim && mEggsAnim.isRunning()){ mEggsAnim.cancel();
            for (View v : mViewHolder.eggsAnchorViews) {
                v.setTranslationX(0);
            }
            for (View v : mViewHolder.eggsOpponentViews) {
                v.setTranslationX(0);
            }
        }
        if (null != bottomLineProgressDrawable) bottomLineProgressDrawable.recycle();
        if (null != anchorDrawable) anchorDrawable.recycle();
        if (null != opponentDrawable) opponentDrawable.recycle();
    }

    @Override
    public int getContainerType() {
        return ScoreContainerType.STATE_EGGS;
    }

    @Override
    public void recycle() {
        mEggsAnim.removeAllListeners();
        mEggsAnim.removeAllUpdateListeners();
        if (null != mEggsAnim && mEggsAnim.isRunning()) mEggsAnim.cancel();
        if (null != bottomLineProgressDrawable) bottomLineProgressDrawable.recycle();
        if (null != anchorDrawable) anchorDrawable.recycle();
        if (null != opponentDrawable) opponentDrawable.recycle();
    }

    @Override
    public List<View> getShowViews() {
        List<View> views = new ArrayList<>();
        views.addAll(mViewHolder.eggsAnchorViews);
        views.addAll(mViewHolder.eggsOpponentViews);
        views.add(mViewHolder.mTvEggsScore);
        views.add(mViewHolder.mIvEggsScoreMaskBuff);
        return views;
    }
}
