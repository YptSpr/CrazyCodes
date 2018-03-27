package com.example.spr_ypt.crazycodes;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HandlerTestActivity extends AppCompatActivity {

    private Button mBtnTest;

    private LooperThread myThread;

    private Handler myHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        initView();
        initEvent();

        myThread = new LooperThread();

        myThread.start();

        myHandler = new Handler(myThread.getMyLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "Thread启动前，我收到一条Handler消息~", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Thread启动后，我收到一条Handler消息~", Toast.LENGTH_SHORT).show();
                        Log.e("4test", "handleMessage: Thread-");
                        break;
                }
            }
        };
    }

    private void initEvent() {
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHandler.sendEmptyMessage(1);
                Log.e("4test", "thread-"+android.os.Process.myTid());
                Log.e("4test", "myThread-"+myThread.getId());
            }
        });
    }

    private void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
    }


    private static class LooperThread extends Thread {

        @Override
        public void run() {
            Looper.prepare();
            Looper.loop();
        }

        public Looper getMyLooper() {
            return Looper.myLooper();
        }
    }

}
