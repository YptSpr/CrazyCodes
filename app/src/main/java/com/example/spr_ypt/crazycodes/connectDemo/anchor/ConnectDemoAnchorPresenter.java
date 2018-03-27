package com.example.spr_ypt.crazycodes.connectDemo.anchor;

/**
 * Created by Spr_ypt on 2017/9/28.
 */

public class ConnectDemoAnchorPresenter extends ConnectDemoAnchorContract.ConnectDemoAnchorVipPresenter<ConnectDemoAnchorController> {


    public ConnectDemoAnchorPresenter() {
    }

    @Override
    public void anchorChangeUi1() {
        getView().anchorTodo1();
    }

    @Override
    public void anchorChangeUi2() {
        //TODO model method

    }


    @Override
    public void allChangeUi1() {
        getView().allTodo1();

    }

    @Override
    public void allChangeUi2() {
        //TODO model method
    }
}
