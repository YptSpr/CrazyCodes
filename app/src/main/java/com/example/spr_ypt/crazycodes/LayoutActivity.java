package com.example.spr_ypt.crazycodes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.spr_ypt.crazycodes.pkpicker.PkArenaOpponentInfoView;

/**
 * Created by Spr_ypt on 2017/9/18.
 */

public class LayoutActivity extends Activity {


    private PkArenaOpponentInfoView mPaoivTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        initView();
        initEvent();
    }

    private void initView() {
        mPaoivTest = (PkArenaOpponentInfoView) findViewById(R.id.paoiv_test);
    }

    private void initEvent() {
        mPaoivTest.setEventListener(new PkArenaOpponentInfoView.PkArenaOpponentInfoEventListener() {
            @Override
            public void onClickSlaveBtn() {
                Toast.makeText(getApplicationContext(),"onClickSlaveBtn",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onClick() {
                Toast.makeText(getApplicationContext(),"onClick",Toast.LENGTH_SHORT).show();
            }
        });
        mPaoivTest.show();
    }


}
