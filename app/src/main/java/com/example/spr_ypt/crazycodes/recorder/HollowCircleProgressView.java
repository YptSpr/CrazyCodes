package com.example.spr_ypt.crazycodes.recorder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.spr_ypt.crazycodes.R;


public class HollowCircleProgressView extends View {
    private int progressColor = Color.parseColor("#ff2d55");
    private float progressWidth = 25;
    private int limitColor = Color.parseColor("#55ff2d55");

    private Paint mPaint = new Paint();
    private Paint mLimitPaint = new Paint();

    int sweep;//0-360一圈
    int limitSweep;//0-360一圈

    public HollowCircleProgressView(Context context) {
        super(context);
        init(context, null);
    }

    public HollowCircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HollowCircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HollowCircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.hollowCircleProgressView);
            if (typedArray != null) {
                try {
                    progressColor = typedArray.getColor(R.styleable.hollowCircleProgressView_hollowcircle_progress_color, progressColor);
                    progressWidth = typedArray.getDimension(R.styleable.hollowCircleProgressView_hollowcircle_progress_width, progressWidth);
                    limitColor = typedArray.getColor(R.styleable.hollowCircleProgressView_limit_color, limitColor);
                } catch (Exception ex) {
                } finally {
                    typedArray.recycle();
                }
            }
        }
        mPaint.setAntiAlias(true);
        mPaint.setColor(progressColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(progressWidth);
        mLimitPaint.setAntiAlias(true);
        mLimitPaint.setColor(limitColor);
        mLimitPaint.setStyle(Paint.Style.STROKE);
        mLimitPaint.setStrokeWidth(progressWidth);
    }

    RectF rectF = new RectF();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF.left = progressWidth / 2;
        rectF.top = progressWidth / 2;
        rectF.right = getWidth() - progressWidth / 2;
        rectF.bottom = getHeight() - progressWidth / 2;
        if (limitSweep > sweep) {
            canvas.drawArc(rectF, -90, limitSweep, false, mLimitPaint);
        }
        canvas.drawArc(rectF, -90, sweep, false, mPaint);
    }

    public void setProgress(int sweep) {
        this.sweep = sweep;
        invalidate();
    }

    public void setLimitSweep(int limitSweep) {
        this.limitSweep = limitSweep;
        invalidate();
    }

    public void resetProgress() {
        this.sweep = 0;
        invalidate();
    }

    public int getSweep() {
        return sweep;
    }

    public int getLimitSweep() {
        return limitSweep;
    }
}
