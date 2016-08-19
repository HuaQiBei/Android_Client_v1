package com.bignerdranch.android.android_client_v1.util;

import android.text.TextUtils;
import android.util.Log;

import com.bignerdranch.android.android_client_v1.model.*;
import com.bignerdranch.android.android_client_v1.model.WeatherDB;

/**
 * Created by DELL on 2016/8/18.
 */
public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     *
     * @param weatherDB
     * @return
     */
    public synchronized static boolean handleProvincesResponse(WeatherDB weatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p :
                        allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    //将解析出来的数据存储到Province表
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的市级数据
     *
     * @param weatherDB
     * @return
     */
    public synchronized static boolean handleCitiesResponse(WeatherDB weatherDB, String response,
                                                           int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCitys = response.split(",");
            if (allCitys != null && allCitys.length > 0) {
                for (String p :
                        allCitys) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    //将解析出来的数据存储到City表
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的县级数据
     *
     * @param weatherDB
     * @return
     */
    public synchronized static boolean handleCountysResponse(WeatherDB weatherDB, String response,
                                                             int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCountys = response.split(",");
            if (allCountys != null && allCountys.length > 0) {
                for (String p :
                        allCountys) {
                    String[] array = p.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    //将解析出来的数据存储到County表
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
