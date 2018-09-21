package com.example.spr_ypt.crazycodes.softInput;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class KeyBoardRelativeLayout extends LinearLayout {
    private OnSizeChangedListener mListener;

    public KeyBoardRelativeLayout(Context context) {
        super(context);
    }

    public KeyBoardRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyBoardRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public KeyBoardRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.mListener != null) {
            mListener.onSizeChanged(w, h, oldw, oldh);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.e("spr_ypt=>inputPop", "onScrollChanged: l=" + l + "||t=" + t + "oldl=" + oldl + "oldt=" + oldt);
    }

    public interface OnSizeChangedListener {
        void onSizeChanged(int w, int h, int oldw, int oldh);

        boolean onMeasure(int widthMeasureSpec, int heightMeasureSpec);
    }

    public void setSizeChangedListener(OnSizeChangedListener mListener) {
        this.mListener = mListener;
    }

    int lastWidthMeasureSpec;

    int lastHeightMeasureSpec;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        int height = rect.bottom - rect.top;

        if (null != mListener) mListener.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        Log.e("spr_ypt=>inputPop", "KeyBoardRelativeLayout onMeasure: heightMeasureSpec"+ MeasureSpec.getSize(heightMeasureSpec));
//        Log.e("spr_ypt=>inputPop", "KeyBoardRelativeLayout onMeasure: rect.height"+ height);

    }
}
