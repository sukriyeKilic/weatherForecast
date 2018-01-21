package com.mobven.weatherforecast.weather;

import com.mobven.weatherforecast.BuildConfig;
import com.mobven.weatherforecast.core.api.base.BaseCallback;
import com.mobven.weatherforecast.core.api.base.BasePresenter;
import com.mobven.weatherforecast.core.api.base.BaseSubscriber;
import com.mobven.weatherforecast.core.api.model.City;
import com.mobven.weatherforecast.core.api.model.WeatherListWrapper;
import com.mobven.weatherforecast.core.api.model.WeatherWrapper;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class WeatherPresenter extends BasePresenter {

    public WeatherCallBack mCallback;

    public WeatherPresenter(WeatherCallBack weatherCallBack) {
        super(weatherCallBack);
        mCallback = weatherCallBack;
    }

    public void getWeatherCityList(List<City> cityList) {

        mCallback.onShowLoading();

        StringBuilder cityListString = new StringBuilder();
        for (City city : cityList) {
            if (!(cityList.indexOf(city) == cityList.size() - 1))
                cityListString.append(city.getCityCode() + ",");
            else
                cityListString.append(city.getCityCode());
        }

        Subscription
                subscription = apiInterface.getWeatherList(cityListString.toString(), BuildConfig.WEATHER_API_KEY, "tr", "metric")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<WeatherListWrapper>(mCallback) {
                    @Override
                    public void onSuccess(WeatherListWrapper response) {
                        mCallback.onWeatherForecast(response);
                    }
                });
        subscriptions.add(subscription);
    }

    public void getWeatherByCoordinates(double lat,double lon){
        mCallback.onShowLoading();

        Subscription
                subscription = apiInterface.getWeatherByCoordinate(String.valueOf(lat),String.valueOf(lon), BuildConfig.WEATHER_API_KEY, "tr", "metric")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<WeatherWrapper>(mCallback) {
                    @Override
                    public void onSuccess(WeatherWrapper response) {
                        mCallback.onWeatherForecastByCoordinate(response);
                    }
                });
        subscriptions.add(subscription);
    }

    public interface WeatherCallBack extends BaseCallback {

        void onWeatherForecast(WeatherListWrapper weatherWrapper);

        void onWeatherForecastByCoordinate(WeatherWrapper weatherWrapper);

    }


}
