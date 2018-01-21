package com.mobven.weatherforecast.camera;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mobven.weatherforecast.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CameraPagerAdapter extends PagerAdapter {

    Activity mActivity;
    List<String> mPathList;

    public CameraPagerAdapter(Activity activity, List<String> pathList) {
        this.mActivity = activity;
        this.mPathList = pathList;
    }


    @Override
    public int getCount() {
        return mPathList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.photo_pager_item, null);

        ImageView img_photo = view.findViewById(R.id.img_photo);


        String path = mPathList.get(position);
        Picasso.with(mActivity).load("file:////" + path).into(img_photo);

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
