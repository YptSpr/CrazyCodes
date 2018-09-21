package com.example.spr_ypt.crazycodes.softInput;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class TestLayoutView extends FrameLayout {

    private int proVisiblity;

    public TestLayoutView(@NonNull Context context) {
        super(context);
    }

    public TestLayoutView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestLayoutView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TestLayoutView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setVisibility(int visibility) {
        proVisiblity=visibility;
        super.setVisibility(visibility);
    }

    public void setProVisiblity(int visiblity){
        proVisiblity=visiblity;
    }


}
