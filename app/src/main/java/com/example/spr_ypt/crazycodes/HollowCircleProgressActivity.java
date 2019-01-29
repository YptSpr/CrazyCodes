package com.example.spr_ypt.crazycodes;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.spr_ypt.crazycodes.recorder.HollowCircleProgressView;

public class HollowCircleProgressActivity extends AppCompatActivity {

    private HollowCircleProgressView mVHollow;
    private ValueAnimator anim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hollow);

        initView();
        initEvent();
        initAnim();
    }

    private void initAnim() {
        anim = ObjectAnimator.ofInt(0, 360);
        anim.setDuration(2000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mVHollow.setProgress((Integer) animation.getAnimatedValue());
            }
        });
    }

    private void initEvent() {
        mVHollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (anim.isRunning()) anim.cancel();
                anim.start();

                String str = "[/v587][/1235]";

                str = str.replaceAll("\\[/v587\\]", "v587");

                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();


            }
        });
    }

    private void initView() {
        mVHollow = (HollowCircleProgressView) findViewById(R.id.v_hollow);
    }
}
