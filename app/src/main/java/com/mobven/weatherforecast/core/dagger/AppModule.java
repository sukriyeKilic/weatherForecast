package com.mobven.weatherforecast.core.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }


    @Singleton
    @Provides
    UserHelper provideUserHelper() {
        return new UserHelper();
    }

}