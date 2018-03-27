package com.example.spr_ypt.crazycodes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {


    private ImageView mIvScrollIcon;
    private ImageView mIvScrollIconB;

    private ValueAnimator anim;//滚动动画

    private boolean reFlag;//记录fade动画alpha执行顺序

    private int lastRandom = -1;//记录上次的随机数（排重用）
    //滚动用的头像
    private int[] icons = {R.drawable.hani_pk_arena_popup_random_scroll_icon_0,
            R.drawable.hani_pk_arena_popup_random_scroll_icon_1,
            R.drawable.hani_pk_arena_popup_random_scroll_icon_2,
            R.drawable.hani_pk_arena_popup_random_scroll_icon_3,
            R.drawable.hani_pk_arena_popup_random_scroll_icon_4,};

    Drawable[] layers = new Drawable[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        startScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initView() {
        mIvScrollIcon = (ImageView) findViewById(R.id.iv_scroll_icon);
        mIvScrollIconB = (ImageView) findViewById(R.id.iv_scroll_icon_b);
    }

    private void startScroll() {
        if (null == anim) {
            anim = ValueAnimator.ofFloat(0f, 1f);
            anim.setDuration(2000);
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                    randomPlayerIcons();
                }
            });
        }
        anim.start();
    }


    private void randomPlayerIcons() {
        int random = getNextRandom(icons.length);

        setRandomPlayerIcon(random, mIvScrollIcon);

    }

    private int getNextRandom(int size) {
        int next = (int) (Math.random() * size);
        while (lastRandom == next) {
            next = (int) (Math.random() * size);
        }
        lastRandom = next;
        return next;
    }

    private void setRandomPlayerIcon(int random, ImageView iv) {

        if (null != layers[1]) {
            layers[0] = layers[1];
        } else {
            layers[0] = getResources().getDrawable(icons[0]);
        }
        layers[1] = getResources().getDrawable(icons[random]);
        TransitionDrawable transitionDrawable = new TransitionDrawable(layers);
        mIvScrollIcon.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(1000);
    }

    private void stopScroll() {
        if (null != anim && anim.isRunning()) anim.cancel();
        reFlag = false;
        mIvScrollIcon.setAlpha(1f);
        mIvScrollIconB.setAlpha(0f);
    }
}
