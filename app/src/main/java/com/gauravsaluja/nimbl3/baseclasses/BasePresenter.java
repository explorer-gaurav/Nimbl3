package com.gauravsaluja.nimbl3.baseclasses;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public abstract class BasePresenter<V extends BaseView> {
    protected V view;

    public V getView() {
        return view;
    }

    public abstract void start();

    public void stop() {
    }

    public void setView(V view) {
        this.view = view;
    }

    public void onDestroy() {
        view = null;
    }
}