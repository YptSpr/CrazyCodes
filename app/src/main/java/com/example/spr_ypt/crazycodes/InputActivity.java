package com.example.spr_ypt.crazycodes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.softInput.InputManager;
import com.example.spr_ypt.crazycodes.softInput.KpSwitchManager;

@SuppressLint("LogUse")
public class InputActivity extends Activity {

    private InputManager inputManager;
    private TextView mEtInput;
    private FrameLayout mFlInput;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        mEtInput = (TextView) findViewById(R.id.et_input);
        mFlInput = (FrameLayout) findViewById(R.id.fl_input);

        inputManager = new InputManager(this);

        mEtInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputManager.showSpeak(getWindow().getDecorView());
            }
        });

        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);

    }

    ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            int different = InputManager.getScreenSize(getApplicationContext())[1] - r.bottom + r.top;
//            Log.e("spr_ypt=>inputPop", "onGlobalLayout: different=" + different);
        }
    };


}
