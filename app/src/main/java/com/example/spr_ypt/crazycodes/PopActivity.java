package com.example.spr_ypt.crazycodes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.spr_ypt.crazycodes.pkArenaPops.PkArenaRandomPopupWindow;

/**
 * Created by Spr_ypt on 2017/10/11.
 */

public class PopActivity extends Activity implements View.OnClickListener {
    private Button mBtnShow;
    private Button mBtnMatch;
    private PkArenaRandomPopupWindow mPop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        initView();
        initEvent();
    }

    private void initEvent() {
        mBtnShow.setOnClickListener(this);
        mBtnMatch.setOnClickListener(this);
    }

    private void initView() {
        mBtnShow = (Button) findViewById(R.id.btn_show);
        mBtnMatch = (Button) findViewById(R.id.btn_match);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show:
                if (null == mPop) {
                    createPop();
                }

                if (mPop.isShowing()) {
                    mPop.dismiss();
                } else {
                    mPop.show(getWindow().getDecorView());
                }


                break;
            case R.id.btn_match:
                if (null != mPop) mPop.matchSuccess();
                break;
        }
    }

    private void createPop() {
        mPop = new PkArenaRandomPopupWindow(this);
        mPop.setPkArenaRandomPopListener(new PkArenaRandomPopupWindow.PkArenaRandomPopListener() {
            @Override
            public void onMatchStart() {
                Toast.makeText(getApplicationContext(),"onMatchStart",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFightStart() {
                Toast.makeText(getApplicationContext(),"onFightStart",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"onCancel",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMatchTimeOut() {
                Toast.makeText(getApplicationContext(),"onMatchTimeOut",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
