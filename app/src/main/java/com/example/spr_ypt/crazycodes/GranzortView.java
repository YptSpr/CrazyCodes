package com.example.spr_ypt.crazycodes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Spr_ypt on 2017/9/8.
 */

public class GranzortView extends View {
    public static final String TAG = GranzortView.class.getSimpleName();

    private Paint paint;

    private Path innerCircle;
    private Path outerCircle;
    private Path trangle1;
    private Path trangle2;
    private Path drawPath;

    private PathMeasure pathMeasure;

    private float mViewWidth;
    private float mViewHeight;

    private long duration = 3000;
    private ValueAnimator valueAnimator;

    private float distance;
    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private Animator.AnimatorListener animatorListener;

    private static final int CIRCLE_STATE = 0x000001;
    private static final int TRANGLE_STATE = 0x000002;
    private static final int FINISH_STATE = 0x000003;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CIRCLE_STATE, TRANGLE_STATE, FINISH_STATE})
    private @interface DrawState {
    }

    @DrawState
    private int currentState = CIRCLE_STATE;

    public GranzortView(Context context) {
        super(context);
        init();
    }

    public GranzortView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GranzortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GranzortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorPrimary));
        canvas.save();
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        switch (currentState) {
            case CIRCLE_STATE:
                drawPath.reset();
                pathMeasure.setPath(innerCircle, false);
                pathMeasure.getSegment(0, distance * pathMeasure.getLength(), drawPath, true);
                canvas.drawPath(drawPath, paint);
                drawPath.reset();
                pathMeasure.setPath(outerCircle, false);
                pathMeasure.getSegment(0, distance * pathMeasure.getLength(), drawPath, true);
                canvas.drawPath(drawPath, paint);
                break;
            case TRANGLE_STATE:
                canvas.drawPath(innerCircle, paint);
                canvas.drawPath(outerCircle, paint);
                drawPath.reset();
                pathMeasure.setPath(trangle1, false);
                float stopD = distance * pathMeasure.getLength();
                float startD = stopD - (0.5f - Math.abs(0.5f - distance)) * 200;
                pathMeasure.getSegment(startD, stopD, drawPath, true);
                canvas.drawPath(drawPath, paint);
                drawPath.reset();
                pathMeasure.setPath(trangle2, false);
                pathMeasure.getSegment(startD, stopD, drawPath, true);
                canvas.drawPath(drawPath, paint);
                break;
            case FINISH_STATE:
                canvas.drawPath(innerCircle, paint);
                canvas.drawPath(outerCircle, paint);
                drawPath.reset();
                pathMeasure.setPath(trangle1, false);
                pathMeasure.getSegment(0, distance * pathMeasure.getLength(), drawPath, true);
                canvas.drawPath(drawPath, paint);
                drawPath.reset();
                pathMeasure.setPath(trangle2, false);
                pathMeasure.getSegment(0, distance * pathMeasure.getLength(), drawPath, true);
                canvas.drawPath(drawPath, paint);
                break;
        }

        canvas.restore();

    }

    private void init() {

        initPaint();

        initPath();

        initAnimatorListener();

        initAnimator();

        currentState = CIRCLE_STATE;
        valueAnimator.start();

    }

    public void restartAnim() {
        if (!valueAnimator.isRunning()) {
            currentState = CIRCLE_STATE;
            valueAnimator.start();
        }
    }

    /**
     * 初始化属性动画触发器
     */
    private void initAnimator() {

        View view=new View(getContext());

        ObjectAnimator.ofFloat(view,view.ALPHA,0F,1F);

        valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(duration);
        valueAnimator.setRepeatCount(2);

        valueAnimator.addUpdateListener(animatorUpdateListener);

        valueAnimator.addListener(animatorListener);

    }

    /**
     * 初始化属性动画触发器监听
     */
    private void initAnimatorListener() {

        animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                distance = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        };

        animatorListener = new AnimatorListenerAdapter() {


            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                Log.d(TAG, "onAnimationRepeat: currentState=" + currentState);
                switch (currentState) {
                    case CIRCLE_STATE:
                        currentState = TRANGLE_STATE;
                        break;
                    case TRANGLE_STATE:
                        currentState = FINISH_STATE;
                        break;

                }
            }
        };

    }

    /**
     * 初始化几个轨迹
     */
    private void initPath() {
        innerCircle = new Path();
        outerCircle = new Path();
        trangle1 = new Path();
        trangle2 = new Path();
        drawPath = new Path();

        pathMeasure = new PathMeasure();

        //圆形轨迹
        RectF innerRect = new RectF(-220, -220, 220, 220);
        RectF outerRect = new RectF(-280, -280, 280, 280);
        innerCircle.addArc(innerRect, 150, -359.9f);
        outerCircle.addArc(outerRect, 60, -359.9f);

        //三角形轨迹，根据圆形0，1/3，2/3，位置点绘制
        pathMeasure.setPath(innerCircle, false);

        float[] pos = new float[2];
        pathMeasure.getPosTan(0, pos, null);
        trangle1.moveTo(pos[0], pos[1]);
        pathMeasure.getPosTan((1f / 3f) * pathMeasure.getLength(), pos, null);
        trangle1.lineTo(pos[0], pos[1]);
        pathMeasure.getPosTan((2f / 3f) * pathMeasure.getLength(), pos, null);
        trangle1.lineTo(pos[0], pos[1]);
        trangle1.close();

        Matrix matrix = new Matrix();
        matrix.postRotate(-180);
        trangle1.transform(matrix, trangle2);

    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.BEVEL);
        paint.setShadowLayer(15, 0, 0, Color.WHITE);

    }
}
