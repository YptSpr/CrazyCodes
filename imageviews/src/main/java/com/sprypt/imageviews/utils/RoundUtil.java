package com.sprypt.imageviews.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by Spr_ypt on 2017/9/11.
 */

public class RoundUtil {

    private View target;

    private Path roundCornerPath;
    private RectF roundRectF;
    private Paint roundCornerPaint;

    public RoundUtil(View target){
        this.target=target;
    }

    public void makeRoundCorner(Canvas canvas,float roundCorner){
        if (roundCornerPath == null) {
            roundCornerPath = new Path();
            roundCornerPath.setFillType(Path.FillType.INVERSE_WINDING);
        }
        if (roundRectF == null) {
            roundRectF = new RectF(0, target.getScrollY(), target.getWidth(), target.getScrollY() + target.getHeight());
        } else {
            roundRectF.set(0, target.getScrollY(), target.getWidth(), target.getScrollY() + target.getHeight());
        }
        roundCornerPath.reset();
        float[] radii=new float[]{roundCorner,roundCorner,roundCorner,roundCorner,roundCorner,roundCorner,roundCorner,roundCorner};
        roundCornerPath.addRoundRect(roundRectF, radii, Path.Direction.CW);
        if (roundCornerPaint == null) {
            roundCornerPaint = createPorterDuffClearPaint();
        }
        canvas.drawPath(roundCornerPath, roundCornerPaint);
    }

    private Paint createPorterDuffClearPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        return paint;
    }





}
