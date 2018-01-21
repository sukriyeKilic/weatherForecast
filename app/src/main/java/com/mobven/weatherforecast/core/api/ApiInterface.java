package com.mobven.weatherforecast.core.api;

import com.mobven.weatherforecast.core.api.model.WeatherListWrapper;
import com.mobven.weatherforecast.core.api.model.WeatherWrapper;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface ApiInterface {

    @GET("group")
    Observable<WeatherListWrapper> getWeatherList(@Query("id") String cityName, @Query("appid") String appid, @Query("lang") String language, @Query("units") String units);

    @GET("weather")
    Observable<WeatherWrapper> getWeatherByCoordinate(@Query("lat") String lat, @Query("lon") String lon,@Query("appid") String appid, @Query("lang") String language, @Query("units") String units);

}

