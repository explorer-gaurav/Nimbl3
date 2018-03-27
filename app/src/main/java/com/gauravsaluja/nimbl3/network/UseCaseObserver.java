package com.gauravsaluja.nimbl3.network;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class UseCaseObserver<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {
        // no operation by default
    }

    @Override
    public void onError(Throwable e) {
        // no operation by default
    }

    @Override
    public void onComplete() {
        // no operation by default
    }
}