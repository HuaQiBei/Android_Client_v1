package com.bignerdranch.android.android_client_v1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.bignerdranch.android.android_client_v1.model.*;
import com.bignerdranch.android.android_client_v1.db.WeatherDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    public synchronized static boolean handleCountiesResponse(WeatherDB weatherDB, String response,
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

//    /**
//     * 解析服务器返回的JSON数据，并将解析出的数据存储到本地。
//     *
//     * @param context
//     * @param response
//     */
//    public static void handleWeatherResponse(Context context, String response) {
//        try {
//            Log.d("life", "JSON");
//            JSONObject jsonObject = new JSONObject(response);
//            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
//            String cityName = weatherInfo.getString("city");
//            String weatherCode = weatherInfo.getString("cityid");
//            String temp1 = weatherInfo.getString("temp1");
//            String temp2 = weatherInfo.getString("temp2");
//            String weatherDesp = weatherInfo.getString("weather");
//            String publishTime = weatherInfo.getString("ptime");
//            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
//                    weatherDesp, publishTime);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 将服务器返回的所有天气信息存储到SharedPreferences文件中
     */
    public static void saveWeatherInfo(Context context, String cityName,
                                       String weatherCode, String temp1, String temp2, String weatherDesp,
                                       String publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        //editor.commit();
        editor.apply();
    }

    //处理从服务器返回的天气信息（可以自己注册和风天气查看具体的数据）
    //这里的数据是JSON，而且返回的JSON数据相对比较复杂
    //在util包下面有一个北京市天气数据的样例
    //这个JSON里面包含了非常多的天气相关的数据，但我们这里只获取基础信息就行了，有兴趣的可以继续扩展
    public synchronized static boolean handleWeatherResponse(Context context, String response) {//SharedPreferences.Editor editor
        if (!TextUtils.isEmpty(response)) {
            try {
                //先把JSON数据加载成数组，因为根部HeWeather data service 3.0后面是[符号，说明是以数组形式存放，只是这个数组里面只有一个元素
                JSONArray jsonArray = new JSONObject(response).getJSONArray("HeWeather data service 3.0");
                Log.d("life", jsonArray.toString());
                //那么既然知道这个数组里面只有一个元素，所以我们直接取出第一个元素为JSONObject
                JSONObject weather_info_all = jsonArray.getJSONObject(0);
                //首先，我们看到，城市名称和数据更新的时间是在basic下面，所以可以直接获取
                JSONObject weather_info_basic = weather_info_all.getJSONObject("basic");
                /*"basic": {
                    "city": "北京",
                    "cnty": "中国",
                    "id": "CN101010100",
                    "lat": "39.904000",
                    "lon": "116.391000",
                    "update":
                    {
                       "loc": "2016-06-30 08:51",
                       "utc": "2016-06-30 00:51"
                    }
                },*/

                //我们发现，有city和update，其中，city可以直接通过名称获取到信息
                String cityName = weather_info_basic.getString("city");
                Log.d("life", "城市名" + cityName);
                String weatherCode = weather_info_basic.getString("id");
                Log.d("life", "天气码" + weatherCode);
                //但是，更新的时间是不能获取的，因为这里update后面是｛｝，表明这是一个对象
                //所以先根据名称获取这个对象
                JSONObject weather_info_basic_update = weather_info_basic.getJSONObject("update");
                //然后再根据这个对象获取名称是loc的数据信息
                String publishTime = weather_info_basic_update.getString("loc");

                //关于天气的所有信息都是在daily_forecast名称下面，仔细查看，发现，daily_forecast后面是[符号，说明，这也是一个JSON数组
                //所以先根据名称获取JSONArray对象
                JSONArray weather_info_daily_forecast = weather_info_all.getJSONArray("daily_forecast");
                //我们发现，[]里面是由很多个像下面这样的元素组成的
                /*
                {
                    "astro": {
                        "sr": "04:49",
                        "ss": "19:47"
                    },
                    "cond": {
                        "code_d": "302",
                        "code_n": "302",
                        "txt_d": "雷阵雨",
                        "txt_n": "雷阵雨"
                    },
                    "date": "2016-06-30",
                    "hum": "30",
                    "pcpn": "0.2",
                    "pop": "39",
                    "pres": "1002",
                    "tmp": {
                        "max": "31",
                        "min": "22"
                    },
                    "vis": "10",
                    "wind": {
                          "deg": "204",
                          "dir": "无持续风向",
                          "sc": "微风",
                          "spd": "4"
                    }
                },
                */

                //第一个元素是当前的日期相关的天气数据，目前我们只需要第一个，并且获取出来的是一个JSONObject
                JSONObject weather_info_now_forecast = weather_info_daily_forecast.getJSONObject(0);
                //你会发现，date是可以直接获取的，因为date后面是没有｛｝的
                // editor.putString("data_now", weather_info_now_forecast.getString("date"));//当前日期
                //tmp节点是当前的温度，包含最低和最高，说明这是一个JSONObject
                JSONObject weather_info_now_forecast_tmp = weather_info_now_forecast.getJSONObject("tmp");
                String temp1 = weather_info_now_forecast_tmp.getString("min");
                String temp2 = weather_info_now_forecast_tmp.getString("max");

                //cond是当前的实际天气描述，获取方法和tmp是一样的
                JSONObject weather_info_forecast_cond = weather_info_now_forecast.getJSONObject("cond");
                String weatherDesp = weather_info_forecast_cond.getString("txt_d");//天气情况前
                //editor.putString("txt_n", weather_info_forecast_cond.getString("txt_n"));//天气情况后

                //最后提交
                saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
                        weatherDesp, publishTime);
            } catch (JSONException e) {
                Log.d("life", "Utility JSONException");
                e.printStackTrace();
            }
        }
        return false;
    }

    public synchronized static boolean handleWeatherResponse(Context context, String response, String date) {//SharedPreferences.Editor editor
        if (!TextUtils.isEmpty(response)) {
            try {
                //先把JSON数据加载成数组，因为根部HeWeather data service 3.0后面是[符号，说明是以数组形式存放，只是这个数组里面只有一个元素
                JSONArray jsonArray = new JSONObject(response).getJSONArray("HeWeather data service 3.0");

                //那么既然知道这个数组里面只有一个元素，所以我们直接取出第一个元素为JSONObject
                JSONObject weather_info_all = jsonArray.getJSONObject(0);

                //关于天气的所有信息都是在daily_forecast名称下面，仔细查看，发现，daily_forecast后面是[符号，说明，这也是一个JSON数组
                //所以先根据名称获取JSONArray对象
                JSONArray weather_info_daily_forecast = weather_info_all.getJSONArray("daily_forecast");
                Log.d("life", "预报" + weather_info_daily_forecast);
                //我们发现，[]里面是由很多个像下面这样的元素组成的
                /*
                {
                    "astro": {
                        "sr": "04:49",
                        "ss": "19:47"
                    },
                    "cond": {
                        "code_d": "302",
                        "code_n": "302",
                        "txt_d": "雷阵雨",
                        "txt_n": "雷阵雨"
                    },
                    "date": "2016-06-30",
                    "hum": "30",
                    "pcpn": "0.2",
                    "pop": "39",
                    "pres": "1002",
                    "tmp": {
                        "max": "31",
                        "min": "22"
                    },
                    "vis": "10",
                    "wind": {
                          "deg": "204",
                          "dir": "无持续风向",
                          "sc": "微风",
                          "spd": "4"
                    }
                },
                */
                for (int i = 0; i < 7; i++) {
                    if (weather_info_daily_forecast.getJSONObject(i).getString("date").equals(date)) {
                        JSONObject weather_info_now_forecast = weather_info_daily_forecast.getJSONObject(i);
                        Log.d("policy", weather_info_now_forecast.getString("date"));
                        //cond是当前的实际天气描述，获取方法和tmp是一样的
                        JSONObject weather_info_forecast_cond = weather_info_now_forecast.getJSONObject("cond");
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(context).edit();
                        editor.putString("txt_d", weather_info_forecast_cond.getString("txt_d"));//天气情况前
                        editor.putString("txt_n", weather_info_forecast_cond.getString("txt_n"));//天气情况后
                        editor.apply();
                        return true;
                    }

                }
            } catch (JSONException e) {
                Log.d("life", "Utility JSONException");
                e.printStackTrace();
            }
        }
        return false;
    }

    //处理从服务器获取的数据
    public synchronized static boolean handleAllCityResponse(WeatherDB weatherDB, String response) {
        Log.d("policy", "handleAllCityResponse");

        if (!TextUtils.isEmpty(response)) {
            try {
                //城市信息JSON比较简单，这里不做详细的解析分析
                JSONArray jsonArray = new JSONObject(response).getJSONArray("city_info");
                if (jsonArray.length() > 0){
                    Log.d("debug", "查询全部城市成功");
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject city_info = jsonArray.getJSONObject(i);
                    AllCity city = new AllCity();
                    String city_name_ch = city_info.getString("city");
                    String city_name_en = "";
                    String city_code = city_info.getString("id");
                    city.setCity_code(city_code);
                    city.setCity_name_en(city_name_en);
                    city.setCity_name_ch(city_name_ch);
                    weatherDB.saveAllCity(city);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
