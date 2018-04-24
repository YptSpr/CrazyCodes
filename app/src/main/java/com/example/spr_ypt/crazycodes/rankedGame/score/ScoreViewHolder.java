package com.example.spr_ypt.crazycodes.rankedGame.score;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.spr_ypt.crazycodes.R;
import com.example.spr_ypt.crazycodes.rankedGame.GlobalData;
import com.example.spr_ypt.crazycodes.rankedGame.RankedRotateRayView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreViewHolder {
    //背景
    public ImageView mIvBg;
    public RankedRotateRayView mVRotateRay;
    //任务进度相关
    public TextView mTvProgress;
    public ImageView mIvProgress;
    public ImageView mIvProgressIcon;
    public TextView mTvProgressTxt;
    //双方比分倒计时
    public TextView mTvAnchorScore;
    public TextView mTvAnchorScorePercent;//任务加成展示tv
    public ImageView mIvAnchorScoreBloodBuff;//加血buff图区
    public ImageView mIvAnchorScoreMaskBuff;//迷雾
    public TextView mTvOpponentScore;
    public ImageView mIvOpponentScoreBloodBuff;//对手加血
    public ImageView mIvOpponentScoreMaskBuff;//对手迷雾
    public TextView mTvScoreCountdown;
    //彩蛋任务开始倒计时相关
    public TextView mTvEggsCountdown;
    public TextView mTvEggsInfo;
    //彩蛋任务中进度相关
    public ImageView mIvEggsAnchorIcon;
    public ImageView mIvEggsAnchorProgress;
    public TextView mTvEggsAnchorInfo;
    public TextView mTvEggsAnchorScore;
    public ImageView mIvEggsAnchorBloodBuff;
    public ImageView mIvEggsOpponentIcon;
    public ImageView mIvEggsOpponentProgress;
    public TextView mTvEggsOpponentInfo;
    public TextView mTvEggsOpponentScore;
    public ImageView mIvEggsOpponentBloodBuff;
    public TextView mTvEggsScore;
    public ImageView mIvEggsScoreMaskBuff;
    //全站排名相关布局
    public ImageView mIvRank;
    public TextView mTvRankInfo;
    //任务成功
    public TextView mTvTaskSuccess;
    //任务失败
    public TextView mTvTaskFailed;
    //pk胜利
    public TextView mTvSuccessFlag;
    public TextView mTvSuccessInfo;
    //开场布局主播部分
    public ImageView mIvStartAnchorBg;
    public ImageView mIvStartAnchorIcon;
    public TextView mTvStartAnchorName;
    public TextView mTvStartAnchorInfo;
    //开场布局对手部分
    public ImageView mIvStartOppBg;
    public ImageView mIvStartOppIcon;
    public TextView mTvStartOppName;
    public TextView mTvStartOppInfo;
    //开场布局时间
    public TextView mTvStartTimer;

    //view集合
    public List<View> progressViews;
    public List<View> scoreViews;
    public List<View> prepareViews;
    public List<View> eggsAnchorViews;
    public List<View> eggsOpponentViews;
    public List<View> rankViews;
    public List<View> missionClearViews;
    public List<View> missionFailedViews;
    public List<View> successViews;
    public List<View> pkStartViews;
    public List<View> pkStartLeftViews;
    public List<View> pkStartRightViews;

    //全部views，除了背景和炫酷背光
    public List<View> all;

    public ScoreViewHolder(View mRootView) {
        mIvBg = (ImageView) mRootView.findViewById(R.id.iv_bg);
        mVRotateRay = (RankedRotateRayView) mRootView.findViewById(R.id.v_rotate_ray);

        mTvProgress = (TextView) mRootView.findViewById(R.id.tv_progress);
        mIvProgress = (ImageView) mRootView.findViewById(R.id.iv_progress);
        mIvProgressIcon = (ImageView) mRootView.findViewById(R.id.iv_progress_icon);
        mTvProgressTxt = (TextView) mRootView.findViewById(R.id.tv_progress_txt);
        progressViews = Arrays.asList(mTvProgress, mIvProgress, mIvProgressIcon, mTvProgressTxt);

        mTvAnchorScore = (TextView) mRootView.findViewById(R.id.tv_anchor_score);

        mIvAnchorScoreBloodBuff = (ImageView) mRootView.findViewById(R.id.iv_anchor_score_blood_buff);
        mIvAnchorScoreMaskBuff = (ImageView) mRootView.findViewById(R.id.iv_anchor_score_mask_buff);
        mTvOpponentScore = (TextView) mRootView.findViewById(R.id.tv_opponent_score);
        mIvOpponentScoreBloodBuff = (ImageView) mRootView.findViewById(R.id.iv_opponent_score_blood_buff);
        mIvOpponentScoreMaskBuff = (ImageView) mRootView.findViewById(R.id.iv_opponent_score_mask_buff);
        mTvScoreCountdown = (TextView) mRootView.findViewById(R.id.tv_score_countdown);
        scoreViews = Arrays.asList(mTvAnchorScore, mIvAnchorScoreBloodBuff, mIvAnchorScoreMaskBuff,
                mTvOpponentScore, mIvOpponentScoreBloodBuff, mIvOpponentScoreMaskBuff, mTvScoreCountdown);

        mTvEggsCountdown = (TextView) mRootView.findViewById(R.id.tv_eggs_countdown);
        mTvEggsInfo = (TextView) mRootView.findViewById(R.id.tv_eggs_info);
        prepareViews = Arrays.asList((View) mTvEggsCountdown, mTvEggsInfo);

        mIvEggsAnchorIcon = (ImageView) mRootView.findViewById(R.id.iv_eggs_anchor_icon);
        mIvEggsAnchorProgress = (ImageView) mRootView.findViewById(R.id.iv_eggs_anchor_progress);
        mTvEggsAnchorInfo = (TextView) mRootView.findViewById(R.id.tv_eggs_anchor_info);
        mTvEggsAnchorScore = (TextView) mRootView.findViewById(R.id.tv_eggs_anchor_score);
        mIvEggsAnchorBloodBuff = (ImageView) mRootView.findViewById(R.id.iv_eggs_anchor_blood_buff);
        eggsAnchorViews = Arrays.asList(mIvEggsAnchorIcon, mIvEggsAnchorProgress, mTvEggsAnchorInfo, mTvEggsAnchorScore,mIvEggsAnchorBloodBuff);

        mIvEggsOpponentIcon = (ImageView) mRootView.findViewById(R.id.iv_eggs_opponent_icon);
        mIvEggsOpponentProgress = (ImageView) mRootView.findViewById(R.id.iv_eggs_opponent_progress);
        mTvEggsOpponentInfo = (TextView) mRootView.findViewById(R.id.tv_eggs_opponent_info);
        mTvEggsOpponentScore = (TextView) mRootView.findViewById(R.id.tv_eggs_opponent_score);
        mIvEggsOpponentBloodBuff = (ImageView) mRootView.findViewById(R.id.iv_eggs_opponent_blood_buff);
        eggsOpponentViews = Arrays.asList(mIvEggsOpponentIcon, mIvEggsOpponentProgress, mTvEggsOpponentInfo, mTvEggsOpponentScore,mIvEggsOpponentBloodBuff);


        mTvEggsScore = mRootView.findViewById(R.id.tv_eggs_score);
        mIvEggsScoreMaskBuff = (ImageView) mRootView.findViewById(R.id.iv_eggs_score_mask_buff);

        mIvRank = (ImageView) mRootView.findViewById(R.id.iv_rank);
        mTvRankInfo = (TextView) mRootView.findViewById(R.id.tv_rank_info);
        rankViews = Arrays.asList(mIvRank, mTvRankInfo);

        mTvTaskSuccess = (TextView) mRootView.findViewById(R.id.tv_task_success);
        mTvAnchorScorePercent=(TextView)mRootView.findViewById(R.id.tv_anchor_score_percent);
        missionClearViews = Arrays.asList((View) mTvTaskSuccess,mTvAnchorScorePercent);

        mTvTaskFailed = (TextView) mRootView.findViewById(R.id.tv_task_failed);
        missionFailedViews = Arrays.asList((View) mTvTaskFailed);

        mTvSuccessFlag = (TextView) mRootView.findViewById(R.id.tv_success_flag);
        mTvSuccessInfo = (TextView) mRootView.findViewById(R.id.tv_success_info);
        successViews = Arrays.asList((View) mTvSuccessFlag, mTvSuccessInfo);

        mIvStartAnchorBg = (ImageView) mRootView.findViewById(R.id.iv_start_anchor_bg);
        mIvStartAnchorIcon = (ImageView) mRootView.findViewById(R.id.iv_start_anchor_icon);
        mTvStartAnchorName = (TextView) mRootView.findViewById(R.id.tv_start_anchor_name);
        mTvStartAnchorInfo = (TextView) mRootView.findViewById(R.id.tv_start_anchor_info);
        pkStartLeftViews=Arrays.asList(mIvStartAnchorBg, mIvStartAnchorIcon, mTvStartAnchorName, mTvStartAnchorInfo);
        mIvStartOppBg = (ImageView) mRootView.findViewById(R.id.iv_start_opp_bg);
        mIvStartOppIcon = (ImageView) mRootView.findViewById(R.id.iv_start_opp_icon);
        mTvStartOppName = (TextView) mRootView.findViewById(R.id.tv_start_opp_name);
        mTvStartOppInfo = (TextView) mRootView.findViewById(R.id.tv_start_opp_info);
        pkStartRightViews=Arrays.asList(mIvStartOppBg, mIvStartOppIcon, mTvStartOppName, mTvStartOppInfo);
        mTvStartTimer = (TextView) mRootView.findViewById(R.id.tv_start_timer);
        pkStartViews = Arrays.asList(mIvStartAnchorBg, mIvStartAnchorIcon, mTvStartAnchorName, mTvStartAnchorInfo,
                mIvStartOppBg, mIvStartOppIcon, mTvStartOppName, mTvStartOppInfo, mTvStartTimer);

        Typeface fontType = GlobalData.getInstance().getFontType();
        if (null != fontType) {
            mTvProgressTxt.setTypeface(fontType);
            mTvAnchorScore.setTypeface(fontType);
            mTvOpponentScore.setTypeface(fontType);
            mTvScoreCountdown.setTypeface(fontType);
            mTvEggsAnchorScore.setTypeface(fontType);
            mTvEggsOpponentScore.setTypeface(fontType);
            mTvAnchorScorePercent.setTypeface(fontType);
            mTvStartTimer.setTypeface(fontType);
        }

        all = new ArrayList<>();
        all.addAll(progressViews);
        all.addAll(scoreViews);
        all.addAll(prepareViews);
        all.addAll(eggsAnchorViews);
        all.addAll(eggsOpponentViews);
        all.addAll(rankViews);
        all.addAll(missionClearViews);
        all.addAll(missionFailedViews);
        all.addAll(successViews);
        all.addAll(pkStartViews);
        all.add(mTvEggsScore);
        all.add(mIvEggsScoreMaskBuff);

    }
}
