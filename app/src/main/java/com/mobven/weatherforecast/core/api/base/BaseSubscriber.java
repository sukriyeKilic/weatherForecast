package com.mobven.weatherforecast.core.api.base;

import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<T> {


    private BaseCallback mBaseCallback;

    /**
     * If you use this constructor you must override onFail method
     */
    public BaseSubscriber() {

    }

    /**
     * Use this constructor if don't want to override onFail case
     *
     * @param baseCallback Pass baseCallback interface to handle onFail in ApiCallback
     */

    protected BaseSubscriber(BaseCallback baseCallback) {
        mBaseCallback = baseCallback;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onFail();
    }

    @Override
    public void onNext(T t) {
        hideLoading();
        onSuccess(t);
    }

    public abstract void onSuccess(T response);


    private void onFail() {
        if (mBaseCallback != null) {
            mBaseCallback.onError();
            mBaseCallback.onHideLoading();
        }
    }

    private void hideLoading() {
        if (mBaseCallback != null) {
            mBaseCallback.onHideLoading();
        }
    }
}
