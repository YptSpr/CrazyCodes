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
        myThread.setName("myThread");

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
                        Log.e("4test", "handleMessage: Thread-" + Thread.currentThread().getName());
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
                Log.e("4test", "thread-" + Thread.currentThread().getName());
                Log.e("4test", "myThread-" + myThread.getName());
            }
        });
    }

    private void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
    }


    /**
     * 实验用的类，加锁位置会卡主线程，参考{@link android.os.HandlerThread}
     */
    private static class LooperThread extends Thread {

        private Looper mLooper;

        @Override
        public void run() {
            Looper.prepare();
            synchronized (LooperThread.this) {
                if (null == mLooper) mLooper = Looper.myLooper();
                LooperThread.this.notify();
            }


            Looper.loop();
        }

        public Looper getMyLooper() {
            synchronized (LooperThread.this) {
                if (mLooper == null) {
                    try {
                        LooperThread.this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            return mLooper;
        }
    }

}
