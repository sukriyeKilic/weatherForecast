package com.mobven.weatherforecast.weather;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.mobven.weatherforecast.BaseFragment;
import com.mobven.weatherforecast.R;
import com.mobven.weatherforecast.core.api.model.City;
import com.mobven.weatherforecast.core.api.model.WeatherListWrapper;
import com.mobven.weatherforecast.core.api.model.WeatherWrapper;
import com.mobven.weatherforecast.core.constant.CityConstant;
import com.mobven.weatherforecast.database.CityDatabase;
import com.mobven.weatherforecast.helper.AppAlertDialog;
import com.mobven.weatherforecast.helper.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.mobven.weatherforecast.core.constant.Constant.REQUEST_CHECK_SETTINGS;


public class WeatherFragment extends BaseFragment implements WeatherPresenter.WeatherCallBack, GoogleApiClient.ConnectionCallbacks, LocationListener {

    ViewPager weatherPager;
    CirclePageIndicator circlePageIndicator;
    WeatherPagerAdapter weatherPagerAdapter;
    View view;

    WeatherPresenter mWeatherPresenter;
    CityDatabase dbCity;
    List<City> selectedCityList = new ArrayList<>();
    List<WeatherWrapper> weathersList = new ArrayList<>();

    //location
    LocationRequest mLocationRequest;
    GoogleApiClient mClient;
    Location mLastLocation;
    LocationSettingsRequest.Builder builder;
    static final int LOCATION_PERMISSION = 100;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setLocationSettings();
                }
                return;
            }

        }
    }

    private void setLocationSettings() {
        mClient = new GoogleApiClient.Builder(mActivity).addApi(LocationServices.API).addConnectionCallbacks(this).build();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        mClient.connect();

        checkLocationSettings();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherPresenter = new WeatherPresenter(this);
        dbCity = new CityDatabase(mActivity);
    }


    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        weatherPager = view.findViewById(R.id.weatherPager);
        circlePageIndicator = view.findViewById(R.id.circlePageIndicator);

        if (dbCity.getAllCity() == null || dbCity.getAllCity().size() == 0) {
            for (CityConstant constant : CityConstant.values()) {
                dbCity.addCity(new City(constant.getPlateNum(), constant.getCityName(), constant.getCityCode(), 0));
            }
            selectedCityList.add(new City("35", "İzmir", "311046", 0));
        } else {
            selectedCityList.clear();
            for (City c : dbCity.getAllCity()) {
                if (c.getSelect() == 1)
                    selectedCityList.add(c);
            }
        }

    }

    public void setAdapter() {
        selectedCityList.clear();
        for (City c : dbCity.getAllCity()) {
            if (c.getSelect() == 1)
                selectedCityList.add(c);
        }
        mWeatherPresenter.getWeatherCityList(selectedCityList);
        if (mActivity.sharedPreference.isLocationActive()) {
            checkandSetLocation();
        }
    }

    protected void checkandSetLocation() {

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
        } else {
            setLocationSettings();

        }
    }


    private void checkLocationSettings() {

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Handler locationHandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                try {
                                    status.startResolutionForResult(mActivity, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e) {

                                }
                            }
                        };
                        AppAlertDialog.showMessage(mActivity, null, getString(R.string.location), true, "Tamam", "İptal", locationHandler, null);
                        break;
                    case LocationSettingsStatusCodes.SUCCESS:
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mClient);
                        getWeatherByLocation();
                        break;

                }
            }
        });
    }

    private void getWeatherByLocation() {
        if (mLastLocation != null) {
            mWeatherPresenter.getWeatherByCoordinates(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    }


    @Override
    public void onWeatherForecast(WeatherListWrapper weatherListWrapper) {
        weathersList = weatherListWrapper.getList();
        weatherPagerAdapter = new WeatherPagerAdapter(mActivity, weathersList);
        weatherPager.setAdapter(weatherPagerAdapter);
        circlePageIndicator.setViewPager(weatherPager);
    }

    @Override
    public void onWeatherForecastByCoordinate(WeatherWrapper weatherWrapper) {
        weathersList.add(0, weatherWrapper);
        weatherPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        mClient.disconnect();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        setLocationSettings();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    default:
                        break;
                }
                break;
        }
    }
}
