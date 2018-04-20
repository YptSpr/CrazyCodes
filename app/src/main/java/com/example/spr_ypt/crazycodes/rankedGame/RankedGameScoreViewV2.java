package com.example.spr_ypt.crazycodes.rankedGame;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


import com.example.spr_ypt.crazycodes.R;
import com.example.spr_ypt.crazycodes.rankedGame.score.AbsScoreContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreContainerType;
import com.example.spr_ypt.crazycodes.rankedGame.score.ScoreViewHolder;
import com.example.spr_ypt.crazycodes.rankedGame.score.imp.EggsContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.imp.EndContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.imp.MissionClearContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.imp.MissionFailedContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.imp.NoneContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.imp.OpenContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.imp.PrepareContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.imp.ProgressContainer;
import com.example.spr_ypt.crazycodes.rankedGame.score.imp.RankContainer;

import java.util.HashMap;
import java.util.Map;

public class RankedGameScoreViewV2 extends RelativeLayout {
    private View mRootView;
    private ScoreViewHolder mViewHolder;
    private Map<Integer, AbsScoreContainer> containerMap;
    private AbsScoreContainer mCurrentContainer;

    private boolean isFirstShow = true;


    public RankedGameScoreViewV2(Context context) {
        super(context);
        init();
    }

    public RankedGameScoreViewV2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RankedGameScoreViewV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RankedGameScoreViewV2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.hani_view_ranked_game_score, this);
        mViewHolder = new ScoreViewHolder(mRootView);
        creatContainers();
    }

    private void creatContainers() {
        containerMap = new HashMap<>();
        AbsScoreContainer noneContainer = new NoneContainer(mViewHolder);
        containerMap.put(noneContainer.getContainerType(), noneContainer);
        mCurrentContainer = noneContainer;

        AbsScoreContainer prepareContainer = new PrepareContainer(mViewHolder);
        containerMap.put(prepareContainer.getContainerType(), prepareContainer);

        AbsScoreContainer eggsContainer = new EggsContainer(mViewHolder);
        containerMap.put(eggsContainer.getContainerType(), eggsContainer);

        AbsScoreContainer progressContainer = new ProgressContainer(mViewHolder);
        containerMap.put(progressContainer.getContainerType(), progressContainer);

        AbsScoreContainer endContainer = new EndContainer(mViewHolder);
        containerMap.put(endContainer.getContainerType(), endContainer);

        AbsScoreContainer missionClearContainer = new MissionClearContainer(mViewHolder);
        containerMap.put(missionClearContainer.getContainerType(), missionClearContainer);

        AbsScoreContainer missionFailedContainer = new MissionFailedContainer(mViewHolder);
        containerMap.put(missionFailedContainer.getContainerType(), missionFailedContainer);

        AbsScoreContainer openContainer = new OpenContainer(mViewHolder);
        containerMap.put(openContainer.getContainerType(), openContainer);

        AbsScoreContainer rankContainer = new RankContainer(mViewHolder);
        containerMap.put(rankContainer.getContainerType(), rankContainer);
    }

    private RankedGameEntity mEntity;

    private Handler mDelayHandler = new Handler();



    public void changeContainerState(@ScoreContainerType.SHOW_STATE int state) {

        Log.e("spr_ypt=>Rank", "changeContainerState cur=" + mCurrentContainer.getContainerType()+"||tar=" + state);


        if (mCurrentContainer.getContainerType() == state) {
            mCurrentContainer.setData(mEntity);
        } else {
            AbsScoreContainer newContainer = containerMap.get(state);
            mCurrentContainer.dropAnim(RankedGameUtils.getOwnVies(mCurrentContainer.getShowViews(), newContainer.getShowViews()), this);
            newContainer.setData(mEntity);
            newContainer.changeAnim(RankedGameUtils.getOwnVies(newContainer.getShowViews(), mCurrentContainer.getShowViews()), this);
            mCurrentContainer = newContainer;
        }
    }

    public void recycle() {
        if (null != mCurrentContainer) mCurrentContainer.recycle();
        mDelayHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE) {
            if (getVisibility() != visibility) {
                isFirstShow = true;
            }else {
                isFirstShow=false;
            }
        } else {
            isFirstShow = false;
            changeContainerState(ScoreContainerType.STATE_NONE);
        }
        Log.e("spr_ypt=>Rank", "setVisibility isFirstShow=" + isFirstShow);
        super.setVisibility(visibility);
    }
}
