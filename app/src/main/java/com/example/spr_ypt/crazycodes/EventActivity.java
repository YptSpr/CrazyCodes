package com.example.spr_ypt.crazycodes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.spr_ypt.crazycodes.eventCenter.dispatcher.NotifyDispatcher;
import com.example.spr_ypt.crazycodes.eventCenter.event.TestEvent;
import com.example.spr_ypt.crazycodes.eventCenter.subscriber.MainSubscriber;
import com.example.spr_ypt.crazycodes.pkpicker.PkArenaOpponentGiftView;
import com.example.spr_ypt.crazycodes.pkpicker.PkArenaOpponentInfoView;
import com.example.spr_ypt.crazycodes.pkpicker.PkArenaTimerWindowView;
import com.example.spr_ypt.crazycodes.pkpicker.PkScoreBoardView;
import com.example.spr_ypt.crazycodes.rankedGame.BottomLineProgressDrawable;
import com.example.spr_ypt.crazycodes.rankedGame.GameProgressDrawable;
import com.example.spr_ypt.crazycodes.rankedGame.RoundRectDrawableFactory;
import com.example.spr_ypt.crazycodes.relay.PkRelayProgressDrawable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Spr_ypt on 2017/9/26.
 */

public class EventActivity extends Activity implements View.OnClickListener {

    private Button mBtnTest;
    private MainSubscriber<TestEvent> testSubscriber;
    private static Toast mToast;
    private PkScoreBoardView mPsbvTest;
    private PkArenaOpponentInfoView mPaoivTest;
    private PkArenaTimerWindowView mTimerTest;
    private PkArenaOpponentGiftView mPaogvTest;
    private ImageView mRelayScoreIv;
    private BottomLineProgressDrawable mRelayDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initView();
        initEvent();
    }

    private void initEvent() {
        mBtnTest.setOnClickListener(this);
        mPaoivTest.setHasPkHistory(true)
                .setIsCanFollow(true)
                .setIconUri(Uri.parse(""))
                .setCombo("6连胜")
                .setOpponentName("一代豪侠")
                .setOpponentLocation("吉林 · 10002.12km")
                .setPkHistory("2胜2负1平")
                .setIsBoss(true);
        if (mPaoivTest.getVisibility() != View.VISIBLE) {
            mPaoivTest.setVisibility(View.VISIBLE);
            mPaoivTest.show();
        } else {

        }
        testSubscriber = new MainSubscriber<TestEvent>() {
            @Override
            @Subscribe(threadMode = ThreadMode.MAIN)
            public void onMainThreadEvent(TestEvent param) {
                if (null == mToast) {
                    mToast = Toast.makeText(getApplicationContext(), param.txt, Toast.LENGTH_LONG);
                } else {
                    mToast.setText(param.txt);
                }
                mToast.show();
            }
        };
        testSubscriber.register();

        mTimerTest.resetTimer(6 * 60 * 1000);

        mTimerTest.setPkArenaTimerListener(new PkArenaTimerWindowView.PkArenaTimerListener() {
            @Override
            public void onCloseClick() {
                Toast.makeText(getApplicationContext(), "onCloseClick", Toast.LENGTH_LONG).show();
            }
        });

        mTimerTest.startTimerDown();
    }

    private void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mPsbvTest = (PkScoreBoardView) findViewById(R.id.psbv_test);
        mPaoivTest = (PkArenaOpponentInfoView) findViewById(R.id.paoiv_test);
        mTimerTest = (PkArenaTimerWindowView) findViewById(R.id.timer_test);
        mPaogvTest = (PkArenaOpponentGiftView) findViewById(R.id.paogv_test);
        mRelayScoreIv = (ImageView) findViewById(R.id.relay_score_iv);
        mRelayDrawable = new BottomLineProgressDrawable(Color.parseColor("#ff0000"),Color.parseColor("#cc00ef"),30f,0.1f);
        mRelayScoreIv.setImageDrawable(RoundRectDrawableFactory.getLeftBottomDrawable(30,Color.RED));
//        mRelayDrawable.setOurRound();

        mRelayDrawable.setAnimListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(getApplicationContext(),"时间到~！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    int index;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                float f = (float) Math.random();
                NotifyDispatcher.dispatch(new TestEvent(1, getApplicationContext().getString(R.string.event_test) + f));
                mPsbvTest.setScore((int) (1000000 * f), (int) (1000000 * (1 - f)));
                mRelayDrawable.startAnim(5000);
//                mRelayDrawable.setRate(f);

                Log.e("spr_ypt", "onClick: index=" + index);
                switch (index) {
                    case 0:
                        mTimerTest.changeToCrit();
                        break;
                    case 1:
                        mTimerTest.changeToNormal();
                        break;
                    case 2:
                        mTimerTest.changeToPunish();
                        break;
                    case 3:
                        mTimerTest.changeToNormal();
                        break;
                }
                index = (index + 1) % 4;
                if (index % 2 == 0) {
                    mPaogvTest.startAnim(true);
//                    mRelayDrawable.setOurRound();
                } else {
                    mPaogvTest.endAnim();
//                    mRelayDrawable.setOtherRound();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        testSubscriber.unregister();
        mPsbvTest.recycle();
        mTimerTest.recycle();
        super.onDestroy();
    }
}
