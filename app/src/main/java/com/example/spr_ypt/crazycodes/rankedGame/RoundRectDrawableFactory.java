package com.example.spr_ypt.crazycodes.rankedGame;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RoundRectDrawableFactory {

    public static Drawable getDrawable(float roundCorner, int color) {
        return new RoundRectDrawable(true, true, true, true, roundCorner, color);
    }

    public static Drawable getLeftBottomDrawable(float roundCorner, int color) {
        return new RoundRectDrawable(false, false, false, true, roundCorner, color);
    }

    public static Drawable getRightBottomDrawable(float roundCorner, int color) {
        return new RoundRectDrawable(false, false, true, false, roundCorner, color);
    }

    private static class RoundRectDrawable extends Drawable {

        private Paint mPaint;

        private Path mPath;

        private boolean leftTop;

        private boolean rightTop;

        private boolean rightBottom;

        private boolean leftBottom;

        private float roundCorner;

        private RectF roundRectF;

        private int color;

        public RoundRectDrawable(boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom, float roundCorner, int color) {
            this.leftTop = leftTop;
            this.rightTop = rightTop;
            this.rightBottom = rightBottom;
            this.leftBottom = leftBottom;
            this.roundCorner = roundCorner;
            this.color = color;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            if (mPath == null) {
                mPath = new Path();
            }
            if (roundRectF == null) {
                roundRectF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            } else {
                roundRectF.set(0, 0, canvas.getWidth(), canvas.getHeight());
            }
            mPath.reset();
            float[] radii = new float[]{leftTop ? roundCorner : 0, leftTop ? roundCorner : 0,
                    rightTop ? roundCorner : 0, rightTop ? roundCorner : 0,
                    rightBottom ? roundCorner : 0, rightBottom ? roundCorner : 0,
                    leftBottom ? roundCorner : 0, leftBottom ? roundCorner : 0};
            mPath.addRoundRect(roundRectF, radii, Path.Direction.CW);
            if (mPaint == null) {
                mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                mPaint.setColor(color);
            }
            canvas.drawPath(mPath, mPaint);
        }

        @Override
        public void setAlpha(int alpha) {
            if (null != mPaint) mPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
            if (null != mPaint) mPaint.setColor(color);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
