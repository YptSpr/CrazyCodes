package com.example.spr_ypt.crazycodes.rankedGame.score.imp;

import android.view.View;


import com.example.spr_ypt.crazycodes.rankedGame.RankedGameUtils;
import com.example.spr_ypt.crazycodes.rankedGame.score.AbsScoreContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreContainerType;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreViewHolder;

import java.util.List;

public class NoneContainer extends AbsScoreContainer {

    public NoneContainer(ScoreViewHolder holder) {
        super(holder);
    }

    @Override
    public void onDataChanged() {
        //nothing need to do
    }

    @Override
    public void changeAnim(List<View> views,View rootView) {
        RankedGameUtils.setViewsVisible(View.GONE, mViewHolder.all);
        mViewHolder.mIvBg.setImageDrawable(null);
        mViewHolder.mVRotateRay.setVisibility(View.GONE);
        mViewHolder.mVRotateRay.stop();
    }

    @Override
    public void dropAnim(List<View> views,View rootView) {
        //nothing need to do
    }

    @Override
    public int getContainerType() {
        return ScoreContainerType.STATE_NONE;
    }

    @Override
    public void recycle() {
        //nothing need to do
    }

    @Override
    public List<View> getShowViews() {
        return mViewHolder.all;
    }
}
