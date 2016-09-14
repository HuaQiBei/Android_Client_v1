package com.bignerdranch.android.android_client_v1.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bignerdranch.android.android_client_v1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Elvira on 2016/9/1.
 */
public class ShowFlightPolicyFragment extends Fragment {

    public static String result;
    private EditText mFlightDate;
    private EditText mFlightId;
    private EditText mFlightRoute;
    private EditText mFlightTime;
    private EditText mFlightWeather;
    private CheckBox mOneHour;
    private CheckBox mFourHours;
    private CheckBox mCancelFlight;
    private TextView mFlightCoverage;
    private TextView mFlightFee;
    private EditText insuredMan;
    private EditText insuredManIDCard;
    private View obtain_pay_button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        result = getActivity().getIntent().getExtras().getString("policydetail");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_flightpolicy, container, false);

        mFlightDate = (EditText) v.findViewById(R.id.flight_date);
        mFlightId = (EditText) v.findViewById(R.id.flight_id);
        mFlightRoute = (EditText) v.findViewById(R.id.flight_route);
        mFlightWeather = (EditText) v.findViewById(R.id.flight_weather);
        mOneHour = (CheckBox) v.findViewById(R.id.one_hour_checked);
        mFourHours = (CheckBox) v.findViewById(R.id.four_hour_checked);
        mCancelFlight = (CheckBox) v.findViewById(R.id.flight_cancel_checked);
        mFlightCoverage = (TextView) v.findViewById(R.id.policy_coverage);
        mFlightFee = (TextView) v.findViewById(R.id.policyfee);
        insuredMan = (EditText) v.findViewById(R.id.insuredMan);
        insuredManIDCard = (EditText) v.findViewById(R.id.insuredManIDCard);
        obtain_pay_button = v.findViewById(R.id.obtain_pay_button);

        JSONArray respObjectArr;
        JSONObject respJsonObj;

        try {
            respObjectArr = new JSONArray(result);
            respJsonObj = respObjectArr.getJSONObject(0);

            mFlightDate.setText(respJsonObj.getString("mFlightDate"));
            mFlightId.setText(respJsonObj.getString("mFlightId"));
            mFlightRoute.setText(respJsonObj.getString("mFlightRoute"));
            mFlightWeather.setText(respJsonObj.getString("mFlightWeather"));
            mFlightCoverage.setText(Double.toString(respJsonObj.getDouble("mFlightCoverage")));
            mFlightFee.setText(Double.toString(respJsonObj.getDouble("mFlightFee")));
            insuredMan.setText(respJsonObj.getString("insuredman"));
            insuredManIDCard.setText(respJsonObj.getString("insuredIDCard"));
            final int policyID = respJsonObj.getInt("policyID");
            if (respJsonObj.getString("state").equals("生效中")){
                obtain_pay_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ObtainPayActivity.class);
                        intent.putExtra("policyID", policyID);
                        startActivity(intent);
                    }
                });
            }
            int sum = Integer.parseInt(respJsonObj.getString("mFlightCheckBox"));
            switch (sum) {
                case 1:
                    mOneHour.setChecked(true);
                    break;
                case 2:
                    mFourHours.setChecked(true);
                    break;
                case 3:
                    mOneHour.setChecked(true);
                    mFourHours.setChecked(true);
                    break;
                case 4:
                    mCancelFlight.setChecked(true);
                    break;
                case 5:
                    mOneHour.setChecked(true);
                    mCancelFlight.setChecked(true);
                    break;
                case 6:
                    mFourHours.setChecked(true);
                    mCancelFlight.setChecked(true);
                    break;
                case 7:
                    mOneHour.setChecked(true);
                    mFourHours.setChecked(true);
                    mCancelFlight.setChecked(true);
                    break;
                default:
                    mOneHour.setChecked(false);
                    mFourHours.setChecked(false);
                    mCancelFlight.setChecked(false);
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.id_toolbar);
        activity.setSupportActionBar(toolbar);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        return v;
    }
}
