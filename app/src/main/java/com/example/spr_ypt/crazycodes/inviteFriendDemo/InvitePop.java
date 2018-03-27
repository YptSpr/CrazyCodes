package com.example.spr_ypt.crazycodes.inviteFriendDemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.spr_ypt.crazycodes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spr_ypt on 2017/12/7.
 */

public class InvitePop extends PopupWindow {
    private Activity mActivity;
    private View mContentView;

    private TabLayout mTopList;
    private ViewPager mContentList;
    private List<String> tops;
    private List<View> mViewList;

    public InvitePop(Activity activity) {
        super(activity);
        ColorDrawable dw = new ColorDrawable(0x00000);
        setBackgroundDrawable(dw);
        mActivity = activity;
        ContextThemeWrapper ctx=new ContextThemeWrapper(activity,R.style.Theme_AppCompat);
        mContentView = LayoutInflater.from(ctx).inflate(R.layout.pop_invite_friend, null);
        setContentView(mContentView);
        setTouchable(true);
        setOutsideTouchable(true);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight((int) mActivity.getResources().getDimension(R.dimen.demin_234_5dp));

        initTestData();
        initView();
        initEvent();
    }

    public void show(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        mTopList.getTabAt(2).select();
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

        mViewList = new ArrayList<>();
        for (String top : tops) {
            mViewList.add(new InviteItemView(mActivity).setData(top));
        }
    }

    private void initEvent() {
        for (int i = 0; i < mTopList.getTabCount(); i++) {
            mTopList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (null != tab && null != tab.getCustomView() && tab.getCustomView() instanceof TabChildView) {
                        ((TabChildView) tab.getCustomView()).setPointVisible(View.GONE);
                        ((TabChildView) tab.getCustomView()).setTextColor(mActivity.getResources().getColor(R.color.colorAccent));
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    if (null != tab && null != tab.getCustomView() && tab.getCustomView() instanceof TabChildView) {
                        ((TabChildView) tab.getCustomView()).setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    private void initView() {
        mTopList = mContentView.findViewById(R.id.top_list);
        mTopList.setTabMode(TabLayout.MODE_SCROLLABLE);
        mContentList = mContentView.findViewById(R.id.content_list);
        mContentList.setAdapter(new InviteFriendViewPagerAdapter(mViewList));
        mContentList.setCurrentItem(0);
        mTopList.setupWithViewPager(mContentList);

        for (int i = 0; i < mTopList.getTabCount(); i++) {
            TabLayout.Tab tab = mTopList.getTabAt(i);
            String title = (String) tab.getText();
            if (null != tab.getCustomView()) {
                ((ViewGroup) tab.getCustomView().getParent()).removeAllViews();
            }
            tab.setCustomView(new TabChildView(mActivity.getApplicationContext()).setData(title));
        }

        mTopList.setSelectedTabIndicatorColor(Color.WHITE);

    }

    private class InviteFriendViewPagerAdapter extends PagerAdapter {


        private List<View> mViewList;

        public InviteFriendViewPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return tops.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tops.get(position);//页卡标题
        }
    }
}
