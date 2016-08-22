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

/**
 * Created by Administrator on 2016/8/10.
 */
public class AddScenicPolicyFragment extends Fragment {

    private AddScenicPolicyTask mAuthTask = null;
    public static String resultString;
    public Connect2Server c2s=new Conn2ServerImp();


    private View bt_add_scenicPolicy_OK;
    private EditText scenicname;
    private EditText scenicweather;
    private EditText startdate;
    private EditText enddate;
    private EditText insurednum;
    private EditText insureduty;
    private TextView fee;
//    private TextView insuredname;
//    private TextView insuredIDcard;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_scenicpolicy, container, false);
        bt_add_scenicPolicy_OK = v.findViewById(R.id.add_scenicPolicy_OK);

        scenicname=(EditText)v.findViewById(R.id.scenicName);
        scenicweather=(EditText)v.findViewById(R.id.scenicWeather);
        startdate=(EditText)v.findViewById(R.id.scenicStartDate);
        enddate=(EditText)v.findViewById(R.id.scenicEndDate);
        insureduty=(EditText)v.findViewById(R.id.insureDuty);
        fee=(TextView) v.findViewById(R.id.scenicFee);

//        insuredname=(TextView) v.findViewById(R.id.insuredman);
//        insuredIDcard=(TextView) v.findViewById(R.id.insuredmanIDCard);

        bt_add_scenicPolicy_OK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String scenicnamestr=scenicname.getText().toString();
                String scenicweatherstr=scenicweather.getText().toString();
                String startdatestr=startdate.getText().toString();
                String enddatestr=enddate.getText().toString();
                String insuredutystr=insureduty.getText().toString();
                String feestr=fee.getText().toString();

                ScenicPolicy policy = new ScenicPolicy(12345,startdatestr,enddatestr,12,Double.parseDouble(feestr),scenicnamestr,scenicweatherstr,insuredutystr);


//                String insurednamestr=insuredname.getText().toString();
//                String insuredIDcardstr=insuredIDcard.getText().toString();

                if (mAuthTask != null) {
                    return;
                }
                Log.d("test","click the commit button!");
                mAuthTask = new AddScenicPolicyTask(policy);//为后台传递参数
                mAuthTask.execute((Void) null);

                Intent intent = new Intent(getActivity(),ContactActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    public class AddScenicPolicyTask extends AsyncTask<Void, Void, String> {

           ScenicPolicy mScenicPolicy=null;

        AddScenicPolicyTask(ScenicPolicy scenicPolicy) {
            this.mScenicPolicy=scenicPolicy;

        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("test","in to do addpolicy in background!");
            try {
                // Simulate network access.
                resultString = c2s.addScenicPolicy(mScenicPolicy);

                Log.d("test","resultString="+resultString);
                return resultString;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Noting";

        }

        @Override
        protected void onPostExecute(final String result) {
            mAuthTask = null;
            //showProgress(false);
            Log.d("test","in to on PostExecute!");

            if (result!=null) {
                Log.d("test",result);
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);

            } else {
                Log.d("test","return nothing!");
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

