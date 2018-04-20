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
import android.util.Log;

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

    public static Drawable getRightDrawable(float roundCorner, int color) {
        return new RoundRectDrawable(false, true, true, false, roundCorner, color);
    }


    public static Drawable getLeftDrawable(float roundCorner, int color) {
        return new RoundRectDrawable(true, false, false, true, roundCorner, color);
    }

    public static Drawable getRightCotDrawable(float roundCorner, float cot, int color) {
        return new RoundRectRightCotDrawable(roundCorner, cot, color);
    }

    public static Drawable getLeftCotDrawable(float roundCorner, float cot, int color) {
        return new RoundRectLeftCotDrawable(roundCorner, cot, color);
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

    private static class RoundRectRightCotDrawable extends Drawable {
        private Paint mPaint;

        private Path mPath;

        private float roundCorner;

        private float cot;//斜边内角cot值,正直：/，负值：\。

        private RectF roundRectF;

        private int color;

        public RoundRectRightCotDrawable(float roundCorner, float cot, int color) {
            this.roundCorner = roundCorner;
            this.cot = cot;
            this.color = color;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            if (mPath == null) {
                mPath = new Path();

                roundCorner = roundCorner > canvas.getHeight() / 2 ? canvas.getHeight() / 2 : roundCorner;

                float centerWidth = canvas.getWidth() - Math.abs(canvas.getHeight() * cot / 2);

                float[] q1 = new float[]{0, roundCorner};
                float[] q2 = new float[]{roundCorner, 0};
                float[] q3 = new float[]{centerWidth + canvas.getHeight() * cot / 2, 0};
                float[] q4 = new float[]{centerWidth - canvas.getHeight() * cot / 2, canvas.getHeight()};
                float[] q5 = new float[]{roundCorner, canvas.getHeight()};
                float[] q6 = new float[]{0, canvas.getHeight() - roundCorner};

                float[] cq1=new float[]{0,0};
                float[] cq2=new float[]{0,canvas.getHeight()};

                mPath.moveTo(q1[0], q1[1]);
                mPath.quadTo(cq1[0],cq1[1],q2[0], q2[1]);
                mPath.lineTo(q3[0], q3[1]);
                mPath.lineTo(q4[0], q4[1]);
                mPath.lineTo(q5[0], q5[1]);
                mPath.quadTo(cq2[0],cq2[1],q6[0], q6[1]);
                mPath.lineTo(q1[0], q1[1]);
                mPath.close();
            }

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

    private static class RoundRectLeftCotDrawable extends Drawable {
        private Paint mPaint;

        private Path mPath;

        private float roundCorner;

        private float cot;//斜边内角cot值,正直：/，负值：\。

        private RectF roundRectF;

        private int color;

        public RoundRectLeftCotDrawable(float roundCorner, float cot, int color) {
            this.roundCorner = roundCorner;
            this.cot = cot;
            this.color = color;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            if (mPath == null) {
                mPath = new Path();

                roundCorner = roundCorner > canvas.getHeight() / 2 ? canvas.getHeight() / 2 : roundCorner;

                float centerWidth = Math.abs(canvas.getHeight() * cot / 2);

                float[] q1 = new float[]{centerWidth + canvas.getHeight() * cot / 2, 0};
                float[] q2 = new float[]{canvas.getWidth() - roundCorner, 0};
                float[] q3 = new float[]{canvas.getWidth(), roundCorner};
                float[] q4 = new float[]{canvas.getWidth(), canvas.getHeight() - roundCorner};
                float[] q5 = new float[]{canvas.getWidth() - roundCorner, canvas.getHeight()};
                float[] q6 = new float[]{centerWidth - canvas.getHeight() * cot / 2, canvas.getHeight()};

                float[] cq1=new float[]{canvas.getWidth(),0};
                float[] cq2=new float[]{canvas.getWidth(),canvas.getHeight()};

                mPath.moveTo(q1[0], q1[1]);
                mPath.lineTo(q2[0], q2[1]);
                mPath.quadTo(cq1[0],cq1[1],q3[0], q3[1]);
                mPath.lineTo(q4[0], q4[1]);
                mPath.quadTo(cq2[0],cq2[1],q5[0], q5[1]);
                mPath.lineTo(q6[0], q6[1]);
                mPath.lineTo(q1[0], q1[1]);
                mPath.close();
            }

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
