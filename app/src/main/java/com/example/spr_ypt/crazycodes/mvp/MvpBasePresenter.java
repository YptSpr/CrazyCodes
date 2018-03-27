package com.example.spr_ypt.crazycodes.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by Spr_ypt on 2017/9/20.
 */

public class MvpBasePresenter<T extends MvpView> implements MvpPresenter<T> {
    private WeakReference<T> reference;

    @Override
    public void attachView(T view) {
        reference = new WeakReference<T>(view);

    }

    @Override
    public void detachView(boolean retainInstance) {
        if (null != reference) {
            reference.clear();
            reference = null;
        }
    }

    @Override
    public T getView() {
        return null!=reference?reference.get():null;
    }

    @Override
    public boolean isViewAttached() {
        return reference != null && reference.get() != null;
    }
}
