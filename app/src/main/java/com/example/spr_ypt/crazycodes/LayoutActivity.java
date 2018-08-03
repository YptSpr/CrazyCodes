package com.example.spr_ypt.crazycodes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spr_ypt.crazycodes.pkArenaBox.CircleRotateProgressDrawable;
import com.example.spr_ypt.crazycodes.pkArenaBox.PkArenaBoxViewV2;
import com.example.spr_ypt.crazycodes.pkArenaBox.pkArenaBoxView;
import com.example.spr_ypt.crazycodes.pkpicker.PkArenaOpponentInfoView;

/**
 * Created by Spr_ypt on 2017/9/18.
 */

public class LayoutActivity extends Activity {


    private PkArenaOpponentInfoView mPaoivTest;
    private TextView mTvHtmlTest;
    private pkArenaBoxView mVBox;
    private ImageView mIvBuff;
    private CircleRotateProgressDrawable mRoatateDrawable;
    private PkArenaBoxViewV2 mVBoxV2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        initView();
        initEvent();
    }


    private void initView() {
        mPaoivTest = (PkArenaOpponentInfoView) findViewById(R.id.paoiv_test);
        mTvHtmlTest = (TextView) findViewById(R.id.tv_html_test);
        mTvHtmlTest.setText(Html.fromHtml("我是<font color='#e87400'>测试</font>的文案"));
        mVBox = (pkArenaBoxView) findViewById(R.id.v_box);
        mIvBuff = (ImageView) findViewById(R.id.iv_buff);
        mRoatateDrawable = new CircleRotateProgressDrawable();//4test
        mIvBuff.setImageDrawable(mRoatateDrawable);//4test
        mVBoxV2 = (PkArenaBoxViewV2) findViewById(R.id.v_box_v2);
    }

    private void initEvent() {
        mPaoivTest.setEventListener(new PkArenaOpponentInfoView.PkArenaOpponentInfoEventListener() {
            @Override
            public void onClickSlaveBtn() {
                Toast.makeText(getApplicationContext(), "onClickSlaveBtn", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onClick() {
                Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_SHORT).show();
                mVBoxV2.reset();
                mVBoxV2.setVisibility(View.GONE);
            }
        });
        mPaoivTest.show();

        mVBox.setOnClickListener(new View.OnClickListener() {
            private int index;

            @Override
            public void onClick(View v) {
                index = ++index % 3;
                switch (index) {
                    case 0:
                        mVBox.changeToSmall();
                        break;
                    case 1:
                        mVBox.changeToBig();
                        mVBox.setTextLine("联屏PK夺宝", "差<font color='#f8e71c'>10000</font>星光值开启宝箱");
                        break;
                    case 2:
                        mVBox.changeTextLine("宝箱已开启", "我是<font color='#e87400'>测试</font>的文案");
                        break;
                }
                mVBox.setProgressRate((float) Math.random());
                mRoatateDrawable.startCountDown(0, 3000);//4test

                mVBoxV2.setVisibility(View.VISIBLE);
                mVBoxV2.reset();
                mVBoxV2.setScaleX(0f);
                mVBoxV2.setScaleY(0f);
                mVBoxV2.animate().scaleX(1.0f).scaleY(1.0f).setInterpolator(new OvershootInterpolator()).setDuration(300).start();
                mVBoxV2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVBoxV2.changeToSmall();
                    }
                },3000);

            }
        });

        mVBoxV2.setOnClickListener(new View.OnClickListener() {
            private int index;

            @Override
            public void onClick(View v) {
                index = ++index % 2;
                switch (index) {
                    case 0:
                        mVBoxV2.reset();
                        break;
                    case 1:
                        mVBoxV2.changeToSmall();
                        break;
                }
            }
        });
    }


}
