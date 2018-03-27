package com.example.spr_ypt.crazycodes;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.spr_ypt.crazycodes.voice.NewVoiceRippleView;

import java.lang.ref.WeakReference;

/**
 * Created by Spr_ypt on 2017/9/20.
 */

public class PercentActivity extends Activity {


    private NewVoiceRippleView mRvMy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);
        initView();
    }

    private void initView() {
        mRvMy = (NewVoiceRippleView) findViewById(R.id.rv_my);
    }

    private static class MyHandler extends Handler{
        private WeakReference<PercentActivity> ref;

        private static final int REFRESH_VOICE=0x00000001;

        public MyHandler(PercentActivity activity) {
            ref=new WeakReference<PercentActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
