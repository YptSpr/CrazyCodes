package com.example.spr_ypt.crazycodes.relay;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * PK 星光对抗的 计分板背景
 *
 * @author chengwangyong
 * @date 2018/1/13
 */
public class PkRelayProgressDrawable extends Drawable {
    private Paint leftPaint;
    private Paint rightPaint;
    private Path path;
    private LinearGradient leftLg;
    private LinearGradient rightLg;
    private int otherColor;

    public PkRelayProgressDrawable() {
        initPath();
    }

    private void initPath() {
        path = new Path();
        otherColor = Color.parseColor("#19ffffff");

    }

    private void initPaints(Canvas canvas) {
        leftPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        leftLg = new LinearGradient(0, canvas.getHeight() / 2,
                canvas.getWidth() / 2, canvas.getHeight() / 2,
                Color.parseColor("#ff2d55"), Color.parseColor("#ff7c3c"), Shader.TileMode.MIRROR);
        rightLg = new LinearGradient(canvas.getWidth(), canvas.getHeight() / 2,
                canvas.getWidth() / 2, canvas.getHeight() / 2,
                Color.parseColor("#408aed"), Color.parseColor("#00c4ff"), Shader.TileMode.MIRROR);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (null == leftPaint || null == rightPaint) {
            initPaints(canvas);
        }
        if (isOurRound) {
            drawOurRound();
        } else {
            drawOtherRound();
        }
        int index = canvas.getWidth() / 2;
        float round = canvas.getHeight() / 2;
        float[] leftRound = {round, round, 0f, 0f, 0f, 0f, round, round};
        float[] rightRound = {0f, 0f, round, round, round, round, 0f, 0f};
        RectF leftRectF = new RectF(0, 0, index, canvas.getHeight());
        RectF rightRectF = new RectF(index, 0, canvas.getWidth(), canvas.getHeight());
        path.reset();
        path.addRoundRect(leftRectF, leftRound, Path.Direction.CW);
        canvas.drawPath(path, leftPaint);
        path.reset();
        path.addRoundRect(rightRectF, rightRound, Path.Direction.CW);
        canvas.drawPath(path, rightPaint);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        this.leftPaint.setAlpha(alpha);
        this.rightPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.leftPaint.setColorFilter(colorFilter);
        this.rightPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private boolean isOurRound;

    public void setOurRound() {
        isOurRound = true;
        invalidateSelf();
    }

    private void drawOurRound() {
        // 别人的变成10%的白色 自己的变为正常颜色
        leftPaint.reset();
        leftPaint.setShader(leftLg);
        rightPaint.reset();
        rightPaint.setColor(otherColor);
    }

    public void setOtherRound() {
        isOurRound = false;
        // 自己的变成10%的白色 别人的变为正常颜色
        invalidateSelf();
    }

    public void drawOtherRound() {
        rightPaint.reset();
        rightPaint.setShader(rightLg);
        leftPaint.reset();
        leftPaint.setColor(otherColor);
    }
}
