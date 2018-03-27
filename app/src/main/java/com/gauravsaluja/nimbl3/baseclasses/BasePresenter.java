package com.gauravsaluja.nimbl3.baseclasses;

import android.content.Context;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public abstract class BasePresenter<V extends BaseView> {
    protected V view;
    protected Context context;

    public V getView() {
        return view;
    }

    public Context getContext() {
        return context;
    }

    public abstract void start();

    public void stop() {
    }

    public void setView(V view) {
        this.view = view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void onDestroy() {
        view = null;
    }
}