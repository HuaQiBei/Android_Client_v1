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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.android_client_v1.view.AddFlightPolicyActivity;
import com.bignerdranch.android.android_client_v1.view.AddScenicPolicyActivity;
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
    private ArrayList<String> par;

    LinearLayout parent;

    Intent intent;

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
        (view.findViewById(R.id.flight_delay)).setOnClickListener(this);
        (view.findViewById(R.id.scenic_spot)).setOnClickListener(this);
        (view.findViewById(R.id.night_running)).setOnClickListener(this);
        parent = (LinearLayout) view.findViewById(R.id.parent);
        iPI();
    }

    /**
     * Intelligent Portable Insurance
     */
    private void iPI() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (prefs.getBoolean("flightDelayView", false)) {
            //航班
            if (getView().findViewById(R.id.add_flight_policy_button) == null)
                parent.addView(flightDelayView(prefs.getString("flightNo", "航班号"),
                        prefs.getString("flightStartCity", "出发地"),
                        prefs.getString("flightEndCity", "到达地")));
        }
        if (prefs.getBoolean("scenicSpotView", false)) {
            //景区
            if (getView().findViewById(R.id.add_scenic_spot_policy_button) == null)
                parent.addView(scenicSpotView());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flight_delay:
                if (getView().findViewById(R.id.add_flight_policy_button) == null) {
                    parent.addView(flightDelayView());
                }
                break;
            case R.id.scenic_spot:
                if (getView().findViewById(R.id.add_scenic_spot_policy_button) == null)
                    parent.addView(scenicSpotView());
                break;
            case R.id.night_running:
                if (getView().findViewById(R.id.add_night_running_policy_button) == null)
                    parent.addView(nightRunningView());
                break;
            case R.id.add_night_running_policy_button:
                break;
            case R.id.add_flight_policy_button:
                par = new ArrayList<>();
                par.add(mFlightNo.getText().toString());
                par.add(mFlightStartCity.getText().toString());
                par.add(mFlightEndCity.getText().toString());
                intent = AddFlightPolicyActivity.newIntent(getActivity(), par, data);
                startActivity(intent);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                preferences.edit()
                        .putBoolean("flightDelayView", false)
                        .apply();
                break;
            case R.id.add_scenic_spot_policy_button:
                par = new ArrayList<>();
                par.add("华山");//TODO mScenicSpot.getText().toString());
                par.add("华阴");//TODO 景区城市 par.add(mScenicSpotIntroduce.getText().toString());
                intent = AddScenicPolicyActivity.newIntent(getActivity(), par);
                startActivity(intent);
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
                    data = result;
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

    private View nightRunningView() {
        //TODO 动态增加卡片
        // LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.item_night_running, null);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        mLocation = (TextView) view.findViewById(R.id.location);
        mWeather = (TextView) view.findViewById(R.id.weather);
        mTemperature = (TextView) view.findViewById(R.id.temperature);
        mAddNightRunningPolicyButton = (Button) view.findViewById(R.id.add_night_running_policy_button);

        //TODO 设置当前位置，暂时填城市
        mLocation.setText(prefs.getString("city_name", ""));
        mTemperature.setText(prefs.getString("temp1", "") + "℃~" + prefs.getString("temp2", "") + "℃");
        mWeather.setText(prefs.getString("weather_desp", ""));
        mAddNightRunningPolicyButton.setOnClickListener(this);
        //view.setLayoutParams(lp);
        return view;
    }

    private View flightDelayView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.item_flight, null);
        view.setFocusable(true);
        mFlightNo = (EditText) view.findViewById(R.id.flight_no);
        mFlightStartCity = (EditText) view.findViewById(R.id.flight_start_city);
        mFlightEndCity = (EditText) view.findViewById(R.id.flight_end_city);
        mDelayRate = (TextView) view.findViewById(R.id.flight_delay_rate);
        mAddFlightPolicyButton = (Button) view.findViewById(R.id.add_flight_policy_button);
        mFlightEndCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String flightNo = mFlightNo.getText().toString();
                    String flightStartCity = mFlightStartCity.getText().toString();
                    String flightEndCity = mFlightEndCity.getText().toString();

                    if (mAuthTask != null) {
                        return;
                    }
                    Log.d("test", "end city focus changed!");//"MU5693","北京首都","上海");//
                    mAuthTask = new GetDelayRateTask(flightNo, flightStartCity, flightEndCity);//为后台传递参数
                    mAuthTask.execute((Void) null);
                }

            }
        });
        mAddFlightPolicyButton.setOnClickListener(this);
        return view;
    }

    private View flightDelayView(String flightNo, String flightStartCity, String flightEndCity) {
        if (mAuthTask != null) {
            return null;
        }
        //"MU5693","北京首都","上海");
        if (!flightNo.equals("航班号")) {
            mAuthTask = new GetDelayRateTask(flightNo, flightStartCity, flightEndCity);//为后台传递参数
            mAuthTask.execute((Void) null);
        }
        View view = flightDelayView();
        mFlightNo.setText(flightNo);
        mFlightStartCity.setText(flightStartCity);
        mFlightEndCity.setText(flightEndCity);
        return view;
    }

    private View scenicSpotView() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.item_scenic_spot, null);
        mScenicSpot = (TextView) view.findViewById(R.id.scenic_spot_name);
        mScenicSpot.setText(prefs.getString("scenic_spot_name", "景点名称"));
        mScenicSpotIntroduce = (TextView) view.findViewById(R.id.scenic_spot_introduce);
        mScenicSpotIntroduce.setText(prefs.getString("scenic_spot_city", "所在城市"));
        mAddScenicSpotPolicyButton = (Button) view.findViewById(R.id.add_scenic_spot_policy_button);
        mAddScenicSpotPolicyButton.setOnClickListener(this);
        return view;
    }

}
