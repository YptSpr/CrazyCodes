package com.example.spr_ypt.crazycodes;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.spr_ypt.crazycodes.inviteFriendDemo.InviteFragment;
import com.example.spr_ypt.crazycodes.inviteFriendDemo.InvitePop;
import com.example.spr_ypt.crazycodes.inviteFriendDemo.TabChildView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Spr_ypt on 2017/12/6.
 */

public class TestFragmentActivity extends FragmentActivity {
    private List<String> tops;
    private Button mBtnPop;
    private InvitePop pop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);
        initTestData();
        initView();
        initEvent();
    }

    private void initTestData() {
        tops = new ArrayList<>();
        tops.add("好友");
        tops.add("工会");
        tops.add("最近");
        tops.add("top");
        tops.add("春天");
        tops.add("凝视");
        tops.add("你猜");
    }

    private void initEvent() {
        mBtnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == pop) {
                    pop = new InvitePop(TestFragmentActivity.this);
                }
                pop.show(getWindow().getDecorView());
            }
        });
    }

    private void initView() {

        mBtnPop = (Button) findViewById(R.id.btn_pop);
    }

}
