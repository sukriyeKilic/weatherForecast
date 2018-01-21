package com.mobven.weatherforecast.weather;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobven.weatherforecast.R;
import com.mobven.weatherforecast.core.api.model.WeatherWrapper;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class WeatherPagerAdapter extends PagerAdapter {

    Activity mActivity;
    List<WeatherWrapper> mWeatherWrapperList;

    public WeatherPagerAdapter(Activity activity, List<WeatherWrapper> weatherWrapperList) {
        this.mActivity = activity;
        this.mWeatherWrapperList = weatherWrapperList;
    }


    @Override
    public int getCount() {
        return mWeatherWrapperList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.weather_pager_item, null);

        TextView txt_temperature = view.findViewById(R.id.txt_temperature);
        TextView txt_description = view.findViewById(R.id.txt_description);
        TextView txt_max_temperature = view.findViewById(R.id.txt_max_temperature);
        TextView txt_min_temperature = view.findViewById(R.id.txt_min_temperature);

        ImageView img_status = view.findViewById(R.id.img_status);


        WeatherWrapper weatherWrapper = mWeatherWrapperList.get(position);
        weatherWrapper.getWeather().get(0).getIcon();
        Picasso.with(mActivity).load("http://openweathermap.org/img/w/" + weatherWrapper.getWeather().get(0).getIcon() + ".png").resize(120,120).into(img_status);

        txt_description.setText(weatherWrapper.getName() + " / " + weatherWrapper.getWeather().get(0).getDescription());
        String max = (int)weatherWrapper.getMain().getTempMax() + " \u2103";
        String min = (int)weatherWrapper.getMain().getTempMin() + " \u2103";

        txt_max_temperature.setText(max);
        txt_min_temperature.setText(min);
        txt_temperature.setText(min + " / " + max);

        container.addView(view);

        return view;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
