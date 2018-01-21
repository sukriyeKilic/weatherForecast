package com.mobven.weatherforecast.settings;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import com.mobven.weatherforecast.BaseActivity;
import com.mobven.weatherforecast.R;
import com.mobven.weatherforecast.core.api.model.City;
import com.mobven.weatherforecast.database.CityDatabase;

import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends BaseActivity implements CityAdapter.CityClickListener, View.OnClickListener {

    RecyclerView list_city;
    List<City> cityList = new ArrayList();
    List<City> selectedList = new ArrayList();
    CityDatabase dbCity;
    ProgressBar progressBarBattery;
    boolean isCheckedLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        dbCity = new CityDatabase(appCompatActivity);
        initUI();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ayarlar");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        list_city = findViewById(R.id.list_city);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(appCompatActivity);
        list_city.setLayoutManager(layoutManager);

        cityList = dbCity.getAllCity();
        list_city.setAdapter(new CityAdapter(appCompatActivity, cityList, this));

        SwitchCompat switchBtnTerms = findViewById(R.id.switch_wifi);
        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        switchBtnTerms.setChecked(wifiManager.isWifiEnabled());
        switchBtnTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                wifiManager.setWifiEnabled(b);
            }
        });

        CheckBox checkbox_location = findViewById(R.id.checkbox_location);

        isCheckedLocation = sharedPreference.isLocationActive();
        checkbox_location.setChecked(isCheckedLocation);

        checkbox_location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckedLocation = isChecked;
            }
        });

        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);


        progressBarBattery = findViewById(R.id.progress);
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void save(){
        for (City c : selectedList) {
            dbCity.updateCity(c);
        }
        sharedPreference.setLocation(isCheckedLocation);
        onBackPressed();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void finishActivity() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            progressBarBattery.setProgress(level);
        }
    };

    @Override
    public void onCityClick(City city) {
        selectedList.add(city);

    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatInfoReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                save();
                break;
        }
    }
}
