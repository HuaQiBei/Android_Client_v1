package com.bignerdranch.android.android_client_v1.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.android_client_v1.db.WeatherDB;
import com.bignerdranch.android.android_client_v1.util.HttpCallbackListener;
import com.bignerdranch.android.android_client_v1.util.HttpUtil;
import com.bignerdranch.android.android_client_v1.util.Utility;

import java.util.ArrayList;

public class AddFlightPolicyFragment extends Fragment {

    private WeatherDB mWeatherDB;//数据库操作对象

    private EditText mFlightData;
    private EditText mFilghtId;
    private EditText mFilghtRoute;
    private EditText mFilghtTime;
    private EditText mFilghtWeather;
    private TextView mFilght;

    private static final String ARG_FLIGHT_DAILAY_POLICY = "flight_policy";

    public static AddFlightPolicyFragment newInstance(ArrayList<String> par) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_FLIGHT_DAILAY_POLICY, par);
        AddFlightPolicyFragment fragment = new AddFlightPolicyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWeatherDB = WeatherDB.getInstance(getActivity());//获取数据库处理对象
        //先检查本地是否已同步过城市数据，如果没有，则从服务器同步
        if (mWeatherDB.checkDataState() == 0) {
            Log.d("policy", "没有All城市列表");
            queryCitiesFromServer();
        }

        ArrayList<String> par = getArguments().getStringArrayList(ARG_FLIGHT_DAILAY_POLICY);
        Log.d("policy", par.get(0));//航班号
        Log.d("policy", par.get(1));//起点
        Log.d("policy", par.get(2));//终点

        queryWeatherInfo(par.get(1), "2016-08-27");
    }

    /**TODO
     * 监听mFilghtTime控件改变或者另外设置按钮触发查询天气
     */
    /**
     * 查询天气代号所对应的天气。
     */
    private void queryWeatherInfo(String cityName, String date/*日期用字符串?Date?*/) {
        String weatherCode = mWeatherDB.loadCitiesByName(cityName).getCity_code();

        Log.d("policy", "cityName" + cityName + "weatherCode" + weatherCode);
        String address = "https://api.heweather.com/x3/weather?cityid=" + weatherCode + "&key=" + WeatherActivity.WEATHER_KEY;
        Log.d("policy", address);
        queryFromServer(address, "weatherCode", date);
    }

    /**
     * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
     */
    private void queryFromServer(final String address, final String type, final String date) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                if ("weatherCode".equals(type)) {
                    Log.d("life", "进入天气服务器weatherCode");
                    // 处理服务器返回的天气信息
                    Utility.handleWeatherResponse(getActivity(), response, date);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            Log.d("policy", prefs.getString("txt_d", "变化前") + "转" + prefs.getString("txt_n", "变化后"));
                            mFilghtWeather.setText(prefs.getString("txt_d", "变化前") + "转" + prefs.getString("txt_n", "变化后"));//设置天气
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFilghtWeather.setText("查询天气失败");
                    }
                });
            }
        });
    }

    //从服务器取出所有的城市信息
    private void queryCitiesFromServer() {
        String address = "https://api.heweather.com/x3/citylist?search=allchina&key=" + WeatherActivity.WEATHER_KEY;
        MyProgressDialog.showProgressDialog(getActivity());
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if (Utility.handleAllCityResponse(mWeatherDB, response)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyProgressDialog.closeProgressDialog();
                            mWeatherDB.updateDataState();
                        }
                    });
                }
            }

            @Override
            public void onError(final Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyProgressDialog.closeProgressDialog();
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }
}
