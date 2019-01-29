package com.example.spr_ypt.crazycodes;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.example.spr_ypt.crazycodes.horizontaltagprogress.HorizontalTagProgressDrawable;
import com.example.spr_ypt.crazycodes.horizontaltagprogress.HorizontalTagProgressView;

import java.util.ArrayList;
import java.util.List;

public class ProgressViewTestActivity extends Activity {

    private ImageView mProgressView;

    private HorizontalTagProgressDrawable progressDrawable;

    private ValueAnimator animator;

    private HorizontalTagProgressView tagProgressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view);
        mProgressView=findViewById(R.id.iv_progress);
        tagProgressView=findViewById(R.id.sv_progress);

        progressDrawable=new HorizontalTagProgressDrawable();

        mProgressView.setImageDrawable(progressDrawable);

        animator= ObjectAnimator.ofFloat(0f,1f);
        animator.setDuration(5000);
//        animator.setInterpolator(new AccelerateInterpolator(10));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressDrawable.setRate((Float) animation.getAnimatedValue());
                tagProgressView.setProgress((long) (2000000000000L*(float)animation.getAnimatedValue()));
            }
        });

        mProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.start();
//                tagProgressView.setProgress(1000000000000L);
            }
        });

        List<HorizontalTagProgressView.TagEntity> tags=createTestTagList();
        tagProgressView.initTags(tags);

    }

    private List<HorizontalTagProgressView.TagEntity> createTestTagList() {
        List<HorizontalTagProgressView.TagEntity> testList=new ArrayList<>();

        testList.add(new HorizontalTagProgressView.TagEntity(1,"跑车",100));
        testList.add(new HorizontalTagProgressView.TagEntity(2,"车队",1000));
        testList.add(new HorizontalTagProgressView.TagEntity(3,"游艇",10000));
        testList.add(new HorizontalTagProgressView.TagEntity(4,"游艇队",100000));
        testList.add(new HorizontalTagProgressView.TagEntity(5,"火箭",1000000));
        testList.add(new HorizontalTagProgressView.TagEntity(6,"火箭队",10000000));
        testList.add(new HorizontalTagProgressView.TagEntity(7,"UFO",100000000));
        testList.add(new HorizontalTagProgressView.TagEntity(8,"UFO群",1000000000));
        testList.add(new HorizontalTagProgressView.TagEntity(9,"彗星", 10000000000L));
        testList.add(new HorizontalTagProgressView.TagEntity(10,"彗星群", 100000000000L));
        testList.add(new HorizontalTagProgressView.TagEntity(11,"宇宙", 1000000000000L));


        return testList;
    }
}
