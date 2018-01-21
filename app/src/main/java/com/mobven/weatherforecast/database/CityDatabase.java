package com.mobven.weatherforecast.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobven.weatherforecast.core.api.model.City;

import java.util.ArrayList;
import java.util.List;

public class CityDatabase extends SQLiteOpenHelper {

    private static final String TABLE_CITY = "city";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_NAME = "cityName";
    private static final String KEY_PLATE = "cityPlate";
    private static final String KEY_CITY_CODE = "cityCode";
    private static final String KEY_STATUS = "status";
    private static final String KEY_ID = "id";

    // Database Name
    private static String DATABASE_NAME = "CityDatabase";

    public CityDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGES);

    }

    private static final String CREATE_TABLE_IMAGES = "CREATE TABLE " + TABLE_CITY + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_PLATE + " TEXT," + KEY_CITY_CODE
            + " TEXT," + KEY_STATUS + " INTEGER" + " )";

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    public void addCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, city.getCityName());
        values.put(KEY_PLATE, city.getPlateNum());
        values.put(KEY_CITY_CODE, city.getCityCode());
        values.put(KEY_STATUS, city.getSelect());
        db.insert(TABLE_CITY, null, values);
        db.close();
    }

    public List<City> getAllCity() {
        List<City> cityList = new ArrayList<City>();
        String selectQuery = "SELECT  * FROM " + TABLE_CITY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setCityName(cursor.getString(1));
                city.setPlateNum(cursor.getString(2));
                city.setCityCode(cursor.getString(3));
                city.setSelect(cursor.getInt(4));
                cityList.add(city);
            } while (cursor.moveToNext());
        }

        db.close();
        return cityList;
    }

    public void updateCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, city.getCityName());
        values.put(KEY_PLATE, city.getPlateNum());
        values.put(KEY_CITY_CODE, city.getCityCode());
        values.put(KEY_STATUS, city.getSelect());

        db.update(TABLE_CITY, values, KEY_PLATE + "=?",
                new String[]{city.getPlateNum() + ""});
        db.close();

    }
}