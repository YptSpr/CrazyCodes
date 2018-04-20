package com.example.spr_ypt.crazycodes.rankedGame.score;


import android.view.View;

import com.example.spr_ypt.crazycodes.rankedGame.RankedGameEntity;

import java.util.List;

public abstract class AbsScoreContainer {
    protected ScoreViewHolder mViewHolder;
    protected RankedGameEntity mEntity;

    public AbsScoreContainer(ScoreViewHolder holder) {
        this.mViewHolder = holder;
    }

    public void setData(RankedGameEntity entity) {
        mEntity = entity;
        onDataChanged();
    }

    public abstract void onDataChanged();

    public abstract void changeAnim(List<View> views, View rootView);

    public abstract void dropAnim(List<View> views, View rootView);

    @ScoreContainerType.SHOW_STATE
    public abstract int getContainerType();

    public abstract void recycle();

    public abstract List<View> getShowViews();



}
