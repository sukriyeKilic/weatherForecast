package com.mobven.weatherforecast;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.mobven.weatherforecast.camera.CameraFragment;
import com.mobven.weatherforecast.camera.CapturePhotoActivity;
import com.mobven.weatherforecast.core.constant.Constant;
import com.mobven.weatherforecast.settings.SettingsActivity;
import com.mobven.weatherforecast.settings.SettingsFragment;
import com.mobven.weatherforecast.weather.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    TabLayout tabLayout;
    ViewPagerAdapter adapter;
    ViewPager viewPager;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.CAMERA_PERMISSION) {
            startCaptureActivity();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initUI();
    }

    private void initUI() {

        viewPager = findViewById(R.id.viewpager);

        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    if (checkIfAlreadyhavePermission(Manifest.permission.CAMERA)) {
                        startCaptureActivity();
                    } else {
                        requestPermission(Manifest.permission.CAMERA, Constant.CAMERA_PERMISSION);
                    }
                } else if (position == 2) {
                    startSettingsActivity();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WeatherFragment(), "Ana Sayfa");
        adapter.addFragment(new CameraFragment(), "Kamera");
        adapter.addFragment(new SettingsFragment(), "Ayarlar");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void startCaptureActivity() {
        Intent i = new Intent(appCompatActivity, CapturePhotoActivity.class);
        startActivityForResult(i, 1002);
    }

    private void startSettingsActivity() {
        Intent i = new Intent(appCompatActivity, SettingsActivity.class);
        startActivityForResult(i, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                viewPager.setCurrentItem(0);
            }
        } else if (requestCode == 1002) {
            if (resultCode == Activity.RESULT_OK) {
                viewPager.setCurrentItem(1);
            }
        }
    }



}
