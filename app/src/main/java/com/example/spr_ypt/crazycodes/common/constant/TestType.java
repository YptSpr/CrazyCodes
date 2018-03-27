package com.example.spr_ypt.crazycodes.common.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Spr_ypt on 2017/9/26.
 */

public class TestType {

    public static final int TYPE_ONE=0x00000001;
    public static final int TYPE_TWO=0x00000002;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_ONE,TYPE_TWO})
    public @interface TYPE_NUM{


    }

}
