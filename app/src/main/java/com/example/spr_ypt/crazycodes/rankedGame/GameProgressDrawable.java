package com.example.spr_ypt.crazycodes.rankedGame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.OvershootInterpolator;

/**
 * 进度条drawable
 * 此drawable使用了xfermode实现，外部imageView必须关闭硬件加速，否则会有奇怪的问题
 */

public class GameProgressDrawable extends Drawable {

    private Paint mBgPaint;
    private Paint mProgressPaint;
    private float mlastRate;//上次动画最终位置
    private float rate;//需要到达的位置
    private float progress;//绘制进度
    private ValueAnimator mAnim;//动画触发器
    private boolean isAnimCancel;
    private int mDuration = 500;

    private int mBgColor;
    private int[] mColors;


    public GameProgressDrawable(float rate, int bgColor, int... colors) {
        this.rate = rate;
        mlastRate = rate;
        progress = rate;
        mBgColor = bgColor;
        mColors = colors;
        initAnim();
    }

    private void initAnim() {
        mAnim = ObjectAnimator.ofFloat(0, 1f);
        mAnim.setDuration(mDuration);
        mAnim.setInterpolator(new OvershootInterpolator());
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue() * (rate - mlastRate) + mlastRate;
                invalidateSelf();
            }
        });
        mAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                //动画中途停止记录进度
                isAnimCancel = true;
                mlastRate = progress;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //正常停止动画记录进度，如果中断过则只记录中断的进度
                if (!isAnimCancel) {
                    mlastRate = rate;
                }
                isAnimCancel = false;
            }


        });
    }

    private void initProgressPaint(int w,int h) {
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        if (mColors.length <= 0) {
            return;
        } else if (mColors.length == 1) {
            mProgressPaint.setColor(mColors[0]);
        } else {
            float[] positions = new float[mColors.length];
            for (int i = 0; i < positions.length; i++) {
                positions[i] = (float) i / (float) mColors.length;
            }
            LinearGradient lg = new LinearGradient(0f, (float) h / 2,
                    (float) w / 2, (float) h/ 2,
                    mColors, positions, Shader.TileMode.MIRROR);
            mProgressPaint.setShader(lg);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        float round=canvas.getHeight()/2;//圆角参数

        if (null == mBgPaint) {
            mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBgPaint.setColor(mBgColor);
        }
        RectF rectFbg = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawRoundRect(rectFbg,round,round, mBgPaint);

        if (null == mProgressPaint) initProgressPaint(canvas.getWidth(),canvas.getHeight());

        float progressWidth = canvas.getWidth() * progress;
        RectF rectF;
        if (progressWidth >= canvas.getHeight()) {
            rectF = new RectF(0, 0, progressWidth, canvas.getHeight());
        } else {
            rectF = new RectF(progressWidth - canvas.getHeight(), 0, progressWidth, canvas.getHeight());
        }
        canvas.drawRoundRect(rectF,round,round, mProgressPaint);


    }


    /**
     * 设置进度比例
     *
     * @param rate
     */
    public void setRate(@FloatRange(from = 0.0f, to = 1.0f) float rate) {
        this.rate = rate;
        if (mAnim.isRunning()) {
            mAnim.cancel();
        }
        mAnim.start();

    }

    public void recycle() {
        if (null!=mAnim&&mAnim.isRunning()){
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
}
