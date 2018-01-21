package com.mobven.weatherforecast.settings;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobven.weatherforecast.R;
import com.mobven.weatherforecast.core.api.model.City;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity mActivity;
    List<City> mCityList;
    CityClickListener mCityClickListener;

    public CityAdapter(Activity mActivity, List<City> mNotificationList,CityClickListener cityClickListener) {
        this.mActivity = mActivity;
        this.mCityList = mNotificationList;
        this.mCityClickListener = cityClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final City city = mCityList.get(position);
        viewHolder.txt_name.setText(city.getCityName());
        if(city.getSelect() == 1) {
            viewHolder.img_select.setVisibility(View.VISIBLE);
            viewHolder.layout_holder.setBackgroundColor(mActivity.getColor(R.color.selected_item));
        } else {
            viewHolder.img_select.setVisibility(View.GONE);
            viewHolder.layout_holder.setBackgroundColor(mActivity.getColor(R.color.white));
        }

        viewHolder.layout_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(city.getSelect() == 0){
                    city.setSelect(1);
                }else{
                    city.setSelect(0);
                }

                notifyDataSetChanged();
                mCityClickListener.onCityClick(city);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name;
        ImageView img_select;
        LinearLayout layout_holder;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
            img_select = itemLayoutView.findViewById(R.id.img_select);
            layout_holder = itemLayoutView.findViewById(R.id.layout_holder);
        }
    }

    public interface CityClickListener {
        public void onCityClick(City city);
    }
    
}
