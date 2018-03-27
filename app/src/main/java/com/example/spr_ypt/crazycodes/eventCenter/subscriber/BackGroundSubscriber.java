package com.example.spr_ypt.crazycodes.eventCenter.subscriber;

import com.example.spr_ypt.crazycodes.eventCenter.event.BaseEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Spr_ypt on 2017/9/26.
 */

public abstract class BackGroundSubscriber<T extends BaseEvent> extends BaseSubscreber {
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public abstract void onEventBackgroundThread(T param);
}
