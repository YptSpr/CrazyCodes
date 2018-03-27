package com.example.spr_ypt.crazycodes.connectDemo.anchor;

import com.example.spr_ypt.crazycodes.connectDemo.BaseContract;

/**
 * Created by Spr_ypt on 2017/9/28.
 */

public class ConnectDemoAnchorContract extends BaseContract {

    interface ConnectDemoAnchorVipView extends BaseConnectVipView {
        public void anchorTodo1();

        public void anchorTodo2();

    }

    static abstract class ConnectDemoAnchorVipPresenter<T extends ConnectDemoAnchorVipView> extends BaseConnectVipPresenter<T> {
        public abstract void anchorChangeUi1();

        public abstract void anchorChangeUi2();
    }
}
