package com.example.spr_ypt.crazycodes.mvp;

/**
 * Created by Spr_ypt on 2017/9/20.
 */

public interface MvpPresenter<T extends MvpView> {
    void attachView(T view);

    void detachView(boolean retainInstance);

    T getView();

    boolean isViewAttached();

}
