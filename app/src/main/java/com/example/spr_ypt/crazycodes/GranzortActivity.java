package com.example.spr_ypt.crazycodes;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GranzortActivity extends AppCompatActivity {
    private GranzortView mVGranzort;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_granzort);
        initView();
        initEvent();
    }

    private void initEvent() {
        mVGranzort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVGranzort.restartAnim();

                ValueAnimator animator1 = ObjectAnimator.ofFloat(mVGranzort, "scaleX", 0.5f, 1f);
                ValueAnimator animator2 = ObjectAnimator.ofFloat(mVGranzort, mVGranzort.SCALE_Y, 0.5f, 1f);

                AnimatorSet set = new AnimatorSet();
                set.playTogether(animator1, animator2);
                set.start();
            }
        });



    }

    private void initView() {
        mVGranzort = (GranzortView) findViewById(R.id.v_granzort);
    }
}
