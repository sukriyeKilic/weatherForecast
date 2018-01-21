package com.mobven.weatherforecast.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobven.weatherforecast.BuildConfig;

import java.util.ArrayList;
import java.util.List;


public class SharedPreference {

    private static SharedPreference sharedPreference;

    SharedPreferences preference;
    Editor editor;
    Context context;

    public SharedPreference(Context context) {
        this.context = context;
        preference = context.getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
        editor = preference.edit();
    }

    public static SharedPreference getInstance(Context context) {
        if (sharedPreference == null)
            sharedPreference = new SharedPreference(context);
        return sharedPreference;
    }



    public void setPathList(List<String> pathList){
        Gson gson = new Gson();
        String jsonText = gson.toJson(pathList);
        editor.putString("pathList", jsonText);
        editor.commit();
    }

    public List<String> getPathList(){
        Gson gson = new Gson();
        String jsonText = preference.getString("pathList", null);
        ArrayList<String> pathList = gson.fromJson(jsonText, new TypeToken<List<String>>(){}.getType());
        return pathList;
    }

    public void setLocation(boolean isLocationActive) {
        editor.putBoolean("isLocationActive", isLocationActive).commit();
    }

    public boolean isLocationActive() {
        return preference.getBoolean("isLocationActive", false);
    }

}