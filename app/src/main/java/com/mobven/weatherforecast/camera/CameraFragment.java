package com.mobven.weatherforecast.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobven.weatherforecast.BaseFragment;
import com.mobven.weatherforecast.R;
import com.mobven.weatherforecast.helper.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;


public class CameraFragment extends BaseFragment {

    ViewPager cameraPager;
    CameraPagerAdapter cameraPagerAdapter;
    CirclePageIndicator circlePageIndicator;
    View view;
    List<String> pathList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_camera,container,false);
        initUI();
        return view;
    }

    private void initUI(){
        cameraPager = view.findViewById(R.id.cameraPager);
        circlePageIndicator = view.findViewById(R.id.circlePageIndicator);
    }

    private void setPager(){
        pathList = mActivity.sharedPreference.getPathList();
        if(pathList != null) {
            cameraPagerAdapter = new CameraPagerAdapter(mActivity, pathList);
            cameraPager.setAdapter(cameraPagerAdapter);
            circlePageIndicator.setViewPager(cameraPager);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            setPager();
        }
    }
}
