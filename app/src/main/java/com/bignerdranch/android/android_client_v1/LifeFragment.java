package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DELL on 2016/8/23.
 */
public class LifeFragment extends Fragment {

    private EditText mFlightNo;
    private EditText mFlightStartCity;
    private EditText mFlightEndCity;
    private TextView mDelayRate;
    private Button mAddFlightPolicyButton;

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
        mFlightNo = (EditText) view.findViewById(R.id.flight_no);
        mFlightStartCity =  (EditText) view.findViewById(R.id.flight_start_city);
        mFlightEndCity = (EditText) view.findViewById(R.id.flight_end_city);
        mDelayRate = (TextView)view.findViewById(R.id.flight_delay_rate);
        mAddFlightPolicyButton = (Button)view.findViewById(R.id.add_flight_policy_button);

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

        mAddFlightPolicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),航班延误险的Activity.class);
//                ArrayList<String> par = new ArrayList<>();
//                par.add(mFlightNo.getText().toString());
//                par.add(mFlightStartCity.getText().toString());
//                par.add(mFlightEndCity.getText().toString());
//                intent.putStringArrayListExtra("flight_policy", par);
//                startActivity(intent);
            }
        });
    }



}
