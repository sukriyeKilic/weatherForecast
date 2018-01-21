package com.mobven.weatherforecast.core.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherListWrapper {

    @SerializedName("list")
    @Expose
    private List<WeatherWrapper> list = null;

    public java.util.List<WeatherWrapper> getList() {
        return list;
    }

    public void setList(java.util.List<WeatherWrapper> list) {
        this.list = list;
    }

}