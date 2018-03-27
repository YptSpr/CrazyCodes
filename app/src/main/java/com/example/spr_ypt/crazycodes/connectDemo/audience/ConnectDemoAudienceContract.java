package com.example.spr_ypt.crazycodes.connectDemo.audience;

import com.example.spr_ypt.crazycodes.connectDemo.BaseContract;

/**
 * Created by Spr_ypt on 2017/9/28.
 */

public class ConnectDemoAudienceContract extends BaseContract {

    interface ConnectDemoAudienceVipView extends BaseConnectVipView {
        public void audienceTodo1();

        public void audienceTodo2();

    }

    static abstract class ConnectDemoAudienceVipPresenter<T extends ConnectDemoAudienceVipView> extends BaseConnectVipPresenter<T> {
        public abstract void audienceChangeUi1();

        public abstract void audienceChangeUi2();
    }
}
