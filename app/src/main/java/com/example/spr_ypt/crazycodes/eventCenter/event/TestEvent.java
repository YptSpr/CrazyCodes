package com.example.spr_ypt.crazycodes.eventCenter.event;

/**
 * Created by Spr_ypt on 2017/9/26.
 */

public class TestEvent implements BaseEvent {
    public int num;
    public String txt;

    public TestEvent(int num, String txt) {
        this.num = num;
        this.txt = txt;
    }
}
