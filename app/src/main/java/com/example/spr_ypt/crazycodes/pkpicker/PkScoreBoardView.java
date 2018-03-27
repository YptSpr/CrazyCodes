package com.example.spr_ypt.crazycodes.pkpicker;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.R;


/**
 * Created by Spr_ypt on 2017/9/29.
 * pk画面底部计分板
 */

public class PkScoreBoardView extends FrameLayout {
    private View mRootView;
    private ImageView mIvPkArenaScoreBoard;
    private TextView mTvPkArenaAnchorScore;
    private TextView mTvPkArenaOpponentScore;
    private PkProgressDrawableV3 mProgressDrawable;
    private View mVPkArenaAnchorThumb;
    private View mVPkArenaOpponentThumb;
    private ObjectAnimator bombLeftAnim, bombRightAnim;
    private long anchorScore, opponentScore;

    public PkScoreBoardView(Context context) {
        super(context);
        init();
    }

    public PkScoreBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PkScoreBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PkScoreBoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {
        mRootView = inflate(getContext(), R.layout.hani_view_window_pk_arena_score_board, this);
        initView(mRootView);
        initAnim();
    }

    private void initAnim() {
        bombLeftAnim = ObjectAnimator.ofFloat(mTvPkArenaAnchorScore, "textSize", 13, 28, 13);
        bombLeftAnim.setInterpolator(new DecelerateInterpolator());
        bombLeftAnim.setDuration(500);

        bombRightAnim = ObjectAnimator.ofFloat(mTvPkArenaOpponentScore, "textSize", 13, 28, 13);
        bombRightAnim.setInterpolator(new DecelerateInterpolator());
        bombRightAnim.setDuration(500);

    }


    private void initView(View mRootView) {
        mIvPkArenaScoreBoard = (ImageView) mRootView.findViewById(R.id.iv_pk_arena_score_board);
        mTvPkArenaAnchorScore = (TextView) mRootView.findViewById(R.id.tv_pk_arena_anchor_score);
        mTvPkArenaOpponentScore = (TextView) mRootView.findViewById(R.id.tv_pk_arena_opponent_score);
        mVPkArenaAnchorThumb = findViewById(R.id.v_pk_arena_anchor_thumb);
        mVPkArenaOpponentThumb = findViewById(R.id.v_pk_arena_opponent_thumb);
        mProgressDrawable = new PkProgressDrawableV3(0.5f);
        mIvPkArenaScoreBoard.setImageDrawable(mProgressDrawable);
    }


    /**
     * 设置pk双方星光值
     *
     * @param anchorScore
     * @param opponentScore
     */
    public void setScore(int anchorScore, int opponentScore) {

        if (anchorScore != this.anchorScore) {
            mTvPkArenaAnchorScore.setText(String.valueOf(anchorScore));
            bombLeftAnim.start();
        }
        if (opponentScore != this.opponentScore) {
            mTvPkArenaOpponentScore.setText(String.valueOf(opponentScore));
            bombRightAnim.start();
        }

        float rate = 0.5f;
        if (anchorScore > 0 && opponentScore > 0) {
            rate = (float) anchorScore / (float) (anchorScore + opponentScore);
            rate = rate > 0.8f ? 0.8f : rate < 0.2 ? 0.2f : rate;//控制进度边界
        } else if (anchorScore > 0) {
            rate = 0.8f;
        } else if (opponentScore > 0) {
            rate = 0.2f;
        }

        mProgressDrawable.setRate(rate);
        this.anchorScore = anchorScore;
        this.opponentScore = opponentScore;
    }

    public void recycle() {
        mProgressDrawable.recycle();
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    private int px2sp(float pxValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private class BombEvaluator implements TypeEvaluator<Integer> {
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {

            Integer max = startValue > endValue ? startValue * 2 : endValue * 2;
            Integer index = (max - endValue) / (2 * max - startValue - endValue);
            if (fraction < index) {
                return (int) (startValue + fraction * (max - startValue));
            } else {
                return (int) (endValue + (1 - fraction) * (max - endValue));
            }
        }
    }
}
