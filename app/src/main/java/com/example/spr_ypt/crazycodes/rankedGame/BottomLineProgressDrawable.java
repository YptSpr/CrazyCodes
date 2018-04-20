package com.example.spr_ypt.crazycodes.rankedGame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class BottomLineProgressDrawable extends Drawable {

    private Paint mBgPaint;
    private Paint mProgressPaint;
    private Paint mClearPaint;
    private Path mClearPath;
    private float progress;//绘制进度
    private ValueAnimator mAnim;//动画触发器
    private ValueAnimator mStartAnim;//启动动画触发器

    private int mBgColor;
    private int mLineColor;

    private float round;

    private float bottomLineHeigtRate;//底部线条高度占比

    private float startRate=1f;

    public BottomLineProgressDrawable(int mBgColor, int mLineColor, float round, float bottomLineHeigtRate) {
        this.mBgColor = mBgColor;
        this.mLineColor = mLineColor;
        this.round = round;
        this.bottomLineHeigtRate = bottomLineHeigtRate;
        initAnim();
    }

    private void initAnim() {
        mAnim = ObjectAnimator.ofFloat(1f, 0f);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = startRate*(float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });

        mStartAnim = ObjectAnimator.ofFloat(0f, 1f);
        mStartAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = startRate*(float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });

    }

    public void startAnim(float startRate,final long duration) {
        this.startRate=startRate;
        if (mAnim.isRunning()) {
            mAnim.removeAllListeners();
            mAnim.cancel();
        }
        if (mStartAnim.isRunning()){
            mStartAnim.cancel();
        }
        mStartAnim.removeAllListeners();
        mStartAnim.setDuration(500);
        mStartAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnim.removeAllListeners();
                mAnim.setDuration(duration-500);
                if (null != mAnimListener) mAnim.addListener(mAnimListener);
                mAnim.start();
            }
        });
        mStartAnim.start();

    }

    private Animator.AnimatorListener mAnimListener;

    public void setAnimListener(Animator.AnimatorListener listener) {
        mAnimListener = listener;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //绘制背景
        if (null == mBgPaint) {
            mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBgPaint.setColor(mBgColor);
        }
        RectF rectFbg = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawRoundRect(rectFbg, round, round, mBgPaint);
        //绘制底部进度条
        if (null == mProgressPaint) {
            mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mProgressPaint.setColor(mLineColor);
        }
        float progressWidth = canvas.getWidth() * progress;
        RectF rectF = new RectF(0, canvas.getHeight() * (1 - bottomLineHeigtRate), progressWidth, canvas.getHeight());
        canvas.drawRect(rectF, mProgressPaint);

        if (null == mClearPaint) {
            mClearPaint = createPorterDuffClearPaint();
        }
        if (null == mClearPath) {
            mClearPath = new Path();
        }
        mClearPath.reset();
        mClearPath.setFillType(Path.FillType.INVERSE_WINDING);
        mClearPath.addRoundRect(rectFbg, round, round, Path.Direction.CW);

        canvas.drawPath(mClearPath, mClearPaint);

    }

    private Paint createPorterDuffClearPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        return paint;
    }

    public void recycle() {
        if (null != mAnim && mAnim.isRunning()) {
            mAnim.removeAllUpdateListeners();
            mAnim.removeAllListeners();
            mAnim.cancel();
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mBgPaint.setAlpha(alpha);
        mProgressPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mBgPaint.setColorFilter(colorFilter);
        mProgressPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public boolean isOnAim() {
        return null!=mAnim&&mAnim.isRunning();
    }
}
