package com.example.spr_ypt.crazycodes.rankedGame.score;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ScoreContainerType {
    public static final int STATE_NONE = 0x000000;//空白模式
    public static final int STATE_PREPARE = 0x000001;//某某任务即将开始的模式
    public static final int STATE_EGGS = 0x000002;//菜单模式
    public static final int STATE_PROGRESS = 0x000003;//任务倒计时模式
    public static final int STATE_RANK = 0x000004;//显示段位的模式
    public static final int STATE_OPEN = 0x000005;//开场红蓝对决模式
    public static final int STATE_END = 0x000006;//结束模式(PK胜利)
    public static final int STATE_MISSION_CLEAR = 0x000007;//任务完成
    public static final int STATE_MISSION_FAILED = 0x000008;//任务失败

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_NONE, STATE_PREPARE, STATE_EGGS, STATE_PROGRESS, STATE_RANK, STATE_OPEN, STATE_END, STATE_MISSION_CLEAR, STATE_MISSION_FAILED})
    public @interface SHOW_STATE {
    }

    public static boolean isOnTask(@SHOW_STATE int state) {
        return state == STATE_EGGS || state == STATE_PROGRESS || state == STATE_PREPARE||state==STATE_NONE;
    }
}
