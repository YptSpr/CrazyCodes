package com.sprypt.imageviews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.airbnb.lottie.LottieAnimationView;
import com.sprypt.imageviews.utils.RoundUtil;

/**
 * Created by Spr_ypt on 2017/9/11.
 */

public class RoundLottieAnimView extends LottieAnimationView {
    private float roundCorner = 0.0f;

    private RoundUtil roundUtil;

    public RoundLottieAnimView(Context context) {
        this(context, null);
    }

    public RoundLottieAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundLottieAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
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
