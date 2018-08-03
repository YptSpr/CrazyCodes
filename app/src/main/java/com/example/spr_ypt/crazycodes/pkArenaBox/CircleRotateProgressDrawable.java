package com.example.spr_ypt.crazycodes.pkArenaBox;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CircleRotateProgressDrawable extends Drawable {

    private Paint mPaint;

    private int mPaintColor = Color.parseColor("#99000000");

    @IntRange(from = 0, to = 360)
    private int rad = 90;

    private ValueAnimator mAnim;

    public CircleRotateProgressDrawable() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mPaintColor);
    }

    public void startCountDown(int startRad,int ms){
        if (null!=mAnim&&mAnim.isRunning()) mAnim.cancel();
        mAnim= ObjectAnimator.ofInt(startRad,360);
        mAnim.setDuration(ms);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rad= (int) animation.getAnimatedValue();
                invalidateSelf();
            }
        });
        mAnim.start();
    }

    public void startCountDown(int ms){
        startCountDown(rad,ms);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        RectF rectF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawArc(rectF, -90, rad - 360, true, mPaint);
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
}
