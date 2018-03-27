package com.example.spr_ypt.crazycodes.inviteFriendDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Spr_ypt on 2017/12/7.
 */

public class InviteFragment extends Fragment {

    private String mData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText(mData);
        tv.setTextSize(28);
        tv.setTextColor(Color.WHITE);
        return tv;
    }

    public InviteFragment setData(String data) {
        mData = data;
        return this;
    }
}
