package com.example.spr_ypt.crazycodes.voice;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.TextureView;

import com.example.spr_ypt.crazycodes.R;
import com.example.spr_ypt.crazycodes.thread.MoliveThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Spr_ypt on 2017/9/21.
 */

public class NewVoiceRippleView extends TextureView implements TextureView.SurfaceTextureListener {

    private final int TAG = 1;
    // line画笔
    private Paint mLinePaint;
    private int mMaxWidth;
    private int mMinWidth;
    private int mMaxHeight;
    // 每个波纹的间距
    private int mDistance;
    private List<Integer> mStartWidthList = new ArrayList<>();
    private List<Float> mVoiceValue = new ArrayList<>();
    private float mFramemDistanceChange;
    // 画笔粗细
    private int mPaintSize;
    // 画笔颜色
    private int mPaintColor;
    // 最多几个环
    private int mMaxLine;
    private RectF mRectF;
    private int mNormalRadio = 90;
    private volatile boolean mAnimatorRunning;
    private int mMaxColorAddWidth;
    private double mAudioSwitchValue;
    private int mFrameTime;
    private volatile boolean mIsMute;
    private int mPanding;
    private boolean mLongTime;
    private volatile boolean mSurfaceViewDestroy;
    private volatile boolean mViewVisiable;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null && msg.what == 1) {
                float volume = new Random(System.currentTimeMillis()).nextFloat();
                setVolume(Math.max(volume - (int) volume, 0.3f), false);
            }
        }
    };

    public NewVoiceRippleView(Context context) {
        this(context, null);

    }

    public NewVoiceRippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }


    public NewVoiceRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSetting();
        initAttrs(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NewVoiceRippleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSetting();
        initAttrs(context, attrs);
        init();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurfaceViewDestroy = false;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mSurfaceViewDestroy = true;
        mAnimatorRunning = false;
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.voiceRippleLine);
        mMaxWidth = a.getDimensionPixelSize(R.styleable.voiceRippleLine_maxWidth, 0);
        mMinWidth = a.getDimensionPixelSize(R.styleable.voiceRippleLine_minWidth, 0);
        mMaxHeight = a.getDimensionPixelSize(R.styleable.voiceRippleLine_maxHeight, 0);
        mPaintSize = a.getDimensionPixelSize(R.styleable.voiceRippleLine_paintSize, 2);
        mMaxLine = a.getInteger(R.styleable.voiceRippleLine_maxLine, 3);
        mPaintColor = a.getColor(R.styleable.voiceRippleLine_paintColor, Color.RED);
        mNormalRadio = a.getInteger(R.styleable.voiceRippleLine_normalRadio, 90);
        mPanding = a.getDimensionPixelSize(R.styleable.voiceRippleLine_panding, 10);
        a.recycle();
        if (mMaxHeight == 0) mMaxHeight = mMaxWidth;
    }

    private void initSetting() {
        //透明
        setOpaque(false);
    }

    private void init() {
//        IndexConfig.DataEntity indexConfig = IndexConfigs.getInstance().getIndexConfig();
//        if (indexConfig != null) {
//            mAudioSwitchValue = indexConfig.getRadio_animation_default_set();
//        }
        mLinePaint = new Paint();
        // 消锯齿
        mLinePaint.setAntiAlias(true);
        // 线
        mLinePaint.setStyle(Paint.Style.STROKE);
        // 画笔粗细
        mLinePaint.setStrokeWidth(mPaintSize);
        // 画笔颜色
        mLinePaint.setColor(mPaintColor);
        // 根据波纹个数和间距  计算每个波纹的间距
        mLongTime = mMaxLine <= 2;
        mDistance = (mMaxWidth - mMinWidth) / (2 * mMaxLine + 1);
        mFrameTime = mLongTime ? 40 : 20;
        mFramemDistanceChange = mDistance * 1.0f / 10;
        mMaxColorAddWidth = (int) (mMaxLine * 1.0f / (mMaxLine + 1) * mMaxWidth);

    }

    private void resetData() {
        mStartWidthList.clear();
        mVoiceValue.clear();
    }

    /**
     * @param volume 音量大小
     * @param isSEI  sei  1秒回掉一次  中间模拟一条假数据
     */
    public void setVolume(float volume, boolean isSEI) {
        if (volume < mAudioSwitchValue || mIsMute || mSurfaceViewDestroy)
            return;

        if (isSEI) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(TAG, 500);
        }

        mStartWidthList.add(mMinWidth);
        mVoiceValue.add(volume);
        if (!mAnimatorRunning && !mIsMute && !mSurfaceViewDestroy && mViewVisiable) {
            mAnimatorRunning = true;
            startDrawThread();
        }
    }

    private void startDrawThread() {
        MoliveThreadPool.getAudioAnimatorPool().execute(new Runnable() {
            @Override
            public void run() {
                while (mAnimatorRunning && !mIsMute && !mSurfaceViewDestroy && mViewVisiable) {
                    Canvas canvas = null;
                    try {
                        if (mSurfaceViewDestroy) {
                            Thread.sleep(100);
                            continue;
                        }

                        long startTime = System.currentTimeMillis();
                        // double check
                        if (!mViewVisiable || mSurfaceViewDestroy) {
                            return;
                        }

                        canvas = lockCanvas();
                        if (canvas != null) {
                            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                            drawCanvas(canvas);
                        }

                        unlockCanvasAndPost(canvas);
                        canvas = null;
                        long endTime = System.currentTimeMillis();
                        Thread.sleep(endTime - startTime > mFrameTime ? 0 : mLongTime ? mFrameTime - (endTime - startTime) : endTime - startTime);
                    } catch (Exception e) {
                        mAnimatorRunning = false;
                        e.printStackTrace();
                    } finally {
                        try {
                            if (canvas != null) {
                                unlockCanvasAndPost(canvas);
                            }
                        } catch (Exception e) {

                        }
                    }
                }

                mAnimatorRunning = false;
                //  清屏
                clearWindow();
            }

        });
    }

    private void clearWindow() {
        Canvas canvas = null;
        try {
            resetData();
            if (mSurfaceViewDestroy || !mViewVisiable)
                return;


            canvas = lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (canvas != null) {
                    unlockCanvasAndPost(canvas);
                }
            } catch (Exception e) {

            }
        }
    }


    private void drawCanvas(Canvas canvas) {
        // 依次绘制 同心圆
        for (int i = 0; i < mStartWidthList.size(); i++) {

            int startWidth = mStartWidthList.get(i);
            int alpha;
            if (startWidth > mMaxColorAddWidth) {
                alpha = Math.max((int) ((1 - (startWidth - mMaxColorAddWidth) * 1.0f / (mMaxWidth - mMaxColorAddWidth)) * 255), 0);
            } else {
                alpha = (int) ((startWidth - mMinWidth) * 1.0f / (mMaxColorAddWidth - mMinWidth) * 255);
            }

            mLinePaint.setAlpha(alpha);
            drawView(canvas, startWidth, getRadio(startWidth, mVoiceValue.get(i)));
            if (startWidth + 10 < mMaxWidth) {
                mStartWidthList.set(i, startWidth + (int) mFramemDistanceChange);
            }
        }

        if (mStartWidthList.size() != 0 && (mStartWidthList.get(0) > mMaxWidth - mPanding || mStartWidthList.size() > mMaxLine + 2)) {
            mStartWidthList.remove(0);
            mVoiceValue.remove(0);
        }

        if (mStartWidthList.size() == 0) {
            mAnimatorRunning = false;
        }
    }

    public float getRadio(int currentWeight, float voiceValue) {
        return mNormalRadio * voiceValue / (currentWeight * 1.0f / mMinWidth);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        mViewVisiable = visibility == VISIBLE;
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onDetachedFromWindow() {
        mAnimatorRunning = false;
        mVoiceValue.clear();
        mStartWidthList.clear();
        mHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    private void drawView(Canvas canvas, int width, float radio) {
        int leftOffest = Math.max(0, (mMaxWidth - width) / 2);
        int topOffest = (mMaxHeight - width) / 2;

        if (mRectF == null)
            mRectF = new RectF();

        mRectF.set(leftOffest, topOffest, getWidth() - leftOffest, getHeight() - topOffest);// 左侧扇形
        canvas.drawArc(mRectF, 180 - radio / 2, radio, false, mLinePaint);

        mRectF.set(leftOffest, topOffest, getWidth() - leftOffest, getHeight() - topOffest);// 右侧扇形
        canvas.drawArc(mRectF, 360 - radio / 2, radio, false, mLinePaint);
    }
}
