package com.example.spr_ypt.crazycodes.pkArenaBox;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.spr_ypt.crazycodes.R;

public class BoxRotateRayView extends FrameLayout implements Animatable {

    private ImageView mImageView;
    private RotateAnimation mAnimation;

    public BoxRotateRayView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BoxRotateRayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BoxRotateRayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BoxRotateRayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setClipChildren(false);

        mImageView = new ImageView(context);
        mImageView.setImageResource(R.drawable.hani_view_arena_box_pk_ray);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = Gravity.CENTER;
        addView(mImageView, params);
    }

    @Override
    public void start() {
        if (mAnimation == null) {
            mAnimation = new RotateAnimation(0, 180,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        }
        mAnimation.setDuration(3000);
        mAnimation.setFillAfter(true);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setInterpolator(new LinearInterpolator());
        mImageView.startAnimation(mAnimation);
    }

    @Override
    public void stop() {
        if (mAnimation != null) {
            mAnimation.cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return mAnimation != null && !mAnimation.hasEnded();
    }
}
