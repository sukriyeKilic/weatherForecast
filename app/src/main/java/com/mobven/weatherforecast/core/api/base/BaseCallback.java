package com.mobven.weatherforecast.core.api.base;


public interface BaseCallback {

    void onShowLoading();

    void onHideLoading();

    void onError();

}