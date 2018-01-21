package com.mobven.weatherforecast;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mobven.weatherforecast.core.api.ApiInterface;
import com.mobven.weatherforecast.core.api.base.BaseCallback;
import com.mobven.weatherforecast.core.dagger.UserHelper;

import javax.inject.Inject;


public class BaseFragment extends Fragment implements BaseCallback {

    public BaseActivity mActivity;

    @Inject
    protected UserHelper userHelper;
    @Inject
    protected ApiInterface apiInterface;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((WeatherForecastApplication) getActivity().getApplication()).getNetComponent().inject(this);
        mActivity = (BaseActivity) getActivity();
    }


    @Override
    public void onShowLoading() {
        mActivity.showDialog();
    }

    @Override
    public void onHideLoading() {
        mActivity.dismissDialog();
    }

    @Override
    public void onError() {

    }


}
