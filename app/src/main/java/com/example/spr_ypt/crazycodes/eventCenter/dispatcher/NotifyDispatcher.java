package com.example.spr_ypt.crazycodes.eventCenter.dispatcher;

import com.example.spr_ypt.crazycodes.eventCenter.event.BaseEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Spr_ypt on 2017/9/26.
 */

public class NotifyDispatcher implements BaseEventDispatcher {
    public static void dispatch(BaseEvent param) {
        EventBus.getDefault().post(param);
    }
}
