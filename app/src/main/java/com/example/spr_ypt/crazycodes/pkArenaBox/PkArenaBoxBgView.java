package com.example.spr_ypt.crazycodes.pkArenaBox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.spr_ypt.crazycodes.R;

public class PkArenaBoxBgView extends View {
    private float index = 1.0f;//0~1,0 为小形态，1为大形态
    private Paint mPaint;

    public PkArenaBoxBgView(Context context) {
        super(context);
    }

    public PkArenaBoxBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PkArenaBoxBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PkArenaBoxBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setIndex(float index) {
        this.index = index;
        if (null != getLayoutParams()) {
            int width = (int) (getResources().getDimension(R.dimen.demin_140dp) + index * getResources().getDimension(R.dimen.demin_235dp));
            int height = (int) (getResources().getDimension(R.dimen.demin_20dp) + index * getResources().getDimension(R.dimen.demin_260dp));
            getLayoutParams().width = width;
            getLayoutParams().height = height;
            if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) getLayoutParams()).setMargins(0, 0, 0, (int) ((1 - index) * getResources().getDimension(R.dimen.demin_24dp)));
            }
            invalidate();
        }
//        requestLayout();//需要父布局调用requestLayout来改变整体布局

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mPaint) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.parseColor("#000000"));
        }
        mPaint.setAlpha((int) (255*(0.5+0.3*(1-index))));
        RectF rectF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        float r = (1 - index) * getResources().getDimension(R.dimen.demin_20dp) / 2;
        canvas.drawRoundRect(rectF, r, r, mPaint);

    }
}
