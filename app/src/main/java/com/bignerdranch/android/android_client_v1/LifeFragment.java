package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import com.bignerdranch.android.android_client_v1.view.AddFlightPolicyActivity;
import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DELL on 2016/8/23.
 */
public class LifeFragment extends Fragment implements View.OnClickListener {

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

    private Connect2Server c2s = new Conn2ServerImp();
    private GetDelayRateTask mAuthTask = null;
    public String data;

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

                    if (mAuthTask != null) {
                        return;
                    }
                    Log.d("test", "end city focus changed!");//"MU5693","北京首都","上海");//
                    mAuthTask = new GetDelayRateTask(flightNo, flightStartCity, flightEndCity);//为后台传递参数
                    mAuthTask.execute((Void) null);


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
                Intent intent = AddFlightPolicyActivity.newIntent(getActivity(), par,data);
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

    public class GetDelayRateTask extends AsyncTask<Void, Void, String> {

        String mFlightCode;
        String mStartCity;
        String mEndCity;

        GetDelayRateTask(String flightCode, String startCity, String endCity) {
            this.mFlightCode = flightCode;
            this.mStartCity = startCity;
            this.mEndCity = endCity;

        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("test", "in to do fing delay rate in background!");
            try {
                // Simulate network access.
                String resultString = c2s.findDelayRate(mFlightCode, mStartCity, mEndCity);

                Log.d("test", "find delay rate resultString=" + resultString);
                return resultString;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Nothing";

        }

        @Override
        protected void onPostExecute(final String result) {
            mAuthTask = null;
            //showProgress(false);
            Log.d("test", "in to on PostExecute!");

            if (result != null) {
                Log.d("test", result);
                try {
                    JSONObject delayRate = new JSONObject(result);
                    mDelayRate.setText(
                            "延误1小时以上：" + delayRate.getString("9") + "\n延误4小时以上：" + delayRate.getString("10") + "\n航班取消：" + delayRate.getString("11"));
                    data=result;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.d("test", "return nothing!");
                //getActivity().finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            // showProgress(false);
        }
    }

}
