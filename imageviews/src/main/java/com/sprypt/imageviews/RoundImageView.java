package com.sprypt.imageviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.sprypt.imageviews.utils.RoundUtil;

/**
 * Created by Spr_ypt on 2017/9/11.
 */

public class RoundImageView extends AppCompatImageView {


    private float roundCorner = 0.0f;

    private RoundUtil roundUtil;

    public RoundImageView(Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        roundUtil = new RoundUtil(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (roundCorner > 0) {
            roundUtil.makeRoundCorner(canvas, roundCorner);
        }
    }

    public void setRoundCorner(float roundCorner) {
        this.roundCorner = roundCorner;
    }


}
