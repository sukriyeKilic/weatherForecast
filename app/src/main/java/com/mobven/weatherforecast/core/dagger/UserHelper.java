package com.mobven.weatherforecast.core.dagger;

public class UserHelper {

    double latitutude = 0.0;
    double longitude = 0.0;

    public double getLatitutude() {
        return latitutude;
    }

    public void setLatitutude(double latitutude) {
        this.latitutude = latitutude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
