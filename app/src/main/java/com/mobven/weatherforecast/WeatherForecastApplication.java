package com.mobven.weatherforecast;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.mobven.weatherforecast.core.dagger.AppModule;
import com.mobven.weatherforecast.core.dagger.DaggerNetComponent;
import com.mobven.weatherforecast.core.dagger.NetComponent;
import com.mobven.weatherforecast.core.dagger.NetModule;


public class WeatherForecastApplication extends MultiDexApplication {

    public static Context mContext;
    private NetComponent mNetComponent;

    public static String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initDagger();
    }

    public void initDagger() {
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL, this))
                .build();
    }

    public static Context getContext() {
        return mContext;
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
