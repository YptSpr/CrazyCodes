package com.example.spr_ypt.crazycodes.eventCenter.subscriber;

import com.example.spr_ypt.crazycodes.eventCenter.event.BaseEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Spr_ypt on 2017/9/26.
 */

public abstract class PostingSubscriber<T extends BaseEvent> extends BaseSubscreber {
    @Subscribe(threadMode = ThreadMode.POSTING)
    public abstract void onEvent(T param);
}
