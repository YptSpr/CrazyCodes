package com.example.spr_ypt.crazycodes.horizontaltagprogress;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class HorizontalTagProgressDrawable extends Drawable {

    private Paint mPaint;

    private Paint mProgressPaint;

    private int mBgColor = Color.parseColor("#19ffffff");

    private int mStarColor = Color.parseColor("#ff3058");

    private int mEndColor = Color.parseColor("#ff0033");

    private int mTagColor = Color.parseColor("#99ffffff");

    private int mZebraColor = Color.parseColor("#33ffffff");

    private float mTagWithRate = 0.33f;//与进度条高度比

    private float mZebraWithRate = 0.33f;//与进度条高度比

    private int mZebraSpaceRate = 3;//与进度条高度比

    private List<Float> mTags;

    private RectF mRectF;

    private float mRate = 0.0f;

    public HorizontalTagProgressDrawable() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTags = Arrays.asList(0.1f, 0.5f, 0.7f);
    }

    public void setRate(@FloatRange(from = 0f, to = 1f) float rate) {
        this.mRate = rate;
        invalidateSelf();
    }

    public void resetTags(List<Float> tags) {
        this.mTags = tags;
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (null == mRectF) {
            mRectF = new RectF();
        }
        drawBg(canvas);

        drawProgress(canvas);

        drawTags(canvas);

    }

    /**
     * 绘制目标分割线
     *
     * @param canvas
     */
    private void drawTags(@NonNull Canvas canvas) {
        if (null != mTags && mTags.size() > 0) {
            resetPaint(mTagColor, getBounds().height() * mTagWithRate);
            for (int i = 0; i < mTags.size(); i++) {
                canvas.drawLine(getBounds().width() * mTags.get(i), 0, getBounds().width() * mTags.get(i), getBounds().height(), mPaint);
            }
        }
    }

    /**
     * 绘制进度条
     *
     * @param canvas
     */
    private void drawProgress(@NonNull Canvas canvas) {
        if (mRate > 0) {
            mRectF.set(0f, 0f, getBounds().width() * mRate, getBounds().height());
            createProgressPaint();
            canvas.drawRoundRect(mRectF, (float) getBounds().height() / 2, (float) getBounds().height() / 2, mProgressPaint);

            drawZebra(canvas);
        }
    }

    /**
     * 绘制进度条上的斑马线
     *
     * @param canvas
     */
    private void drawZebra(@NonNull Canvas canvas) {
        //实际上要绘制斑马线的宽度=进度条长度-2边圆角长度（等于0.5+0.5个进度条宽度）
        float zebraProgressWidth = getBounds().width() * mRate - getBounds().height();
        //斑马线数量 =实际上要绘制斑马线的宽度/斑马线间隔+宽度
        int zebraCount = (int) (zebraProgressWidth / (getBounds().height() * mZebraSpaceRate));
        if (zebraProgressWidth % (getBounds().height() * mZebraSpaceRate) > getBounds().height()) {
            //如果剩余的距离还能画一个斑马线的话+1
            zebraCount = zebraCount + 1;
        }
        resetPaint(mZebraColor, getBounds().height() * mZebraWithRate);
        for (int i = 0; i < zebraCount; i++) {
            canvas.drawLine(getBounds().height() * (0.5f + mZebraSpaceRate * (i)), 0, getBounds().height() * (0.5f + mZebraSpaceRate * (i)) + getBounds().height(), getBounds().height(), mPaint);
        }
    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBg(@NonNull Canvas canvas) {
        mRectF.set(0f, 0f, getBounds().width(), getBounds().height());
        resetPaint(mBgColor, 0);
        canvas.drawRoundRect(mRectF, (float) getBounds().height() / 2, (float) getBounds().height() / 2, mPaint);
    }

    /**
     * 创建进度条画笔
     */
    private void createProgressPaint() {
        if (null == mProgressPaint) {
            LinearGradient linearGradient = new LinearGradient(0f, 0f, getBounds().width(), getBounds().height(), mStarColor, mEndColor, Shader.TileMode.CLAMP);
            mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mProgressPaint.setShader(linearGradient);
        }
    }

    /**
     * 重置通用画笔，需要改变的元素统一此处管理，避免忘记重设导致绘制异常
     *
     * @param color
     * @param strokeWidth
     */
    private void resetPaint(int color, float strokeWidth) {
        mPaint.setColor(color);
        mPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    public void setAlpha(int alpha) {
        if (null != mPaint) {
            mPaint.setAlpha(alpha);
        }

        if (null != mProgressPaint) {
            mProgressPaint.setAlpha(alpha);
        }

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (null != mPaint) {
            mPaint.setColorFilter(colorFilter);
        }
        if (null != mProgressPaint) {
            mProgressPaint.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
