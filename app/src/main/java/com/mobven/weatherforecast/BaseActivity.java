package com.mobven.weatherforecast;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.mobven.weatherforecast.core.api.ApiInterface;
import com.mobven.weatherforecast.core.api.base.BaseCallback;
import com.mobven.weatherforecast.core.dagger.UserHelper;
import com.mobven.weatherforecast.utils.SharedPreference;

import javax.inject.Inject;


public class BaseActivity extends AppCompatActivity implements BaseCallback {

    public AppCompatActivity appCompatActivity;
    public SharedPreference sharedPreference;

    @Inject
    protected ApiInterface apiInterface;
    @Inject
    protected UserHelper userHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((WeatherForecastApplication) getApplication()).getNetComponent().inject(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        appCompatActivity = this;
        sharedPreference = SharedPreference.getInstance(appCompatActivity);
    }

    public boolean checkIfAlreadyhavePermission(String permissionType) {
        int result = ActivityCompat.checkSelfPermission(appCompatActivity, permissionType);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPermission(String permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onShowLoading() {
        if (!appCompatActivity.isFinishing()) {
            showDialog();
        }
    }

    @Override
    public void onHideLoading() {
        dismissDialog();
    }

    @Override
    public void onError() {

    }

    Dialog dialog;

    public void showDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        appCompatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog = ProgressDialog.show(appCompatActivity, null, getString(R.string.loading));
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                if (!dialog.isShowing())
                    dialog.show();
            }
        });
    }

    public void dismissDialog() {
        appCompatActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    public void setMainPage(){

    }
}
