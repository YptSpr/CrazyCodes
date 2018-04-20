package com.example.spr_ypt.crazycodes.rankedGame;

import android.content.Context;
import android.graphics.Typeface;

public class GlobalData {
    Typeface fontType = null;
    static GlobalData instance;
    static Context mCtx;

    public static void build(Context ctx){
        mCtx=ctx;
        instance=new GlobalData();
    }

    public static GlobalData getInstance() {
        return instance;
    }


    public Typeface getFontType() {
        if (null != fontType) {
        } else {
            try {
                fontType = Typeface.createFromAsset(mCtx.getAssets(), "molivehomeonline.ttf");
            } catch (Exception e) {
                e.printStackTrace();
                fontType = null;
            }
        }
        return fontType;
    }
}
