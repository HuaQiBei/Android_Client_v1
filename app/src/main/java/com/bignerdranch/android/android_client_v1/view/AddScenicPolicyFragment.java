package com.bignerdranch.android.android_client_v1.view;

/**
 * Created by Administrator on 2016/8/22.
 */

import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.android_client_v1.MainActivity;
import com.bignerdranch.android.android_client_v1.R;
import com.bignerdranch.android.android_client_v1.model.BasePolicy;
import com.bignerdranch.android.android_client_v1.model.PolicyLab;
import com.bignerdranch.android.android_client_v1.model.ScenicPolicy;
import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

import org.json.JSONException;
import java.util.ArrayList;
/**
 * Created by Administrator on 2016/8/10.
 */
public class AddScenicPolicyFragment extends Fragment {

    private AddScenicPolicyTask mAuthTask = null;
    public static String resultString;
    public Connect2Server c2s = new Conn2ServerImp();


    private View bt_add_scenicPolicy_OK;
    private EditText scenicname;
    private EditText scenicweather;
    private EditText startdate;
    private EditText enddate;
    private EditText insurednum;
    private EditText insureduty;
    private TextView fee;
    private ListView mInsuredPersonListView;
    private ImageView mAdd;
    private MyAdapter adapter;

    private static final String ARG_SCENIC_SPOT = "scenic_policy";

    //    private TextView insuredname;
//    private TextView insuredIDcard;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static AddScenicPolicyFragment newInstance(ArrayList<String> par) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_SCENIC_SPOT, par);
        AddScenicPolicyFragment fragment = new AddScenicPolicyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_scenicpolicy, container, false);
        bt_add_scenicPolicy_OK = v.findViewById(R.id.add_scenicPolicy_OK);

        scenicname = (EditText) v.findViewById(R.id.scenicName);
        scenicweather = (EditText) v.findViewById(R.id.scenicWeather);
        startdate = (EditText) v.findViewById(R.id.scenicStartDate);
        enddate = (EditText) v.findViewById(R.id.scenicEndDate);
        insureduty = (EditText) v.findViewById(R.id.insureDuty);
        fee = (TextView) v.findViewById(R.id.scenicFee);
        mInsuredPersonListView = (ListView) v.findViewById(R.id.insured_list);
        mAdd = (ImageView) v.findViewById(R.id.addScenicPolicyMan);

//        insuredname=(TextView) v.findViewById(R.id.insuredman);
//        insuredIDcard=(TextView) v.findViewById(R.id.insuredmanIDCard);

        adapter = new MyAdapter(getActivity());
        mInsuredPersonListView.setAdapter(adapter);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                adapter.arr1.add("");
                adapter.arr2.add("");
                adapter.notifyDataSetChanged();
            }
        });

        bt_add_scenicPolicy_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String scenicnamestr = scenicname.getText().toString();
                String scenicweatherstr = scenicweather.getText().toString();
                String startdatestr = startdate.getText().toString();
                String enddatestr = enddate.getText().toString();
                String insuredutystr = insureduty.getText().toString();
                String feestr = fee.getText().toString();


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                int userID=preferences.getInt("id",0);
                if(userID==0){
                    Log.d("test","没取到用户ID");
                }
                ScenicPolicy policy = new ScenicPolicy(100,startdatestr,enddatestr,userID,Double.parseDouble(feestr),scenicnamestr,scenicweatherstr,insuredutystr,"生效中");


//                String insurednamestr=insuredname.getText().toString();
//                String insuredIDcardstr=insuredIDcard.getText().toString();

                if (mAuthTask != null) {
                    return;
                }
                Log.d("test","click the scenic commit button!");
                mAuthTask = new AddScenicPolicyTask(policy);//为后台传递参数
                mAuthTask.execute((Void) null);

//                Intent intent = new Intent(getActivity(),MainActivity.class);
//                startActivity(intent);
            }
        });
        return v;
    }

    public class AddScenicPolicyTask extends AsyncTask<Void, Void, String> {

        ScenicPolicy mScenicPolicy = null;

        AddScenicPolicyTask(ScenicPolicy scenicPolicy) {
            this.mScenicPolicy = scenicPolicy;

        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("test","in to do addpolicy in background!");
            try {
                // Simulate network access.
                resultString = c2s.addScenicPolicy(mScenicPolicy);

                Log.d("test","add scenic policy resultString="+resultString);
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
                try {
                    mScenicPolicy.setPolicyID(Integer.parseInt(result));
                    PolicyLab policyList = PolicyLab.get(result);
                    BasePolicy newPolicy=new BasePolicy(mScenicPolicy.getFee(),mScenicPolicy.getPolicyID(),"景区意外险","生效中",mScenicPolicy.getScenicname());
                    policyList.addPolicy(newPolicy);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private class MyAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        public ArrayList<String> arr1;
        public ArrayList<String> arr2;
        public MyAdapter(Context context) {
            super();
            this.context = context;
            inflater = LayoutInflater.from(context);
            arr1 = new ArrayList<String>();
            for (int i = 0; i < 3; i++) {    //listview初始化3个子项
                arr1.add("");
            }
            arr2 = new ArrayList<String>();
            for (int i = 0; i < 3; i++) {    //listview初始化3个子项
                arr2.add("");
            }
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arr1.size()+arr2.size();
        }
        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }
        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }
        @Override
        public View getView(final int position, View view, ViewGroup arg2) {
            // TODO Auto-generated method stub
            if(view == null){
                view = inflater.inflate(R.layout.item_insured, null);
            }
            final EditText name = (EditText) view.findViewById(R.id.insured_person);
            name.setText(arr1.get(position));    //在重构adapter的时候不至于数据错乱
            final EditText idcard = (EditText) view.findViewById(R.id.insured_idcard);
            idcard.setText(arr2.get(position));
            ImageView del = (ImageView) view.findViewById(R.id.delete_insured_person);
            name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // TODO Auto-generated method stub
                    if(arr1.size()>0){
                        arr1.set(position, name.getText().toString());
                    }
                }
            });
            idcard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // TODO Auto-generated method stub
                    if(arr2.size()>0){
                        arr2.set(position, idcard.getText().toString());
                    }
                }
            });
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    //从集合中删除所删除项的EditText的内容
                    arr1.remove(position);
                    arr2.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}


