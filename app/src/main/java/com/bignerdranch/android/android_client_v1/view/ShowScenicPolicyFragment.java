package com.bignerdranch.android.android_client_v1.view;

/**
 * Created by Administrator on 2016/8/22.
 */

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
import android.widget.Button;
import android.widget.TextView;


import com.bignerdranch.android.android_client_v1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ShowScenicPolicyFragment extends Fragment {

    public static String result;


    private TextView scenicname;
    private TextView scenicweather;
    private TextView startdate;
    private TextView enddate;
    private TextView fee;
    private TextView insuredMan;
    private TextView insuredManIDCard;
    private TextView coverage;
    private TextView coverage1;
    private TextView coverage2;
    private View obtain_pay_button;

    //    private TextView insuredname;
//    private TextView insuredIDcard;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        result = getActivity().getIntent().getExtras().getString("policydetail");
        Log.d("test", "from policy detail:" + result);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_scenicpolicy, container, false);

        scenicname = (TextView) v.findViewById(R.id.scenicName);
        scenicweather = (TextView) v.findViewById(R.id.scenicWeather);
        startdate = (TextView) v.findViewById(R.id.scenicStartDate);
        enddate = (TextView) v.findViewById(R.id.scenicEndDate);
        fee = (TextView) v.findViewById(R.id.fee);
        insuredMan = (TextView) v.findViewById(R.id.insuredMan);
        insuredManIDCard = (TextView) v.findViewById(R.id.insuredManIDCard);
        coverage = (TextView) v.findViewById(R.id.policy_coverage);
        coverage1 = (TextView) v.findViewById(R.id.insureduty1);
        coverage2 = (TextView) v.findViewById(R.id.insureduty2);
        obtain_pay_button = v.findViewById(R.id.obtain_pay_button);
        JSONArray respObjectArr = null;
        JSONObject respJsonObj = null;


        try {
            respObjectArr = new JSONArray(result);
            respJsonObj = respObjectArr.getJSONObject(0);

            scenicname.setText(respJsonObj.getString("scenicname"));
            scenicweather.setText(respJsonObj.getString("scenicweather"));
            startdate.setText(respJsonObj.getString("startdate"));
            enddate.setText(respJsonObj.getString("enddate"));
            coverage.setText(Integer.parseInt(respJsonObj.getString("insureduty")) + Integer.parseInt(respJsonObj.getString("coverage")) + "");
            coverage1.setText(Integer.parseInt(respJsonObj.getString("insureduty")) + "");
            coverage2.setText(Integer.parseInt(respJsonObj.getString("coverage")) + "");
            fee.setText(String.valueOf(respJsonObj.getDouble("fee")));
            insuredMan.setText(respJsonObj.getString("insuredman"));
            insuredManIDCard.setText(respJsonObj.getString("insuredIDCard"));
            final int policyID = respJsonObj.getInt("policyID");

            if (respJsonObj.getString("state").equals("生效中")) {
                obtain_pay_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ObtainPayActivity.class);
                        intent.putExtra("policyID", policyID);
                        startActivity(intent);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        insuredname=(TextView) v.findViewById(R.id.insuredman);
//        insuredIDcard=(TextView) v.findViewById(R.id.insuredmanIDCard);
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

