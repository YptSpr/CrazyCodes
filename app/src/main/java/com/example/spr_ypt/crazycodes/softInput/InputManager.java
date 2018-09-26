package com.example.spr_ypt.crazycodes.softInput;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.spr_ypt.crazycodes.R;

@SuppressLint("LogUse")
public class InputManager {

    private Activity mContext = null;
    private PopupWindow popWindow = null;

    private KeyBoardRelativeLayout mPopContent;
    private EditText mEtInput;
    private MyTestView mVTest;
    private Button mBtnSwitch;
    private InputMethodManager mImm;
    private LinearLayout mLlInput;


    public InputManager(Activity mContext) {
        this.mContext = mContext;
        init();
    }

    private void init() {
        mPopContent = (KeyBoardRelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.pop_input, null);
        initView();
        initPop();
        initEvent();
    }

    boolean isSoftInputShow;

    private void initEvent() {
        mBtnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSoftInputShow) {
                    mImm.hideSoftInputFromWindow(mEtInput.getWindowToken(), 0);
//                    mVTest.setBackgroundColor(Color.RED);
                    isSoftInputShow = false;
                } else {
                    mImm.showSoftInput(mEtInput, 0);
//                    mVTest.setBackgroundColor(Color.TRANSPARENT);
                    isSoftInputShow = true;
                }

//                NavigationBarUtil.showNavigationBar(mContext,false);

            }
        });
        mVTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtInput.append("[TEST]");
            }
        });
    }

//    private KeyBoardRelativeLayout keyBoardRelativeLayout;

    private void initView() {
        mEtInput = (EditText) mPopContent.findViewById(R.id.et_input);
        mVTest = (MyTestView) mPopContent.findViewById(R.id.v_test);
        mBtnSwitch = (Button) mPopContent.findViewById(R.id.btn_switch);
        mImm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        mLlInput = mPopContent.findViewById(R.id.ll_input);
//        keyBoardRelativeLayout = mPopContent.findViewById(R.id.rl_keyboard);
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
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideSpeak();
//                mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            }
        });


        popWindow.getContentView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            int lastHeight = 2000;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                popWindow.getContentView().getWindowVisibleDisplayFrame(r);

                int difference = getScreenSize(mContext)[1] - r.bottom;


//                Log.e("spr_ypt=>inputPop", "pop lastHeight=" + lastHeight);
//                Log.e("spr_ypt=>inputPop", "pop difference=" + difference);


                if (difference > 100 && lastHeight != difference) {


//                    if (difference > lastHeight) {
//                        mVTest.getLayoutParams().height = 0;
//                        mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
//                        mVTest.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//                            }
//                        });
//                    } else {
//                    int inputType = mEtInput.getInputType();

//                    mVTest.getLayoutParams().height = difference;
//                    }
//                    mVTest.requestLayout();
                    lastHeight = difference;

//                    mVTest.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mEtInput.setFocusable(true);
//                            mEtInput.requestFocus();
//                        }
//                    }, 200);
//                    mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//                        popWindow.update();
//                    }
//                    ((RelativeLayout.LayoutParams) mEtInput.getLayoutParams()).bottomMargin = difference;
//                    mEtInput.requestLayout();

                }

//                Log.e("spr_ypt=>inputPop", "mPopContent onGlobalLayout isSoftInputShow=" + isSoftInputShow);

//                Log.e("spr_ypt=>inputPop", "onGlobalLayout: ");

//                if (isSoftInputShow){
//                    mVTest.setVisibility(View.GONE);
//                }else {
//                    mVTest.setVisibility(View.VISIBLE);
//                }
            }
        });

        mPopContent.setSizeChangedListener(new KeyBoardRelativeLayout.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
//                Log.e("spr_ypt=>inputPop", "mPopContent onSizeChanged: oldh=" + oldh);
//                Log.e("spr_ypt=>inputPop", "mPopContent onSizeChanged: h=" + h);

//                Rect outRect = new Rect();
//                popWindow.getContentView().getWindowVisibleDisplayFrame(outRect);
//                Log.e("spr_ypt=>inputPop", "onSizeChanged: outRect=" + outRect.toString());
            }

            int lastHeight = 0;


            int mSuitHeight = 0;


            @Override
            public boolean onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                Rect r = new Rect();
                popWindow.getContentView().getWindowVisibleDisplayFrame(r);
//                Log.e("spr_ypt=>inputPop", "onMeasure: getWindowVisibleDisplayFrame r="+r.toString());
//                popWindow.getContentView().getGlobalVisibleRect(r);
//                Log.e("spr_ypt=>inputPop", "onMeasure: getGlobalVisibleRect r="+r.toString());
//                popWindow.getContentView().getLocalVisibleRect(r);
//                Log.e("spr_ypt=>inputPop", "onMeasure: getLocalVisibleRect r="+r.toString());
//                int index = r.bottom - r.top;
//
//                Log.e("spr_ypt=>inputPop", "onMeasure: [index]=" + index);
//
//                if (index<0){
//                    return;
//                }
//
//                if (mLastIndex==0){
//                    mLastIndex=index;
//                    return;
//                }
//
//                int offset=mLastIndex-index;
//
//                if (Math.abs(offset)<=100){
//                    return;
//                }


                int difference = getScreenSize(mContext)[1] - r.bottom;

                Log.e("spr_ypt=>inputPop", "onMeasure: [difference]=<" + difference + ">");
                Log.e("spr_ypt=>inputPop", "onMeasure: [r.top]=<" + r.top + ">");


                if (difference > getScreenSize(mContext)[1] * 0.8f) {
                    return true;
                }


                if (difference < 0) {
                    difference = 0;
                }

                if (lastHeight != difference) {

                    if (difference > 0) {
                        mVTest.setVisibility(View.GONE);
//                        mVTest.getLayoutParams().height=1;
                        mSuitHeight = difference;
                    } else {
                        mVTest.setVisibility(View.VISIBLE);
                        mVTest.getLayoutParams().height = mSuitHeight;

                    }
                    lastHeight = difference;
                }
                return true;
            }

        });


        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    //如果焦点不在popupWindow上，且点击了外面，不再往下dispatch事件：
                    //不做任何响应,不 dismiss popupWindow

//                    return true;

                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    int[] etLocation = new int[2];
                    mEtInput.getLocationInWindow(etLocation);

                    if (event.getY() < etLocation[1]) {
                        hideSpeak();
                        return true;
                    }

                    if (event.getY() > etLocation[1] + mPopContent.getHeight()) {
//                        if (!isSoftInputShow) {
//                            mSelfInputLayout.dispatchTouchEvent(event);
//                            return true;
//                        }
                    }
                }
                //否则default，往下dispatch事件:关掉popupWindow，
                return false;
            }
        });


    }

    private View parent;


    public void showSpeak(View parent) {
        if (isShowing()) {
            return;
        }

        this.parent = parent;
//        mVTest.setVisibility(View.VISIBLE);
//
//
//        int isFullScreen=(mContext.getWindow().getAttributes().flags&WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        int isTraslate=(mContext.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


//        mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);

//        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mImm.showSoftInput(mEtInput, 0);
            }
        }, 50);


        // 设置菜单显示的位置
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        isSoftInputShow = true;
//        mVTest.setBackgroundColor(Color.RED);

        showStatus(true);


        // 优化显示效果


    }

    public void showPanel(View parent){
        if (isShowing()) {
            return;
        }
        this.parent = parent;
        mVTest.setVisibility(View.VISIBLE);

        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        isSoftInputShow = false;

    }

    private void showStatus(boolean hideStatusBar) {
        if (!NavigationBarUtil.isNavigationBarShow(mContext)) return;
        if (hideStatusBar) {
            WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            mContext.getWindow().setAttributes(lp);
        } else {//显示状态栏
            WindowManager.LayoutParams attr = mContext.getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mContext.getWindow().setAttributes(attr);
        }
    }


    public boolean isShowing() {
        return null != popWindow && popWindow.isShowing();
    }

    public void hideSpeak() {
        mImm.hideSoftInputFromWindow(mEtInput.getWindowToken(), 0);
//        mVTest.setVisibility(View.GONE);
        popWindow.dismiss();
        showStatus(false);
    }


    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }
}
