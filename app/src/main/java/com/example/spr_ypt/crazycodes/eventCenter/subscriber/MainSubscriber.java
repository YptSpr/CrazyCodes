package com.example.spr_ypt.crazycodes.eventCenter.subscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Spr_ypt on 2017/9/26.
 */

public abstract class MainSubscriber<T> extends BaseSubscreber {
    @Subscribe(threadMode = ThreadMode.MAIN)
   public abstract void onMainThreadEvent(T param);
}
