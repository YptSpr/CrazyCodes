package com.example.spr_ypt.crazycodes.test4Layout;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.spr_ypt.crazycodes.R;

/**
 * Created by Spr_ypt on 2017/9/18.
 */

public class BaseView extends FrameLayout {

    private View mRootView;

    private ImageView mIvLeft;
    private ImageView mIvRight;

    public BaseView(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        init(this);
    }

    private void init(ViewGroup viewGroup) {
        mRootView=inflate(getContext(), R.layout.view_base, viewGroup);
        initView(mRootView);
    }


    private void initView(View mRootView) {
        mIvLeft = (ImageView) mRootView.findViewById(R.id.iv_left);
        mIvRight = (ImageView) mRootView.findViewById(R.id.iv_right);
    }
}
