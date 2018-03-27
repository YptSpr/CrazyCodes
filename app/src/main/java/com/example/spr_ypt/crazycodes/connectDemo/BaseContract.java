package com.example.spr_ypt.crazycodes.connectDemo;

import com.example.spr_ypt.crazycodes.mvp.MvpBasePresenter;
import com.example.spr_ypt.crazycodes.mvp.MvpView;

/**
 * Created by Spr_ypt on 2017/9/28.
 */

public class BaseContract {

    protected interface BaseConnectVipView extends MvpView{

        public void allTodo1();

        public void allTodo2();

    }

    protected static abstract class BaseConnectVipPresenter<T extends BaseConnectVipView> extends MvpBasePresenter<T>{

        public abstract void allChangeUi1();

        public abstract void allChangeUi2();
    }
}
