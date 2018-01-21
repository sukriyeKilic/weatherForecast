package com.mobven.weatherforecast.core.api.model;


public class City {

    String plateNum;
    String cityName;
    String cityCode;
    int select;

    public City() {
    }

    public City(String plateNum, String cityName, String cityCode, int isSelect) {
        this.plateNum = plateNum;
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.select = isSelect;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }
}
