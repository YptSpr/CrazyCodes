package com.example.spr_ypt.crazycodes.inviteFriendDemo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Spr_ypt on 2017/12/7.
 */

public class InviteItemView extends FrameLayout {
    TextView tv;

    public InviteItemView(Context context) {
        super(context);
        init();
    }


    public InviteItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InviteItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public InviteItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        tv = new TextView(getContext());
        tv.setTextSize(28);
        tv.setTextColor(Color.WHITE);
        addView(tv);
    }


    public InviteItemView setData(String data) {
        tv.setText(data);
        return this;
    }

}
