package com.example.spr_ypt.crazycodes.connectDemo.anchor;

/**
 * Created by Spr_ypt on 2017/9/28.
 */

public class ConnectDemoAnchorController implements ConnectDemoAnchorContract.ConnectDemoAnchorVipView {

    ConnectDemoAnchorPresenter presenter;

    ConnectDemoAnchorViewManager manager;


    public ConnectDemoAnchorController() {
        presenter = new ConnectDemoAnchorPresenter();
        presenter.attachView(this);

        manager = new ConnectDemoAnchorViewManager();
    }

    @Override
    public void anchorTodo1() {
        manager.changeUi1();
    }

    @Override
    public void anchorTodo2() {
        presenter.anchorChangeUi2();
    }

    @Override
    public void allTodo1() {
        manager.changeUi2();
    }

    @Override
    public void allTodo2() {
        presenter.allChangeUi2();
    }
}
