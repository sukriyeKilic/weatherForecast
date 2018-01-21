package com.mobven.weatherforecast.core.api.base;

import com.mobven.weatherforecast.WeatherForecastApplication;
import com.mobven.weatherforecast.core.api.ApiInterface;
import com.mobven.weatherforecast.core.dagger.UserHelper;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

public class BasePresenter {

    private BaseCallback mBaseCallback;
    public CompositeSubscription subscriptions;

    @Inject
    protected UserHelper userHelper;
    @Inject
    protected ApiInterface apiInterface;

    public BasePresenter(BaseCallback baseCallback) {
        mBaseCallback = baseCallback;
        this.subscriptions = new CompositeSubscription();
        ((WeatherForecastApplication) WeatherForecastApplication.getContext()).getNetComponent().inject(this);
    }

    protected void handleError() {
        mBaseCallback.onHideLoading();
    }


    public void onStop() {
        subscriptions.unsubscribe();
    }

}