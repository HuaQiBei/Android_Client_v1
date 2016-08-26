package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bignerdranch.android.android_client_v1.ConnectTask;
import com.bignerdranch.android.android_client_v1.R;
import com.bignerdranch.android.android_client_v1.view.AddFlightPolicyActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DELL on 2016/8/23.
 */
public class LifeFragment extends Fragment implements View.OnClickListener{

    private TextView mLocation;
    private TextView mWeather;
    private TextView mTemperature;
    private Button mAddNightRunningPolicyButton;

    private EditText mFlightNo;
    private EditText mFlightStartCity;
    private EditText mFlightEndCity;
    private TextView mDelayRate;
    private Button mAddFlightPolicyButton;

    private TextView mScenicSpot;
    private TextView mScenicSpotIntroduce;
    private Button mAddScenicSpotPolicyButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("test", "LifeFragment onCreateView");
        View view = inflater.inflate(
                R.layout.fragment_life,
                container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        mLocation = (TextView) view.findViewById(R.id.location);
        mWeather = (TextView) view.findViewById(R.id.weather);
        mTemperature = (TextView) view.findViewById(R.id.temperature);
        mAddNightRunningPolicyButton = (Button) view.findViewById(R.id.add_night_running_policy_button);

        //TODO 设置当前位置，暂时填城市
        mLocation.setText(prefs.getString("city_name", ""));
        mTemperature.setText(prefs.getString("temp1", "") + "℃~" + prefs.getString("temp2", "") + "℃");
        mWeather.setText(prefs.getString("weather_desp", ""));


        mFlightNo = (EditText) view.findViewById(R.id.flight_no);
        mFlightStartCity = (EditText) view.findViewById(R.id.flight_start_city);
        mFlightEndCity = (EditText) view.findViewById(R.id.flight_end_city);
        mDelayRate = (TextView) view.findViewById(R.id.flight_delay_rate);
        mAddFlightPolicyButton = (Button) view.findViewById(R.id.add_flight_policy_button);

        mScenicSpot = (TextView) view.findViewById(R.id.scenic_spot_name);
        mScenicSpotIntroduce = (TextView) view.findViewById(R.id.scenic_spot_introduce);
        mAddScenicSpotPolicyButton = (Button) view.findViewById(R.id.add_flight_policy_button);

        mScenicSpot.setText("");//TODO 根据定位得到的景区名称 要加景区描述吗？

        mFlightEndCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String flightNo = mFlightNo.getText().toString();
                    String flightStartCity = mFlightStartCity.getText().toString();
                    String flightEndCity = mFlightEndCity.getText().toString();
                    /**
                     * TODO
                     * 去查询输入对应航班的延误率
                     * 然后显示在mDelayRate
                     */
                }

            }
        });

        mAddNightRunningPolicyButton.setOnClickListener(this);
        mAddFlightPolicyButton.setOnClickListener(this);
        mAddScenicSpotPolicyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_night_running_policy_button:

                /**
                 * TODO
                 * 夜跑险的Activity
                 */
                break;
            case R.id.add_flight_policy_button:
                ArrayList<String> par = new ArrayList<>();
                par.add(mFlightNo.getText().toString());
                par.add(mFlightStartCity.getText().toString());
                par.add(mFlightEndCity.getText().toString());
                Intent intent = AddFlightPolicyActivity.newIntent(getActivity(), par);
                startActivity(intent);
                break;
            case R.id.add_scenic_spot_policy_button:
                /**
                 * TODO
                 * 景区意外险的Activity
                 */
                break;
        }

    }
}
