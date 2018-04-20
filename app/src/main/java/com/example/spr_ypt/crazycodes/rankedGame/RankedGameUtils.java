package com.example.spr_ypt.crazycodes.rankedGame;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RankedGameUtils {
    public static int parseColor(String rgb) {
        int color = Color.RED;
        try {
            color = Color.parseColor(rgb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }

    public static float getRate(float cur, float tol) {
        if (cur <= 0 || tol <= 0) return 0f;
        if (cur >= tol) return 1f;
        return cur / tol;
    }

    public static void setViewsVisible(int visible, List<View>... lists) {
        if (null != lists && lists.length > 0) {
            for (List<View> list : lists) {
                if (null != list && list.size() > 0) {
                    for (View v : list) {
                        if (v.getVisibility() != visible) {
                            v.setVisibility(visible);
                        }
                    }
                }
            }
        }
    }

    /**
     * s
     * 计算特有的views
     *
     * @param own      我的views
     * @param contrast 对比项
     * @return
     */
    public static List<View> getOwnVies(List<View> own, List<View> contrast) {
        List<View> spc = new ArrayList<>();
        if (null != own && own.size() > 0) {
            for (View v : own) {
                if (null != v && null != contrast && contrast.size() > 0 && !contrast.contains(v)) {
                    spc.add(v);
                }
            }
        }
        return spc;
    }


    public static void resetTxtTimer(long time, final TextView tv, final String format) {
        tv.setText(String.format(format, 520));
    }


    public static String getScoreTxt(int score) {
        if (score > 1000000) {
            return score / 10000 + "万";
        } else {
            return score + "";
        }
    }

    public static String getEggsScoreTxt(int score) {
        if (score > 100000) {
            return score / 10000 + "万";
        } else {
            return score + "";
        }
    }
}
