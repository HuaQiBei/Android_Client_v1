package com.bignerdranch.android.android_client_v1.view;

/**
 * Created by Administrator on 2016/8/22.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bignerdranch.android.android_client_v1.ContactActivity;
import com.bignerdranch.android.android_client_v1.MainActivity;
import com.bignerdranch.android.android_client_v1.MyInfoActivity;
import com.bignerdranch.android.android_client_v1.R;
import com.bignerdranch.android.android_client_v1.model.ScenicPolicy;
import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ShowScenicPolicyFragment extends Fragment {

    public static String result;


    private EditText scenicname;
    private EditText scenicweather;
    private EditText startdate;
    private EditText enddate;
    private EditText insureduty;
    private TextView fee;
    //    private TextView insuredname;
//    private TextView insuredIDcard;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         result=getActivity().getIntent().getExtras().getString("policydetail");
         Log.d("test","from policy detail:"+result);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_scenicpolicy, container, false);

        scenicname = (EditText) v.findViewById(R.id.scenicName);
        scenicweather = (EditText) v.findViewById(R.id.scenicWeather);
        startdate = (EditText) v.findViewById(R.id.scenicStartDate);
        enddate = (EditText) v.findViewById(R.id.scenicEndDate);
        insureduty = (EditText) v.findViewById(R.id.insureDuty);
        fee = (TextView) v.findViewById(R.id.scenicFee);

        JSONArray respObjectArr = null;
        JSONObject respJsonObj = null;


        try {
            respObjectArr = new JSONArray(result);
            respJsonObj = respObjectArr.getJSONObject(0);

            scenicname.setText(respJsonObj.getString("scenicname"));
            scenicweather.setText(respJsonObj.getString("scenicweather"));
            startdate.setText(respJsonObj.getString("startdate"));
            enddate.setText(respJsonObj.getString("enddate"));
            insureduty.setText(respJsonObj.getString("insureduty"));
            fee.setText(Double.toString(respJsonObj.getDouble("fee")));


        } catch (JSONException e) {
            e.printStackTrace();
        }


//        insuredname=(TextView) v.findViewById(R.id.insuredman);
//        insuredIDcard=(TextView) v.findViewById(R.id.insuredmanIDCard);

        return v;
    }
}

