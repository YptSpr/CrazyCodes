package com.example.spr_ypt.crazycodes.pkpicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.R;

import java.lang.ref.WeakReference;

/**
 * Created by Spr_ypt on 2017/9/30.
 */

public class PkArenaOpponentInfoView extends RelativeLayout {
    private View mRootView;
    private ImageView mPkArenaOpponentIcon;
    private TextView mPkArenaInfoFirst;
    private TextView mPkArenaInfoSecond;
    private TextView mPkArenaInfoCombo;
    private ImageView mPkArenaInfoSlaveBtn;
    private PkArenaOpponentInfoEventListener mEventListener;
    private InfoViewHandler mHander;
    private int mCorrentShap = SHAP_BIG;
    private static final int SHAP_SMALL = 0x00020001;
    private static final int SHAP_BIG = 0x00020002;
    private InfoHolder info;
    private RelativeLayout mPkArenaInfoLl;
    private int page = PAGE_NORMAL;//记录信息翻滚到那种状态
    private static final int PAGE_NORMAL = 0x00030001;
    private static final int PAGE_PK_HISTORY = 0x00030002;


    public PkArenaOpponentInfoView(Context context) {
        super(context);
        init();
    }

    public PkArenaOpponentInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PkArenaOpponentInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PkArenaOpponentInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        initView();
        initEvent();
    }

    /**
     * 初始化views
     */
    private void initView() {
        mRootView = inflate(getContext(), R.layout.hani_view_window_pk_arena_opponent_info_view, this);
        mPkArenaOpponentIcon = (ImageView) mRootView.findViewById(R.id.pk_arena_opponent_icon);
        mPkArenaInfoFirst = (TextView) mRootView.findViewById(R.id.pk_arena_info_first);
        mPkArenaInfoCombo = (TextView) mRootView.findViewById(R.id.pk_arena_info_combo);
        mPkArenaInfoSlaveBtn = (ImageView) mRootView.findViewById(R.id.pk_arena_info_follow_btn);
        mPkArenaInfoSecond = (TextView) findViewById(R.id.pk_arena_info_second);
        setBackgroundResource(R.drawable.hani_window_view_pk_arena_score_bg);
        setTranslationX(500);
        mPkArenaInfoLl = (RelativeLayout) findViewById(R.id.pk_arena_info_ll);

    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mPkArenaInfoSlaveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏关注按钮
                mPkArenaInfoSlaveBtn.setVisibility(GONE);
                mPkArenaInfoFirst.setMaxWidth((int) getResources().getDimension(R.dimen.demin_80dp));
                if (null != mEventListener) {
                    mEventListener.onClickSlaveBtn();
                }
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToBig();
                if (null != mEventListener) {
                    mEventListener.onClick();
                }
            }
        });
        mHander = new InfoViewHandler(this);
        info = new InfoHolder();

    }


    //以下为设置数据的方法们
    public PkArenaOpponentInfoView setHasPkHistory(boolean hasPkHistory) {
        this.info.hasPkHistory = hasPkHistory;
        return this;
    }

    public PkArenaOpponentInfoView setIconUri(Uri iconUri) {
        this.info.iconUri = iconUri;
        return this;
    }

    public PkArenaOpponentInfoView setOpponentName(String opponentName) {
        this.info.opponentName = opponentName;
        return this;
    }

    public PkArenaOpponentInfoView setCombo(String combo) {
        this.info.combo = combo;
        return this;
    }

    public PkArenaOpponentInfoView setPkHistorytitle(String pkHistorytitle) {
        this.info.pkHistorytitle = pkHistorytitle;
        return this;
    }

    public PkArenaOpponentInfoView setPkHistory(String pkHistory) {
        this.info.pkHistory = pkHistory;
        return this;
    }

    public PkArenaOpponentInfoView setOpponentLocation(String opponentLocation) {
        this.info.opponentLocation = opponentLocation;
        return this;
    }

    public PkArenaOpponentInfoView setIsCanFollow(boolean isCanFollow) {
        this.info.isCanFollow = isCanFollow;
        return this;
    }

    public PkArenaOpponentInfoView setIsBoss(boolean isBoss) {
        this.info.isBoss = isBoss;
        return this;
    }
    //以上为设置数据的方法们


    /**
     * 设置信息
     */
    public void setData() {

    }

    /**
     * 展示方法，请在{@link #setData()}之后调用
     */
    public void show() {
        //TODO 判断数据是否已有
        //TODO 有空加个炫酷的显示动画

        resetDataAsNormal();

        animate().translationX(0).setInterpolator(new DecelerateInterpolator()).setDuration(500).start();

        changeToSmallDelay();
        scrollPageDelay();


    }

    /**
     * 隐藏信息只显示头像名字的方法
     */
    private void changeToSmall() {
        if (mCorrentShap == SHAP_SMALL) return;

        ValueAnimator animator1 = ObjectAnimator.ofInt(getLayoutParams().width, (int) getResources().getDimension(R.dimen.demin_94dp));
        ValueAnimator animator2 = ObjectAnimator.ofInt(getLayoutParams().height, (int) getResources().getDimension(R.dimen.demin_24dp));
        ValueAnimator animator3 = ObjectAnimator.ofInt(mPkArenaOpponentIcon.getLayoutParams().width,
                (int) getResources().getDimension(R.dimen.demin_20dp));
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getLayoutParams().width = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getLayoutParams().height = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });

        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPkArenaOpponentIcon.getLayoutParams().width = (int) animation.getAnimatedValue();
                mPkArenaOpponentIcon.getLayoutParams().height = (int) animation.getAnimatedValue();
                mPkArenaOpponentIcon.requestLayout();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1, animator2, animator3);
        set.setInterpolator(new OvershootInterpolator(2f));
        set.setDuration(500);
        set.start();

        resetDataAsSmall();

        mCorrentShap = SHAP_SMALL;
    }

    /**
     * 显示详细信息的方法
     */
    private void changeToBig() {
        if (mCorrentShap == SHAP_BIG) return;

        ValueAnimator animator1 = ObjectAnimator.ofInt(getLayoutParams().width, (int) getResources().getDimension(R.dimen.demin_130_5dp));
        ValueAnimator animator2 = ObjectAnimator.ofInt(getLayoutParams().height, (int) getResources().getDimension(R.dimen.demin_36dp));

        ValueAnimator animator3 = ObjectAnimator.ofInt(mPkArenaOpponentIcon.getLayoutParams().width,
                (int) getResources().getDimension(R.dimen.demin_32dp));

        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getLayoutParams().width = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getLayoutParams().height = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });

        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPkArenaOpponentIcon.getLayoutParams().width = (int) animation.getAnimatedValue();
                mPkArenaOpponentIcon.getLayoutParams().height = (int) animation.getAnimatedValue();
                mPkArenaOpponentIcon.requestLayout();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1, animator2, animator3);
        set.setInterpolator(new OvershootInterpolator(2f));
        set.setDuration(500);
        set.start();

        resetDataAsNormal();

        mCorrentShap = SHAP_BIG;

        changeToSmallDelay();

        scrollPageDelay();
    }

    private void scrollPageDelay() {
        if (info.hasPkHistory && mCorrentShap == SHAP_BIG) {
            mHander.removeMessages(InfoViewHandler.PK_ARENA_INFO_SCROLL);
            mHander.sendEmptyMessageDelayed(InfoViewHandler.PK_ARENA_INFO_SCROLL, 8 * 1000);
        }
    }

    private void changeToSmallDelay() {
        mHander.removeMessages(InfoViewHandler.PK_ARENA_OPPONENT_INFO_SMALL);
        mHander.sendEmptyMessageDelayed(InfoViewHandler.PK_ARENA_OPPONENT_INFO_SMALL, info.hasPkHistory ? 16 * 1000 : 8 * 1000);
    }


    private void scrollPage() {
        if (!info.hasPkHistory || mCorrentShap == SHAP_SMALL) return;

        mPkArenaInfoSecond.animate().translationY(-getHeight()).setDuration(100)
                .setInterpolator(new AccelerateInterpolator()).start();
        mPkArenaInfoLl.animate().translationY(-getHeight()).setDuration(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mPkArenaInfoSecond.setTranslationY(getHeight());
                        mPkArenaInfoLl.setTranslationY(getHeight());
                        switch (page) {
                            case PAGE_NORMAL:
                                resetDataAsPkHistory();
                                break;
                            case PAGE_PK_HISTORY:
                                resetDataAsNormal();
                                break;
                        }
                        mPkArenaInfoSecond.animate().translationY(0).setDuration(100)
                                .setInterpolator(new DecelerateInterpolator()).start();
                        mPkArenaInfoLl.animate().translationY(0).setDuration(100).setListener(null)
                                .setInterpolator(new DecelerateInterpolator()).start();

                    }
                }).setInterpolator(new AccelerateInterpolator()).start();

    }

    /**
     * 展示正常信息
     */
    private void resetDataAsNormal() {
        mPkArenaInfoFirst.setText(info.opponentName);
        mPkArenaInfoFirst.setMaxWidth((int) getResources().getDimension(R.dimen.demin_42dp));
        mPkArenaInfoCombo.setVisibility(VISIBLE);
        mPkArenaInfoCombo.setText(info.combo);
        mPkArenaInfoSecond.setVisibility(VISIBLE);
        if (info.isBoss) {
            mPkArenaInfoSecond.setTextSize(8);
            mPkArenaInfoSecond.setText("大魔王");
            mPkArenaInfoSecond.setPadding(20,5,20,5);
            mPkArenaInfoSecond.setBackgroundResource(R.drawable.hani_pk_arena_window_opponent_gift_multiple_bg);
        } else {
            mPkArenaInfoSecond.setTextSize(11);
            mPkArenaInfoSecond.setText(info.opponentLocation);
            mPkArenaInfoSecond.setPadding(0,0,0,0);
            mPkArenaInfoSecond.setBackground(null);
        }
        mPkArenaInfoSlaveBtn.setVisibility(GONE);
        page = PAGE_NORMAL;

    }

    /**
     * 展示对战历史信息
     */
    private void resetDataAsPkHistory() {
        mPkArenaInfoFirst.setText(info.pkHistorytitle);
        mPkArenaInfoFirst.setMaxWidth((int) getResources().getDimension(R.dimen.demin_80dp));
        mPkArenaInfoCombo.setVisibility(GONE);
        mPkArenaInfoSecond.setVisibility(VISIBLE);
        mPkArenaInfoSecond.setTextSize(11);
        mPkArenaInfoSecond.setPadding(0,0,0,0);
        mPkArenaInfoSecond.setBackground(null);
        mPkArenaInfoSecond.setText(info.pkHistory);
        mPkArenaInfoSlaveBtn.setVisibility(GONE);
        page = PAGE_PK_HISTORY;

    }

    /**
     * 展示小窗下的信息
     */
    private void resetDataAsSmall() {
        mPkArenaInfoFirst.setText(info.opponentName);
        mPkArenaInfoCombo.setVisibility(GONE);
        mPkArenaInfoSecond.setVisibility(GONE);
        if (info.isCanFollow) {
            mPkArenaInfoFirst.setMaxWidth((int) getResources().getDimension(R.dimen.demin_42dp));
            mPkArenaInfoSlaveBtn.setVisibility(VISIBLE);
        } else {
            mPkArenaInfoSlaveBtn.setVisibility(GONE);
            mPkArenaInfoFirst.setMaxWidth((int) getResources().getDimension(R.dimen.demin_80dp));
        }
    }


    public void setEventListener(PkArenaOpponentInfoEventListener eventListener) {
        this.mEventListener = eventListener;
    }

    /**
     * 对手信息框所有回调事件这里统一处理
     */
    public interface PkArenaOpponentInfoEventListener {
        /**
         * 加号按钮被点击
         */
        void onClickSlaveBtn();

        /**
         * 自己被点击
         */
        void onClick();
    }

    /**
     * 这个空间内部handler处理在这里统一管理
     * 目前只处理{@link #show()}5秒后自动缩小事件
     * 由于控件可能在{@link #show()}后5秒内被移除所以这里只用弱引用引入view
     */
    private static class InfoViewHandler extends Handler {
        private WeakReference<PkArenaOpponentInfoView> reference;

        private static final int PK_ARENA_OPPONENT_INFO_SMALL = 0x00010001;

        private static final int PK_ARENA_INFO_SCROLL = 0x00010002;

        public InfoViewHandler(PkArenaOpponentInfoView view) {
            reference = new WeakReference<PkArenaOpponentInfoView>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PkArenaOpponentInfoView view = reference.get();
            switch (msg.what) {
                case PK_ARENA_OPPONENT_INFO_SMALL:
                    if (null != view) {
                        view.mHander.removeMessages(InfoViewHandler.PK_ARENA_OPPONENT_INFO_SMALL);
                        view.mHander.removeMessages(PK_ARENA_INFO_SCROLL);
                        view.changeToSmall();
                    }
                    break;
                case PK_ARENA_INFO_SCROLL:
                    if (null != view) {
                        view.mHander.removeMessages(PK_ARENA_INFO_SCROLL);
                        view.scrollPage();
                        view.mHander.sendEmptyMessageDelayed(PK_ARENA_INFO_SCROLL, 8 * 1000);
                    }
                    break;
            }
        }
    }

    /**
     * 持有相关信息，单独维护，与外部实体类解耦
     */
    public static class InfoHolder {
        public boolean hasPkHistory = true;

        public Uri iconUri;

        public String opponentName;

        public String combo;//连胜

        public String pkHistorytitle = "过往战绩";

        public String pkHistory;//胜负关系

        public String opponentLocation;//位置信息

        public boolean isCanFollow = false;//是否显示关注按钮（未关注的主播端显示）

        public boolean isBoss = false;//是否大魔王

    }


}
