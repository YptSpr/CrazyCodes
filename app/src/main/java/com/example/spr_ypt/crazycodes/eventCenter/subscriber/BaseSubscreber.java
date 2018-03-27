package com.example.spr_ypt.crazycodes.eventCenter.subscriber;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Spr_ypt on 2017/9/26.
 */

public abstract class BaseSubscreber {
    public void register() {
        if (!isRegister()) {
            EventBus.getDefault().register(this);
        }
    }

    public void unregister() {
        if (isRegister()) {
            EventBus.getDefault().unregister(this);
        }
    }

    public boolean isRegister() {
        return EventBus.getDefault().isRegistered(this);
    }
}
