package com.mobven.weatherforecast.core.dagger;


import com.mobven.weatherforecast.BaseActivity;
import com.mobven.weatherforecast.core.api.base.BasePresenter;
import com.mobven.weatherforecast.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})

public interface NetComponent {

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(BasePresenter basePresenter);


}