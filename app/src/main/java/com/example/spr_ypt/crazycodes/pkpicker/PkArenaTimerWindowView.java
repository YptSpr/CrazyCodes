package com.example.spr_ypt.crazycodes.pkpicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.R;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Spr_ypt on 2017/10/12.
 */

public class PkArenaTimerWindowView extends FrameLayout {
    private View mRootView;
    private TextView mTvPkArenaTimer;
    private ImageView mIvPkArenaTimerClose;
    private PkArenaTimerListener mListener;
    private CountDownTimer mTimer;
    private ImageView mIvPkArenaTitleIcon;
    private int closeBtnVisible;
    private WipeDrawable mWipeDrawable;
    private TextView mTvPkArenaCrit;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_PUNISH = 1;
    private static final int STATE_CRIT = 2;
    private int state = STATE_NORMAL;


    public PkArenaTimerWindowView(Context context) {
        super(context);
        init();
    }

    public PkArenaTimerWindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PkArenaTimerWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PkArenaTimerWindowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {
        initView();
        initEvent();

    }

    private void initView() {
        mRootView = inflate(getContext(), R.layout.hani_view_window_pk_arena_timer_view, this);
        mTvPkArenaTimer = (TextView) mRootView.findViewById(R.id.tv_pk_arena_timer);
        mIvPkArenaTimerClose = (ImageView) mRootView.findViewById(R.id.iv_pk_arena_timer_close);
        mIvPkArenaTitleIcon = (ImageView) mRootView.findViewById(R.id.iv_pk_arena_title_icon);
        mTvPkArenaCrit = (TextView) mRootView.findViewById(R.id.tv_pk_arena_crit);
        setBackgroundResource(R.drawable.hani_window_view_pk_arena_timer_bg);
    }

    private void initEvent() {
        mIvPkArenaTimerClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvPkArenaTimerClose.setVisibility(GONE);
                closeBtnVisible = GONE;
                getLayoutParams().width = (int) getContext().getResources().getDimension(R.dimen.demin_75dp);
                requestLayout();
                if (null != mListener) {
                    mListener.onCloseClick();
                }
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getLayoutParams().width = (int) getContext().getResources().getDimension(R.dimen.demin_100dp);
                requestLayout();
                mIvPkArenaTimerClose.setVisibility(VISIBLE);
                closeBtnVisible = VISIBLE;
            }
        });
    }

    private static final int CHNAGE_ANIM_DURATION = 300;

    public void changeToPunish() {
        if (state == STATE_PUNISH) return;
        ValueAnimator animator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(CHNAGE_ANIM_DURATION / 2);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                getLayoutParams().width = closeBtnVisible == VISIBLE ? (int) getResources().getDimension(R.dimen.demin_120dp) :
                        (int) getResources().getDimension(R.dimen.demin_105_5dp);
                requestLayout();
                mIvPkArenaTitleIcon.getLayoutParams().width = (int) getResources().getDimension(R.dimen.demin_47dp);
                mIvPkArenaTitleIcon.requestLayout();
                mIvPkArenaTitleIcon.setImageResource(R.drawable.hani_pk_arena_window_view_timer_vs_punish);
            }
        });
        animator.start();
        startBreathAnim(false);
        state = STATE_PUNISH;
    }

    public void changeToNormal() {
        if (state == STATE_NORMAL) return;

        if (state == STATE_CRIT) {
            int width = closeBtnVisible == VISIBLE ? (int) getResources().getDimension(R.dimen.demin_100dp) :
                    (int) getResources().getDimension(R.dimen.demin_75dp);
            ValueAnimator animator = ObjectAnimator.ofInt(getLayoutParams().width, width);
            animator.setDuration(CHNAGE_ANIM_DURATION);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    getLayoutParams().width = (int) animation.getAnimatedValue();
                    requestLayout();
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (null != mWipeDrawable) mWipeDrawable.recycle();
                    mTvPkArenaCrit.setVisibility(GONE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mTvPkArenaTimer.setVisibility(VISIBLE);
                    mIvPkArenaTitleIcon.setVisibility(VISIBLE);
                    mIvPkArenaTitleIcon.setImageResource(R.drawable.hani_pk_arena_window_view_timer_vs);
                    mIvPkArenaTimerClose.setVisibility(closeBtnVisible);
                    setBackgroundResource(R.drawable.hani_window_view_pk_arena_timer_bg);
                }
            });
            animator.start();
            changeBgColor(Color.parseColor("#fa1944"), Color.parseColor("#62000000"));
        } else if (state == STATE_PUNISH) {
            ValueAnimator animator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
            animator.setRepeatCount(1);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setDuration(CHNAGE_ANIM_DURATION / 2);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                    getLayoutParams().width = closeBtnVisible == VISIBLE ? (int) getResources().getDimension(R.dimen.demin_100dp) :
                            (int) getResources().getDimension(R.dimen.demin_75dp);
                    requestLayout();
                    mIvPkArenaTitleIcon.getLayoutParams().width = (int) getResources().getDimension(R.dimen.demin_20dp);
                    mIvPkArenaTitleIcon.requestLayout();
                    mIvPkArenaTitleIcon.setImageResource(R.drawable.hani_pk_arena_window_view_timer_vs);
                }
            });
            animator.start();
        }
        startBreathAnim(false);
        state = STATE_NORMAL;
    }

    public void changeToCrit() {
        if (state == STATE_CRIT) return;
        ValueAnimator animator = ObjectAnimator.ofInt(getLayoutParams().width, (int) getResources().getDimension(R.dimen.demin_120dp));
        animator.setDuration(CHNAGE_ANIM_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getLayoutParams().width = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mTvPkArenaTimer.setVisibility(GONE);
                mIvPkArenaTitleIcon.setVisibility(GONE);
                mIvPkArenaTimerClose.setVisibility(GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTvPkArenaCrit.setVisibility(VISIBLE);
                mWipeDrawable = new WipeDrawable();
                mWipeDrawable.startWipe(0.2f, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        changeToNormal();
                    }
                });
                setBackground(mWipeDrawable);
                startBreathAnim(true);
            }
        });
        animator.start();
        changeBgColor(Color.parseColor("#62000000"), Color.parseColor("#eb5a80"));
        state = STATE_CRIT;
    }

    private void changeBgColor(int start, int end) {
        ValueAnimator animator = ObjectAnimator.ofArgb(start, end);
        animator.setDuration((long) (CHNAGE_ANIM_DURATION * 0.9));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (Integer) animation.getAnimatedValue();
                setBackgroundDrawable(new WipeDrawable(color, color));
            }
        });
        animator.start();
    }

    ValueAnimator breathAnim;

    private void startBreathAnim(boolean startOrEnd) {
        if (startOrEnd) {
            breathAnim = ObjectAnimator.ofFloat(1f, 1.2f);
            breathAnim.setRepeatCount(ValueAnimator.INFINITE);
            breathAnim.setRepeatMode(ValueAnimator.REVERSE);
            breathAnim.setInterpolator(new OvershootInterpolator(4f));
            breathAnim.setDuration(500);
            breathAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setScaleX((Float) animation.getAnimatedValue());
                    setScaleY((Float) animation.getAnimatedValue());
                }
            });
            breathAnim.start();
        } else {
            if (null != breathAnim && breathAnim.isRunning()) breathAnim.cancel();
            setScaleX(1f);
            setScaleY(1f);
        }
    }

    /**
     * 重置计时器
     *
     * @param time
     */
    public void resetTimer(long time) {
        mTvPkArenaTimer.setText(formatTime(time));
        if (null != mTimer) {
            mTimer.cancel();
        }
        mTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (mTvPkArenaTimer.getVisibility() == VISIBLE) {
                    mTvPkArenaTimer.setText(formatTime(millisUntilFinished));
                }
            }

            @Override
            public void onFinish() {
                mTvPkArenaTimer.setText("0:00");
            }
        };
    }

    /**
     * 开始倒计时
     */
    public void startTimerDown() {
        if (null != mTimer) mTimer.start();
    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    private String formatTime(long time) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return format.format(new Date(time));

    }


    public void recycle() {
        if (null != mTimer) mTimer.cancel();
    }

    public void setPkArenaTimerListener(PkArenaTimerListener mListener) {
        this.mListener = mListener;
    }

    public interface PkArenaTimerListener {
        void onCloseClick();
    }

    public static class WipeDrawable extends Drawable {


        private Paint mPaint;
        private int startColor = Color.parseColor("#eb5a80");
        private int endColor = Color.parseColor("#fa1944");
        private ValueAnimator anim;
        private float colorRate;

        public WipeDrawable(int startColor, int endColor) {
            this();
            this.startColor = startColor;
            this.endColor = endColor;
        }

        public WipeDrawable() {
            mPaint = new Paint();
        }

        public void startWipe(float startRate, Animator.AnimatorListener listener) {
            if (startColor == endColor) return;
            Log.e("spr_ypt", "startWipe:");
            anim = ObjectAnimator.ofFloat(startRate, 1f);
            anim.setDuration(5000);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    colorRate = (float) animation.getAnimatedValue();
                    Log.e("spr_ypt", "onAnimationUpdate: colorRate=" + colorRate);
                    invalidateSelf();
                }
            });
            anim.addListener(listener);
            anim.start();
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            Log.e("spr_ypt", "draw: colorRate=" + colorRate);
            if (startColor != endColor) {
                LinearGradient mLg = new LinearGradient(0, 0, canvas.getWidth(), 0,
                        new int[]{startColor, endColor}, new float[]{1f - colorRate, 1f - colorRate - 0.01f}, Shader.TileMode.CLAMP);
                mPaint.setShader(mLg);
            } else {
                mPaint.setColor(startColor);
            }
            RectF rectF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            canvas.drawRoundRect(rectF, canvas.getHeight() / 2, canvas.getHeight() / 2, mPaint);
        }

        @Override
        public void setAlpha(int alpha) {
            mPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        public void recycle() {
            if (null != anim) {
                anim.removeAllUpdateListeners();
                anim.removeAllListeners();
                anim.cancel();
                anim = null;
            }
        }
    }
}
