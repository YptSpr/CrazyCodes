package com.example.spr_ypt.crazycodes.pkArenaBox;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.OvershootInterpolator;

public class CircleVerticalProgressDrawable extends Drawable {

    private Paint mPaint;
    private int startColor = Color.TRANSPARENT;
    private int endColor = Color.parseColor("#99000000");
    private ValueAnimator anim;
    private float colorRate;
    private float mLastRate;

    public CircleVerticalProgressDrawable(int startColor, int endColor) {
        this();
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public CircleVerticalProgressDrawable() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setRate(@FloatRange(from = 0.0, to = 1.0) float rate) {
        if (null != anim && anim.isRunning()) anim.cancel();
        anim = ObjectAnimator.ofFloat(mLastRate, rate);
        anim.setDuration(200);
        anim.setInterpolator(new OvershootInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                colorRate = (float) animation.getAnimatedValue();
                mLastRate = colorRate;
                invalidateSelf();
            }
        });
        anim.start();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mPaint.reset();
        if (startColor != endColor) {
            LinearGradient mLg = new LinearGradient(0, 0, 0, canvas.getHeight(),
                    new int[]{startColor, endColor}, new float[]{1f - colorRate, 1f - colorRate - 0.01f}, Shader.TileMode.CLAMP);
            mPaint.setShader(mLg);
        } else {
            mPaint.setColor(startColor);
        }
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, Math.min(canvas.getWidth() / 2, canvas.getHeight() / 2), mPaint);
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
        if (null != anim && anim.isRunning()) anim.cancel();
    }
}
