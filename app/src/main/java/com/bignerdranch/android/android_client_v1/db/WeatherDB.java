package com.bignerdranch.android.android_client_v1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bignerdranch.android.android_client_v1.model.AllCity;
import com.bignerdranch.android.android_client_v1.model.City;
import com.bignerdranch.android.android_client_v1.model.County;
import com.bignerdranch.android.android_client_v1.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/8/18.
 */
public class WeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "cool_weather";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static WeatherDB sWeatherDB;

    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     *
     * @param context
     */
    public WeatherDB(Context context) {
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取WeatherDB的实例
     *
     * @param context
     * @return
     */
    public synchronized static WeatherDB getInstance(Context context) {
        if (sWeatherDB == null) {
            sWeatherDB = new WeatherDB(context);
        }
        return sWeatherDB;
    }

    /**
     * 将Province实例存储到数据库
     *
     * @param province
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);

        }
    }

    /**
     * 从数据库读取全国所有的省份信息
     *
     * @return
     */
    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<>();
        Cursor cursor = db
                .query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将City实例存储到数据库
     *
     * @param city
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /**
     * 从数据库读取全国所有的城市信息
     *
     * @return
     */
    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<>();
        Cursor cursor = db
                .query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将County实例存储到数据库
     *
     * @param county
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("city_id", county.getCityId());
            db.insert("County", null, values);
        }
    }

    /**
     * 从数据库读取全国所有的城市信息
     *
     * @return
     */
    public List<County> loadCounties(int cityId) {
        List<County> list = new ArrayList<>();
        Cursor cursor = db
                .query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            } while (cursor.moveToNext());
        }
        return list;
    }

    //保存一个城市对象数据到数据库
    public void saveAllCity(AllCity city) {
        if (city != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("CITY_NAME_EN", city.getCity_name_en());
            contentValues.put("CITY_NAME_CH", city.getCity_name_ch());
            contentValues.put("CITY_CODE", city.getCity_code());
            db.insert("AllCITY", null, contentValues);
        }
    }

    //更新状态为已有数据
    public void updateDataState() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("state", 1);
        db.update("data_state", contentValues, null, null);
    }

    //检查是否是第一次安装（0-是 1-否）
    public int checkDataState() {
        int data_state = -1;
        Cursor cursor = db.query("data_state", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                data_state = cursor.getInt(cursor.getColumnIndex("STATE"));
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        Log.d("policy", data_state + " 0代表没All城市");

        return data_state;
    }


    //根据名称获取某一个或多个匹配的城市
    public AllCity loadCitiesByName(String name) {
        Log.d("test", "查询天气" + name.substring(0, 2));
        //先检查数据库处理对象是否获取
        List<AllCity> cities = new ArrayList<>();
        Cursor cursor = db.query("ALLCITY", null, "CITY_NAME_CH like ?", new String[]{name.substring(0, 2) + "%"}, null, null, "CITY_CODE");
        while (cursor.moveToNext()) {
            AllCity city = new AllCity();
            city.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            city.setCity_name_en(cursor.getString(cursor.getColumnIndex("CITY_NAME_EN")));
            city.setCity_name_ch(cursor.getString(cursor.getColumnIndex("CITY_NAME_CH")));
            city.setCity_code(cursor.getString(cursor.getColumnIndex("CITY_CODE")));
            cities.add(city);
        }
        if (cursor != null)
            cursor.close();
        Log.d("policy", "查到城市" + cities.get(0).getCity_name_ch());
        return cities.get(0);
    }
}
