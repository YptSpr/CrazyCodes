package com.example.spr_ypt.crazycodes.rankedGame;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.spr_ypt.crazycodes.R;

public class RankedGameScoreView extends RelativeLayout {
    public RankedGameScoreView(Context context) {
        super(context);
        init();
    }

    public RankedGameScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RankedGameScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RankedGameScoreView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hani_view_ranked_game_score,this);
    }
}
