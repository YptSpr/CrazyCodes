package com.example.spr_ypt.crazycodes.pkArenaPops;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.spr_ypt.crazycodes.R;

import java.lang.ref.WeakReference;

/**
 * Created by Spr_ypt on 2017/10/10.
 */

public class PkArenaRandomPopupWindow extends PopupWindow {
    private Activity mActivity;
    private View mContentView;
    private ImageView mIvAnchorIcon;//玩家头像
    private TextView mTvAnchorName;//玩家姓名
    private TextView mTvAnchorRank;//玩家排名
    private LottieAnimationView mLavVs;//vs图形
    private ImageView mIvOpponentIcon;//对手头像
    private TextView mTvOpponentName;//对手姓名
    private TextView mTvOpponentRank;//对手排名
    private TextView mTvBottom;//底部按钮
    private RandomPopHandler mHander;
    private int count;//开始战斗倒数
    private PkArenaRandomPopListener mListener;

    private int[] icons = {R.drawable.hani_pk_arena_popup_random_scroll_icon_0,
            R.drawable.hani_pk_arena_popup_random_scroll_icon_1,
            R.drawable.hani_pk_arena_popup_random_scroll_icon_2,
            R.drawable.hani_pk_arena_popup_random_scroll_icon_3,
            R.drawable.hani_pk_arena_popup_random_scroll_icon_4,};

    private ValueAnimator anim;//滚动动画


    private static final int STATE_NONE = 0;
    private static final int STATE_PREPARE = 1;
    private static final int STATE_MATCHING = 2;
    private static final int STATE_MATCHED = 3;
    private int state;


    public PkArenaRandomPopupWindow(Activity activity) {
        super(activity);
        ColorDrawable dw = new ColorDrawable(0x00000);
        setBackgroundDrawable(dw);
        mActivity = activity;
        mContentView = LayoutInflater.from(activity).inflate(R.layout.hani_popup_pk_arena_random, null);
        setContentView(mContentView);
        setTouchable(true);
        setOutsideTouchable(true);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight((int) mActivity.getResources().getDimension(R.dimen.demin_234_5dp));
        initView();
        initEvent();
        state = STATE_NONE;
    }

    private void initView() {
        mIvAnchorIcon = (ImageView) mContentView.findViewById(R.id.iv_anchor_icon);
        mTvAnchorName = (TextView) mContentView.findViewById(R.id.tv_anchor_name);
        mTvAnchorRank = (TextView) mContentView.findViewById(R.id.tv_anchor_rank);
        mLavVs = (LottieAnimationView) mContentView.findViewById(R.id.lav_vs);
        mIvOpponentIcon = (ImageView) mContentView.findViewById(R.id.iv_opponent_icon);
        mTvOpponentName = (TextView) mContentView.findViewById(R.id.tv_opponent_name);
        mTvOpponentRank = (TextView) mContentView.findViewById(R.id.tv_opponent_rank);
        mTvBottom = (TextView) mContentView.findViewById(R.id.tv_bottom);
    }

    private void initEvent() {
        mHander = new RandomPopHandler(this);
        mTvBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity.getString(R.string.pk_arena_popup_random_cancel).equals(mTvBottom.getText())) {
                    state = STATE_NONE;
                    mHander.removeMessages(RandomPopHandler.START_MATCH);
                    mHander.removeMessages(RandomPopHandler.FIGHT_COUNT);
                    dismiss();
                    if (null != mListener) {
                        mListener.onCancel();
                    }
                }
            }
        });

    }

    /**
     * 展示pop
     *
     * @param parent
     */
    public void show(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        if (state == STATE_NONE) {
            state = STATE_PREPARE;
            mTvBottom.setTextColor(Color.RED);
            mTvBottom.setText(mActivity.getString(R.string.pk_arena_popup_random_cancel));
            mHander.removeMessages(RandomPopHandler.START_MATCH);
            mHander.sendEmptyMessageDelayed(RandomPopHandler.START_MATCH, 3000);
        }
        startScroll();
    }

    boolean isOrder;

    private void startScroll() {
        if (state != STATE_NONE && state != STATE_MATCHED) {
            mTvOpponentName.setText("");
            mIvOpponentIcon.setImageResource(icons[(int) (Math.random() * 4)]);
            if (null == anim) {
                anim = ValueAnimator.ofFloat(0f, 1f, 0f);
                anim.setDuration(1000);
                anim.setInterpolator(new MyInterpolator(0.8f));
                anim.setRepeatCount(ValueAnimator.INFINITE);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float index = (float) animation.getAnimatedValue();
                        mIvOpponentIcon.setAlpha(index);
                    }
                });
                anim.addListener(new AnimatorListenerAdapter() {
                    private int lastIcon = -1;

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        super.onAnimationRepeat(animation);
                        mIvOpponentIcon.setImageResource(getNextIcon());
                    }

                    private int getNextIcon() {
                        int next = icons[(int) (Math.random() * 4)];
                        while (lastIcon == next) {
                            next = icons[(int) (Math.random() * 4)];
                        }
                        lastIcon = next;
                        return next;
                    }
                });
            }
            anim.start();
        }
    }

    private static class MyInterpolator implements TimeInterpolator {
        private float stayCenterRate;//停留在50%进度的时间占比

        public MyInterpolator(float stayCenterRate) {
            this.stayCenterRate = stayCenterRate;
        }

        @Override
        public float getInterpolation(float input) {
            float stayStar=0.5f - 0.5f * stayCenterRate;
            float stayEnd=0.5f + 0.5f * stayCenterRate;
            if (input < stayStar) {
                return input / (1 - stayCenterRate);
            } else if (input > stayEnd) {
                return (input - stayCenterRate) / (1 - stayCenterRate);
            } else {
                return 0.5f;
            }

        }
    }

    private void stopScroll() {
        if (null != anim && anim.isRunning()) anim.cancel();

        mIvOpponentIcon.setTranslationY(0);
        mIvOpponentIcon.setImageResource(icons[0]);
    }

    /**
     * 设置数据
     */
    public void setData() {

    }

    /**
     * 匹配成功
     */
    public void matchSuccess() {
        if (state == STATE_MATCHING) {
            mHander.removeMessages(RandomPopHandler.MATCH_TIMEOUT);
            mHander.removeMessages(RandomPopHandler.FIGHT_COUNT);
            state = STATE_MATCHED;
            count = 10;
            mTvBottom.setTextColor(mActivity.getResources().getColor(R.color.hani_c01with50alpha));
            mTvBottom.setText(String.format(mActivity.getString(R.string.pk_arena_popup_random_count), String.valueOf(count)));
            mHander.sendEmptyMessageDelayed(RandomPopHandler.FIGHT_COUNT, 1000);
            stopScroll();
        }
    }

    @Override
    public void dismiss() {
        if (null != anim && anim.isRunning()) anim.cancel();
        super.dismiss();
    }

    private static class RandomPopHandler extends Handler {
        private WeakReference<PkArenaRandomPopupWindow> reference;

        public static final int START_MATCH = 0x00010001;//开始匹配

        public static final int FIGHT_COUNT = 0x00010002;//倒计时

        public static final int MATCH_TIMEOUT = 0X00010003;//匹配超时

        public RandomPopHandler(PkArenaRandomPopupWindow popupWindow) {
            reference = new WeakReference<PkArenaRandomPopupWindow>(popupWindow);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PkArenaRandomPopupWindow popupWindow = reference.get();
            if (null == popupWindow) return;
            switch (msg.what) {
                case START_MATCH:
                    popupWindow.state = STATE_MATCHING;
                    popupWindow.mHander.removeMessages(RandomPopHandler.START_MATCH);
                    popupWindow.mHander.removeMessages(RandomPopHandler.FIGHT_COUNT);
                    if (null != popupWindow.mListener) {
                        popupWindow.mListener.onMatchStart();
                    }
                    popupWindow.mHander.removeMessages(RandomPopHandler.MATCH_TIMEOUT);
                    popupWindow.mHander.sendEmptyMessageDelayed(RandomPopHandler.MATCH_TIMEOUT, 10 * 1000);
                    break;
                case FIGHT_COUNT:
                    popupWindow.mHander.removeMessages(RandomPopHandler.FIGHT_COUNT);
                    popupWindow.mHander.removeMessages(RandomPopHandler.MATCH_TIMEOUT);
                    if (--popupWindow.count > 0) {
                        popupWindow.mTvBottom.setTextColor(popupWindow.mActivity.getResources().getColor(R.color.hani_c01with50alpha));
                        popupWindow.mTvBottom.setText(String.format(popupWindow.mActivity.getString(R.string.pk_arena_popup_random_count), String.valueOf(popupWindow.count)));
                        popupWindow.mHander.sendEmptyMessageDelayed(FIGHT_COUNT, 1000);
                    } else {
                        popupWindow.state = STATE_NONE;
                        popupWindow.dismiss();
                        if (null != popupWindow.mListener) {
                            popupWindow.mListener.onFightStart();
                        }
                    }
                    break;
                case MATCH_TIMEOUT:
                    popupWindow.state = STATE_NONE;
                    popupWindow.mHander.removeMessages(RandomPopHandler.MATCH_TIMEOUT);
                    Toast.makeText(popupWindow.mActivity, "匹配超时", Toast.LENGTH_LONG).show();
                    popupWindow.dismiss();
                    if (null != popupWindow.mListener) {
                        popupWindow.mListener.onMatchTimeOut();
                    }
                    break;
            }
        }
    }

    public void setPkArenaRandomPopListener(PkArenaRandomPopListener mListener) {
        this.mListener = mListener;
    }

    public interface PkArenaRandomPopListener {
        void onMatchStart();

        void onFightStart();

        void onCancel();

        void onMatchTimeOut();

    }

}
