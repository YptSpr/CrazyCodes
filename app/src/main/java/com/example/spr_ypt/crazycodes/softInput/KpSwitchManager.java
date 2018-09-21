package com.example.spr_ypt.crazycodes.softInput;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.example.spr_ypt.crazycodes.R;

import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchFSPanelLinearLayout;

public class KpSwitchManager {

    private Activity mContext = null;
    private PopupWindow popWindow = null;
    private ViewGroup mPopContent;
    private EditText mEtInput;
    private Button mBtnSwitch;
    private KPSwitchFSPanelLinearLayout mLayoutPanel;
    private Button mBtnTest;
    private InputMethodManager mImm;

    public KpSwitchManager(Activity mContext) {
        this.mContext = mContext;
        init();
    }

    private void init() {
        mPopContent = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.pop_input_2, null);
        initView();
        initPop();
        initEvent();
    }

    private void initPop() {
        popWindow = new PopupWindow(mPopContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // popupwindow弹出时的动画
        // popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        // 软键盘不会挡着popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

    boolean isSoftInputShow;

    private void initEvent() {
        mBtnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isSoftInputShow) {
                    mImm.hideSoftInputFromWindow(mEtInput.getWindowToken(), 0);
                    mLayoutPanel.setBackgroundColor(Color.RED);
                    isSoftInputShow = false;
                } else {
                    mImm.showSoftInput(mEtInput, 0);
                    mLayoutPanel.setBackgroundColor(Color.TRANSPARENT);
                    isSoftInputShow = true;
                }


            }
        });
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtInput.append("[TEST]");
            }
        });
    }

    private void initView() {
        mBtnSwitch = (Button) mPopContent.findViewById(R.id.btn_switch);
        mLayoutPanel = (KPSwitchFSPanelLinearLayout) mPopContent.findViewById(R.id.layout_panel);
        mBtnTest = (Button) mPopContent.findViewById(R.id.btn_test);
        mImm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public boolean isShowing() {
        return null != popWindow && popWindow.isShowing();
    }

    public void showSpeak(View parent) {
        if (isShowing()) {
            return;
        }

//        this.parent = parent;
//        mVTest.setVisibility(View.VISIBLE);
//
//
//        int isFullScreen=(mContext.getWindow().getAttributes().flags&WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        int isTraslate=(mContext.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


//        mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);

//        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


//        mImm.showSoftInputFromInputMethod(mEtInput.getWindowToken(), 0);
        // 设置菜单显示的位置
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        isSoftInputShow = true;
        KPSwitchConflictUtil.attach(mLayoutPanel, mBtnSwitch, mEtInput,
                new KPSwitchConflictUtil.SwitchClickListener() {

                    @Override
                    public void onClickSwitch(boolean switchToPanel) {
                        if (switchToPanel) {
                            mEtInput.clearFocus();
                        } else {
                            mEtInput.requestFocus();
                        }

                    }
                });

        // 优化显示效果


    }

    public void hideSpeak() {
        mImm.hideSoftInputFromWindow(mEtInput.getWindowToken(), 0);
        popWindow.dismiss();
    }
}
