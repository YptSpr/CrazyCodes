package com.example.spr_ypt.crazycodes.horizontaltagprogress;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spr_ypt.crazycodes.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HorizontalTagProgressView extends HorizontalScrollView {

    private FrameLayout rootView;

    private ImageView progressView;

    private HorizontalTagProgressDrawable progressDrawable;

    private List<TagEntity> tags;

    private static final int TOUCH_DELAY_TIME = 5 * 1000;

    private Handler mDelayHandler;

    private boolean isTouching;

    private float mCurrentRate;

    public HorizontalTagProgressView(@NonNull Context context) {
        super(context);
        init();
    }

    public HorizontalTagProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalTagProgressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalTagProgressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setFillViewport(false);
        setHorizontalScrollBarEnabled(false);

        rootView = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.hani_view_horizontal_tag_progress, null);

        addView(rootView);

        progressView = rootView.findViewById(R.id.iv_tag_progress);

        progressDrawable = new HorizontalTagProgressDrawable();
        progressView.setImageDrawable(progressDrawable);

        mDelayHandler = new Handler();
    }

    public void setProgress(long score) {
        if (tags == null || tags.size() <= 0) return;

        float tagLineRate = 1f / (tags.size() + 1);
        float rate = 0f;
        for (int i = 0; i < tags.size(); i++) {
            if (score >= tags.get(i).getScore()) {
                tags.get(i).getTextView().setTextColor(Color.WHITE);
                rate += tagLineRate;
                if (i == tags.size() - 1) {
                    long diff = score - tags.get(i).getScore();
                    if (diff > 0) {
                        rate += tagLineRate * (1f - 1f / diff);
                    }
                }
            } else {
                tags.get(i).getTextView().setTextColor(Color.parseColor("#66ffffff"));
                long lastTagScore = i > 0 ? tags.get(i - 1).getScore() : 0;
                long diff = score - lastTagScore;
                if (diff > 0) {
                    rate += tagLineRate * ((float) diff / (tags.get(i).getScore() - lastTagScore));
                }
            }
        }

        if (Math.abs(rate - mCurrentRate) < 0.001f) return;

        mCurrentRate = rate;

        progressDrawable.setRate(rate);

        if (!isTouching) {
            scrollByRate(rate, false);
        }
    }

    private void scrollByRate(float rate, boolean isSmooth) {
        int scrollx = (int) (rate * progressView.getWidth() - getWidth() / 2);
        if (scrollx < 0) {
            scrollx = 0;
        }
        if (scrollx > progressView.getWidth() - getWidth()) {
            scrollx = progressView.getWidth() - getWidth();
        }

        if (isSmooth) {
            smoothScrollTo(scrollx, 0);
        } else {
            scrollTo(scrollx, 0);
        }
    }

    public void initTags(List<TagEntity> tags) {
        Collections.sort(tags);
        this.tags = tags;

        int perWidth = (int) getResources().getDimension(R.dimen.demin_50dp);
        //计算进度条宽度
        progressView.getLayoutParams().width = (tags.size() + 1) * perWidth;
//        progressView.invalidate();

        //设置进度条分割线
        float tagLineRate = 1f / (tags.size() + 1);
        List<Float> tagLines = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            tagLines.add(tagLineRate * (i + 1));
        }
        progressDrawable.resetTags(tagLines);

        //设置文本
        for (int i = 0; i < tags.size(); i++) {
            TextView tagTextView = new TextView(getContext());
            tagTextView.setTextSize(9);
            tagTextView.setTextColor(Color.parseColor("#66ffffff"));
            tagTextView.setGravity(Gravity.CENTER);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(perWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = (int) getResources().getDimension(R.dimen.demin_8dp);
            lp.leftMargin = (int) ((i + 0.5f) * perWidth);

            rootView.addView(tagTextView, lp);

            tagTextView.setText(String.valueOf(tags.get(i).getTagName() + "\n" + formateScore(tags.get(i).getScore())));

            tags.get(i).setTextView(tagTextView);
        }

        invalidate();
//        requestLayout();
    }

    private String formateScore(long score) {
        if (score < 100000L) {
            return String.valueOf(score);
        } else if (score < 100000000L) {
            return score / 10000 + "万";
        } else if (score < 1000000000000L) {
            return score / 10000000L + "亿";
        } else {
            return score / 1000000000000L + "兆";
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("spr_ypt=>tag_p", "dispatchTouchEvent: " + ev.getAction());
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            isTouching = true;
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            mDelayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isTouching = false;
                    scrollByRate(mCurrentRate, true);

                }
            }, TOUCH_DELAY_TIME);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDelayHandler.removeCallbacksAndMessages(null);
    }

    public static class TagEntity implements Comparable<TagEntity> {
        private int tagId;
        private String tagName;
        private long score;
        private TextView textView;

        public TagEntity(int tagId, String tagName, long score) {
            this.tagId = tagId;
            this.tagName = tagName;
            this.score = score;
        }

        public int getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public long getScore() {
            return score;
        }

        public void setScore(long score) {
            this.score = score;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        @Override
        public int compareTo(@NonNull TagEntity o) {
            if (o.score - this.score > 0) return -1;
            else if (o.score - this.score < 0) return 1;
            else return 0;
        }
    }


}
