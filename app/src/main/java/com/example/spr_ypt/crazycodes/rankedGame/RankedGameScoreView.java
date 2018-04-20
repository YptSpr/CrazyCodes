package com.example.spr_ypt.crazycodes.rankedGame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public class RankedGameScoreView extends RelativeLayout {

    public static final int STATE_NONE = 0x000000;
    public static final int STATE_EGGS_START = 0x000001;
    public static final int STATE_EGGS = 0x000002;
    public static final int STATE_PROGRESS = 0x000003;
    public static final int STATE_RANK = 0x000004;
    private View mRootView;
    //背景
    private ImageView mIvBg;
    //任务进度相关
    private TextView mTvProgress;
    private ImageView mIvProgress;
    private ImageView mIvProgressIcon;
    private TextView mTvProgressTxt;
    //双方比分倒计时
    private TextView mTvAnchorScore;
    private TextView mTvOpponentScore;
    private TextView mTvScoreCountdown;
    //彩蛋任务开始倒计时相关
    private TextView mTvEggsCountdown;
    private TextView mTvEggsInfo;
    //彩蛋任务中进度相关
    private ImageView mIvEggsAnchorIcon;
    private ImageView mIvEggsAnchorProgress;
    private TextView mTvEggsAnchorInfo;
    private TextView mTvEggsAnchorScore;
    private ImageView mIvEggsOpponentIcon;
    private ImageView mIvEggsOpponentProgress;
    private TextView mTvEggsOpponentInfo;
    private TextView mTvEggsOpponentScore;
    //全站排名相关布局
    private ImageView mIvRank;
    private TextView mTvRankInfo;
    private List<View> progressViews;
    private List<View> scoreViews;
    private List<View> eggsStartViews;
    private List<View> eggsAnchorViews;
    private List<View> eggsOpponentViews;
    private List<View> rankViews;
    //state
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_EGGS_START, STATE_EGGS, STATE_PROGRESS, STATE_RANK})
    public @interface SHOW_STATE {
    }
    @SHOW_STATE
    private int mCurrentState = STATE_NONE;
    //anims
    private ValueAnimator mEggsStartAnim;
    private ValueAnimator mEggsAnim;
    private ValueAnimator mProgressAnim;
    private ValueAnimator mRankAnim;
    //drawables
    private BottomLineProgressDrawable bottomLineProgressDrawable;
    private GameProgressDrawable anchorDrawable;
    private GameProgressDrawable opponentDrawable;
    private GameProgressDrawable progressDrawable;


    public RankedGameScoreView(Context context) {
        super(context);
        init();
    }

    public RankedGameScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RankedGameScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RankedGameScoreView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.hani_view_ranked_game_score, this);
        initView();
    }

    private void initView() {
        mIvBg = (ImageView) mRootView.findViewById(R.id.iv_bg);

        mTvProgress = (TextView) mRootView.findViewById(R.id.tv_progress);
        mIvProgress = (ImageView) mRootView.findViewById(R.id.iv_progress);
        mIvProgressIcon = (ImageView) mRootView.findViewById(R.id.iv_progress_icon);
        mTvProgressTxt = (TextView) mRootView.findViewById(R.id.tv_progress_txt);
        progressViews = Arrays.asList(mTvProgress, mIvProgress, mIvProgressIcon, mTvProgressTxt);

        mTvAnchorScore = (TextView) mRootView.findViewById(R.id.tv_anchor_score);
        mTvOpponentScore = (TextView) findViewById(R.id.tv_opponent_score);
        mTvScoreCountdown = (TextView) findViewById(R.id.tv_score_countdown);
        scoreViews = Arrays.asList((View) mTvAnchorScore, mTvOpponentScore, mTvScoreCountdown);

        mTvEggsCountdown = (TextView) mRootView.findViewById(R.id.tv_eggs_countdown);
        mTvEggsInfo = (TextView) mRootView.findViewById(R.id.tv_eggs_info);
        eggsStartViews = Arrays.asList((View) mTvEggsCountdown, mTvEggsInfo);

        mIvEggsAnchorIcon = (ImageView) mRootView.findViewById(R.id.iv_eggs_anchor_icon);
        mIvEggsAnchorProgress = (ImageView) mRootView.findViewById(R.id.iv_eggs_anchor_progress);
        mTvEggsAnchorInfo = (TextView) mRootView.findViewById(R.id.tv_eggs_anchor_info);
        mTvEggsAnchorScore = (TextView) mRootView.findViewById(R.id.tv_eggs_anchor_score);
        eggsAnchorViews = Arrays.asList(mIvEggsAnchorIcon, mIvEggsAnchorProgress, mTvEggsAnchorInfo, mTvEggsAnchorScore);

        mIvEggsOpponentIcon = (ImageView) mRootView.findViewById(R.id.iv_eggs_opponent_icon);
        mIvEggsOpponentProgress = (ImageView) mRootView.findViewById(R.id.iv_eggs_opponent_progress);
        mTvEggsOpponentInfo = (TextView) mRootView.findViewById(R.id.tv_eggs_opponent_info);
        mTvEggsOpponentScore = (TextView) mRootView.findViewById(R.id.tv_eggs_opponent_score);
        eggsOpponentViews = Arrays.asList(mIvEggsOpponentIcon, mIvEggsOpponentProgress, mTvEggsOpponentInfo, mTvEggsOpponentScore);

        mIvRank = (ImageView) mRootView.findViewById(R.id.iv_rank);
        mTvRankInfo = (TextView) mRootView.findViewById(R.id.tv_rank_info);
        rankViews = Arrays.asList(mIvRank, mTvRankInfo);

        Typeface fontType = GlobalData.getInstance().getFontType();
        if (null!=fontType){
            mTvAnchorScore.setTypeface(fontType);
            mTvOpponentScore.setTypeface(fontType);
            mTvScoreCountdown.setTypeface(fontType);
            mTvEggsAnchorScore.setTypeface(fontType);
            mTvEggsOpponentScore.setTypeface(fontType);
        }
    }

    public void setShowState(@SHOW_STATE int state) {
        Log.e("spr_ypt", "setShowState: current=" + mCurrentState + "||state=" + state);
        if (mCurrentState == state) return;
        dropCurrentState(state);
        change2State(state);
        mCurrentState = state;
    }

    private void change2State(@SHOW_STATE int state) {
        switch (state) {
            case STATE_NONE:
                change2None(mCurrentState);
                break;
            case STATE_EGGS_START:
                change2EggsStart(mCurrentState);
                break;
            case STATE_EGGS:
                change2Eggs(mCurrentState);
                break;
            case STATE_PROGRESS:
                change2Progress(mCurrentState);
                break;
            case STATE_RANK:
                change2Rank(mCurrentState);
                break;
            default:
                break;
        }


    }

    private void dropCurrentState(@SHOW_STATE int newState) {
        switch (mCurrentState) {
            case STATE_NONE:
                dropNone(newState);
                break;
            case STATE_EGGS_START:
                dropEggsStart(newState);
                break;
            case STATE_EGGS:
                dropEggs(newState);
                break;
            case STATE_PROGRESS:
                dropProgress(newState);
                break;
            case STATE_RANK:
                dropRank(newState);
                break;
            default:
                break;
        }
    }

    private void change2None(int mCurrentState) {
        setViewsVisible(GONE, eggsStartViews, progressViews, scoreViews, eggsAnchorViews, eggsOpponentViews, rankViews);
        mIvBg.setImageDrawable(null);

    }

    private void dropNone(int newState) {
        //nothing need to do
    }

    private void change2EggsStart(int mCurrentState) {
        setViewsVisible(VISIBLE, eggsStartViews);
        mIvBg.setImageDrawable(RoundRectDrawableFactory.getDrawable(getResources().getDimension(R.dimen.demin_8dp), Color.parseColor("#4c000000")));
        //背景可能要走个lottie

        //动画
        if (null == mEggsStartAnim) {
            mEggsStartAnim = ObjectAnimator.ofFloat(0f, 1f);
            mEggsStartAnim.setInterpolator(new OvershootInterpolator());
            mEggsStartAnim.setDuration(300);
            mEggsStartAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float progress = (float) animation.getAnimatedValue();
                    for (View v : eggsStartViews) {
                        v.setScaleX(progress);
                        v.setScaleY(progress);
                    }
                }
            });
        }
        mEggsStartAnim.start();

    }

    private void dropEggsStart(int newState) {
        setViewsVisible(GONE, eggsStartViews);
        if (null != mEggsStartAnim && mEggsStartAnim.isRunning()) mEggsStartAnim.cancel();

    }

    private void change2Eggs(int mCurrentState) {
        setViewsVisible(VISIBLE, eggsAnchorViews, eggsOpponentViews);
        bottomLineProgressDrawable = new BottomLineProgressDrawable(Color.parseColor("#33000000"),
                Color.parseColor("#ff7446"), getResources().getDimension(R.dimen.demin_8dp), 0.05f);
        mIvBg.setImageDrawable(bottomLineProgressDrawable);
        bottomLineProgressDrawable.startAnim(0.8f,5000);

        anchorDrawable = new GameProgressDrawable(0.8f, Color.parseColor("#33ffffff"), Color.parseColor("#ff2d55"));
        mIvEggsAnchorProgress.setImageDrawable(anchorDrawable);

        opponentDrawable = new GameProgressDrawable(0.7f, Color.parseColor("#33ffffff"), Color.parseColor("#408aec"));
        mIvEggsOpponentProgress.setImageDrawable(opponentDrawable);

        if (null == mEggsAnim) {
            mEggsAnim = ObjectAnimator.ofFloat(0f, 1f);
            mEggsAnim.setInterpolator(new OvershootInterpolator());
            mEggsAnim.setDuration(300);
            mEggsAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float anchorX = -getWidth() * (1 - (float) animation.getAnimatedValue());
                    float opponentX = anchorX * 2f;
                    for (View v : eggsAnchorViews) {
                        v.setTranslationX(anchorX);
                    }
                    for (View v : eggsOpponentViews) {
                        v.setTranslationX(opponentX);
                    }
                }
            });
        }
        mEggsAnim.start();
    }

    private void dropEggs(int newState) {
        setViewsVisible(GONE, eggsAnchorViews, eggsOpponentViews);
        if (null != mEggsAnim && mEggsAnim.isRunning()) mEggsAnim.cancel();
        if (null != bottomLineProgressDrawable) bottomLineProgressDrawable.recycle();
        if (null != anchorDrawable) anchorDrawable.recycle();
        if (null != opponentDrawable) opponentDrawable.recycle();
    }

    private void change2Progress(int mCurrentState) {
        setViewsVisible(VISIBLE, progressViews, scoreViews);
        mIvBg.setImageDrawable(RoundRectDrawableFactory.getDrawable(getResources().getDimension(R.dimen.demin_8dp), Color.parseColor("#4c000000")));
        mTvAnchorScore.setBackground(RoundRectDrawableFactory.getLeftBottomDrawable(getResources().getDimension(R.dimen.demin_8dp), Color.parseColor("#ffc108")));
        mTvOpponentScore.setBackground(RoundRectDrawableFactory.getRightBottomDrawable(getResources().getDimension(R.dimen.demin_8dp), Color.parseColor("#b07643")));
        mTvScoreCountdown.setBackground(RoundRectDrawableFactory.getDrawable(getResources().getDimension(R.dimen.demin_8dp), Color.WHITE));
        progressDrawable = new GameProgressDrawable(0.4f, Color.parseColor("#33ffffff"), Color.parseColor("#cc00ef"));
        mIvProgress.setImageDrawable(progressDrawable);

        if (mCurrentState == STATE_RANK) return;//这种情况无需动画
        createProgressAnim();
        mProgressAnim.removeAllListeners();
        mProgressAnim.start();
    }

    private void createProgressAnim() {
        if (null == mProgressAnim) {
            mProgressAnim = ObjectAnimator.ofFloat(0f, 1f);
            mProgressAnim.setDuration(200);
            mProgressAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            mProgressAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float transY = getHeight() * (1 - (float) animation.getAnimatedValue());
                    for (View v : progressViews) {
                        v.setTranslationY(transY);
                    }
                    for (View v : scoreViews) {
                        v.setTranslationY(transY);
                    }
                }

            });
        }
    }

    private void dropProgress(int newState) {
        Log.e("spr_ypt", "dropProgress: ");
        if (null != mProgressAnim && mProgressAnim.isRunning()) {
            mProgressAnim.cancel();
            for (View v : progressViews) {
                v.setTranslationY(0f);
            }
            for (View v : scoreViews) {
                v.setTranslationY(0f);
            }
        }
        if (null != progressDrawable) progressDrawable.recycle();

        if (newState == STATE_RANK) {
            setViewsVisible(GONE, progressViews, scoreViews);
        } else {
            createProgressAnim();
            mProgressAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setViewsVisible(GONE, progressViews, scoreViews);
                    for (View v : progressViews) {
                        v.setTranslationY(0f);
                    }
                    for (View v : scoreViews) {
                        v.setTranslationY(0f);
                    }
                }
            });
            mProgressAnim.reverse();
        }
    }

    private void change2Rank(int mCurrentState) {
        setViewsVisible(VISIBLE, rankViews, scoreViews);
        Log.e("spr_ypt", "change2Rank: mIvRank.visible=" + mIvRank.getVisibility());
        Log.e("spr_ypt", "change2Rank: mIvRank.tranY=" + mIvRank.getTranslationY());
        mIvBg.setImageDrawable(RoundRectDrawableFactory.getDrawable(getResources().getDimension(R.dimen.demin_8dp), Color.parseColor("#4c000000")));
        mTvAnchorScore.setBackground(RoundRectDrawableFactory.getLeftBottomDrawable(getResources().getDimension(R.dimen.demin_8dp), Color.parseColor("#ffc108")));
        mTvOpponentScore.setBackground(RoundRectDrawableFactory.getRightBottomDrawable(getResources().getDimension(R.dimen.demin_8dp), Color.parseColor("#b07643")));
        mTvScoreCountdown.setBackground(RoundRectDrawableFactory.getDrawable(getResources().getDimension(R.dimen.demin_8dp), Color.WHITE));

        if (mCurrentState == STATE_PROGRESS) return;
        createRankAnim();
        mRankAnim.removeAllListeners();
        mRankAnim.start();
    }

    private void createRankAnim() {
        if (null == mRankAnim) {
            mRankAnim = ObjectAnimator.ofFloat(0f, 1f);
            mRankAnim.setDuration(200);
            mRankAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            mRankAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float transY = getHeight() * (1 - (float) animation.getAnimatedValue());
                    for (View v : rankViews) {
                        v.setTranslationY(transY);
                    }
                    for (View v : scoreViews) {
                        v.setTranslationY(transY);
                    }
                }
            });
        }
    }

    private void dropRank(int newState) {
        if (null != mRankAnim && mRankAnim.isRunning()) {
            mRankAnim.cancel();
            for (View v : rankViews) {
                v.setTranslationY(0f);
            }
            for (View v : scoreViews) {
                v.setTranslationY(0f);
            }
        }

        if (newState == STATE_PROGRESS) {
            setViewsVisible(GONE, rankViews, scoreViews);
        } else {
            createRankAnim();
            mRankAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setViewsVisible(GONE, rankViews, scoreViews);
                    for (View v : rankViews) {
                        v.setTranslationY(0f);
                    }
                    for (View v : scoreViews) {
                        v.setTranslationY(0f);
                    }
                }
            });
            mRankAnim.reverse();
        }

    }

    private void clearAnim(ValueAnimator anim) {
        if (null == anim) return;
        anim.removeAllUpdateListeners();
        anim.removeAllListeners();
        if (anim.isRunning()) anim.cancel();
    }

    private void setViewsVisible(int visible, List<View>... lists) {
        if (null != lists && lists.length > 0) {
            for (List<View> list : lists) {
                if (null != list && list.size() > 0) {
                    for (View v : list) {
                        if (v.getVisibility() != visible) {
                            v.setVisibility(visible);
                        }
                    }
                }
            }
        }
    }

    public void recycle() {
        clearAnim(mEggsStartAnim);
        clearAnim(mEggsAnim);
        clearAnim(mProgressAnim);
        clearAnim(mRankAnim);

        if (null != bottomLineProgressDrawable) bottomLineProgressDrawable.recycle();
        if (null != anchorDrawable) anchorDrawable.recycle();
        if (null != opponentDrawable) opponentDrawable.recycle();
        if (null != progressDrawable) progressDrawable.recycle();
    }



}
