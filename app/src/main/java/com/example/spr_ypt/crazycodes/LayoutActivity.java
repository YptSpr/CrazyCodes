package com.example.spr_ypt.crazycodes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spr_ypt.crazycodes.pkArenaBox.pkArenaBoxView;
import com.example.spr_ypt.crazycodes.pkpicker.PkArenaOpponentInfoView;

/**
 * Created by Spr_ypt on 2017/9/18.
 */

public class LayoutActivity extends Activity {


    private PkArenaOpponentInfoView mPaoivTest;
    private TextView mTvHtmlTest;
    private pkArenaBoxView mVBox;

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
            }
        });
        mPaoivTest.show();

        mVBox.setOnClickListener(new View.OnClickListener() {
            private boolean isBig;

            @Override
            public void onClick(View v) {
                if (isBig) {
                    mVBox.changeToSmall();
                    isBig = false;
                } else {
                    mVBox.changeToBig();
                    isBig = true;
                }
                mVBox.setProgressRate((float) Math.random());
            }
        });
    }


}
