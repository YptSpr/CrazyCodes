package com.example.spr_ypt.crazycodes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.spr_ypt.crazycodes.rankedGame.GlobalData;
import com.example.spr_ypt.crazycodes.rankedGame.RankedGameMatchingView;
import com.example.spr_ypt.crazycodes.rankedGame.RankedGameScoreViewV2;

public class RankedGameActivity extends Activity {

    private RankedGameScoreViewV2 mVScore;
    private Button mBtnTest;
    private RankedGameMatchingView mVMatching;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        GlobalData.build(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankedgame);
        initView();
        initEvent();
    }

    int i;


    private void initEvent() {
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = (int) Math.floor(Math.random() * 9);
//                i = 1;
                mBtnTest.setText("当前Container【"+i+"】");
                mVScore.changeContainerState(i);
                Log.e("spr_ypt=>Rank", "onClick: boolean="+("".equals(null)));
                i = ++i % 2;

                mVMatching.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView() {
        mVScore = (RankedGameScoreViewV2) findViewById(R.id.v_score);
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mVMatching = (RankedGameMatchingView) findViewById(R.id.v_matching);
    }
}
