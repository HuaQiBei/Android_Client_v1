package com.bignerdranch.android.android_client_v1.view;

/**
 * Created by Administrator on 2016/8/22.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.android_client_v1.MainActivity;
import com.bignerdranch.android.android_client_v1.R;
import com.bignerdranch.android.android_client_v1.db.WeatherDB;
import com.bignerdranch.android.android_client_v1.model.BasePolicy;
import com.bignerdranch.android.android_client_v1.model.Fee;
import com.bignerdranch.android.android_client_v1.model.PolicyLab;
import com.bignerdranch.android.android_client_v1.model.ScenicPolicy;
import com.bignerdranch.android.android_client_v1.util.HttpCallbackListener;
import com.bignerdranch.android.android_client_v1.util.HttpUtil;
import com.bignerdranch.android.android_client_v1.util.Utility;
import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by Administrator on 2016/8/10.
 */
public class AddScenicPolicyFragment extends Fragment {

    private AddScenicPolicyTask mAuthTask = null;
    public static String resultString;
    public Connect2Server c2s = new Conn2ServerImp();

    private WeatherDB mWeatherDB;//数据库操作对象
    private View bt_add_scenicPolicy_OK;
    private EditText scenicname;
    private EditText scenicweather;
    private EditText startdate;
    private EditText enddate;
    private EditText insurednum;
    private EditText insureduty;
    private EditText insured_person;
    private EditText insured_idcard;
    private TextView fee;
    private ImageView mAdd;
    private TextView mCoverage;

    private RadioGroup mRG1;
    private RadioGroup mRG2;

    private RadioButton rb1_1;
    private RadioButton rb1_2;
    private RadioButton rb1_3;

    private RadioButton rb2_1;
    private RadioButton rb2_2;
    private RadioButton rb2_3;

    private View v;

    private ArrayList<Fee> feeLab;

    private static final String ARG_SCENIC_SPOT = "scenic_policy";

    //    private TextView insuredname;
//    private TextView insuredIDcard;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        feeLab = new ArrayList<>();
        super.onCreate(savedInstanceState);
        mWeatherDB = WeatherDB.getInstance(getActivity());//获取数据库处理对象
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
        v = inflater.inflate(R.layout.fragment_add_scenicpolicy, container, false);
        bt_add_scenicPolicy_OK = v.findViewById(R.id.add_scenicPolicy_OK);

        scenicname = (EditText) v.findViewById(R.id.scenicName);
        scenicname.setText(getArguments().getStringArrayList(ARG_SCENIC_SPOT).get(0));
        scenicweather = (EditText) v.findViewById(R.id.scenicWeather);
        startdate = (EditText) v.findViewById(R.id.scenicStartDate);
        enddate = (EditText) v.findViewById(R.id.scenicEndDate);
        fee = (TextView) v.findViewById(R.id.scenicFee);
        mAdd = (ImageView) v.findViewById(R.id.addScenicPolicyMan);

        mRG1 = (RadioGroup) v.findViewById(R.id.rg_1);
        mRG2 = (RadioGroup) v.findViewById(R.id.rg_2);

        rb1_1 = (RadioButton) v.findViewById(R.id.rb_1_1);
        rb1_2 = (RadioButton) v.findViewById(R.id.rb_1_2);
        rb1_3 = (RadioButton) v.findViewById(R.id.rb_1_3);
        rb2_1 = (RadioButton) v.findViewById(R.id.rb_2_1);
        rb2_2 = (RadioButton) v.findViewById(R.id.rb_2_2);
        rb2_3 = (RadioButton) v.findViewById(R.id.rb_2_3);

        mCoverage = (TextView) v.findViewById(R.id.policy_coverage);
        insured_person = (EditText) v.findViewById(R.id.insured_person);
        insured_idcard = (EditText) v.findViewById(R.id.insured_idcard);
        getDate();
        calculatePolicyCoverage();

        final ViewGroup group = (ViewGroup) v.findViewById(R.id.insured_list);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.item_insured, null);
                final View finalView = view;
                view.findViewById(R.id.delete_insured_person).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        group.removeView(finalView);
                    }
                });
                group.addView(view);
            }
        });


        bt_add_scenicPolicy_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String scenicnamestr = scenicname.getText().toString();
                String scenicweatherstr = scenicweather.getText().toString();
                String startdatestr = startdate.getText().toString();
                String enddatestr = enddate.getText().toString();
                String feestr = fee.getText().toString();
                String insuredman = insured_person.getText().toString();
                String insuredmanIDCard = insured_idcard.getText().toString();
                String coverage = deadordis + "";
                String insureduty = medical + "";

//                for (int i = 0; i < group.getChildCount(); i++) {
//                    group.getChildAt(i).findViewById(R.id.insured_person);
//                    group.getChildAt(i).findViewById(R.id.insured_idcard);
//                }


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                int userID = preferences.getInt("id", 0);
                if (userID == 0) {
                    Log.d("test", "没取到用户ID");
                }
                Log.d("test", startdatestr + enddatestr + userID + Double.parseDouble(feestr) + scenicnamestr + scenicweatherstr + insureduty + "生效中" + Integer.parseInt(coverage));
                ScenicPolicy policy = new ScenicPolicy(100, startdatestr, enddatestr, userID, Double.parseDouble(feestr), scenicnamestr, scenicweatherstr, insureduty, "生效中", Integer.parseInt(coverage));
                Log.d("test", policy.toString());

                if (mAuthTask != null) {
                    return;
                }
                Log.d("test", "click the scenic commit button!");
                mAuthTask = new AddScenicPolicyTask(policy, insuredman, insuredmanIDCard);//为后台传递参数
                mAuthTask.execute((Void) null);

            }
        });

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

    private void getFee() {
        GetFeeTask mAuthTask;
        Log.d("test", "getFee");
        mAuthTask = new GetFeeTask(scenicname.getText().toString(), (scenicweather.getText().toString().contains("雨") || scenicweather.getText().toString().contains("雪")) ? "2" : "1");//为后台传递参数
        mAuthTask.execute((Void) null);
    }


    private void getDate() {
        final Calendar c = Calendar.getInstance();
        final Calendar start = Calendar.getInstance();
        final Calendar end = Calendar.getInstance();
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                                start.set(year, monthOfYear, dayOfMonth);
                                CharSequence date = DateFormat.format("yyy-MM-dd", start);
                                startdate.setText(date);
                                try {
                                    if (inSevenDays(date)) {
                                        queryWeatherInfo(getArguments().getStringArrayList(ARG_SCENIC_SPOT).get(1), date + "");
                                    } else {
                                        scenicweather.setText("暂无天气预报");
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (end.getTimeInMillis() < start.getTimeInMillis()) {
                                    enddate.setText("");
                                }
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 604800000);
                dialog.show();
            }
        });
        final Calendar d = Calendar.getInstance();

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                end.set(year, monthOfYear, dayOfMonth);
                                CharSequence date = DateFormat.format("yyy-MM-dd", end);
                                enddate.setText(date);
                                if (end.getTimeInMillis() < start.getTimeInMillis()) {
                                    enddate.setText("");
                                }
                            }
                        },
                        d.get(Calendar.YEAR),
                        d.get(Calendar.MONTH),
                        d.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(start.getTimeInMillis());
                dialog.show();
            }
        });

        if (end.getTimeInMillis() < start.getTimeInMillis()) {
            enddate.setText("");
        }
    }

    private int deadordis = 100000, medical = 5000;
    //private String[] flag;

    /*计算保额*/
    private void calculatePolicyCoverage() {
        update();
        mRG1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) getView().findViewById(i);
                deadordis = Integer.parseInt(rb.getText().toString());
                Log.d("test", rb.getText().toString() + " " + deadordis);
//                if (rb1_1.getId() == i) {
//                    flag[0] = "a";
//                } else if (rb1_2.getId() == i) {
//                    flag[0] = "b";
//                } else if (rb1_3.getId() == i) {
//                    flag[0] = "c";
//                }
                update();
            }
        });

        mRG2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) getView().findViewById(i);
                medical = Integer.parseInt(rb.getText().toString());
                Log.d("test", rb.getText().toString() + " " + medical);
//                if (rb2_1.getId() == i) {
//                    flag[1] = "a";
//                } else if (rb2_2.getId() == i) {
//                    flag[1] = "b";
//                } else if (rb2_3.getId() == i) {
//                    flag[1] = "c";
//                }
                update();
            }
        });
    }

    /*计算保费*/
    private float calculatePolicyFee(int deadordis, int medical) {
        if (feeLab != null) {
            Iterator it = feeLab.iterator();
            Fee fee;
            while (it.hasNext()) {
                fee = (Fee) it.next();
                if ((Integer.parseInt(fee.getDeadordis()) == deadordis) && (Integer.parseInt(fee.getMedical()) == medical)) {
                    return Float.parseFloat(fee.getFee());
                }
            }
        }
        Toast.makeText(getContext(), "保费查询中，请稍后", Toast.LENGTH_SHORT);
        return 0;
    }

    public void update() {
        Log.d("test", deadordis + "" + medical + calculatePolicyFee(deadordis, medical) + "计算保费");
        mCoverage.setText(deadordis + medical + "");
        fee.setText(calculatePolicyFee(deadordis, medical) + "");

//
//        String sum = flag[0] + flag[1];
//        switch (sum) {
//            case "aa":
//                mCoverage.setText("105000");
//                break;
//            case "ab":
//                mCoverage.setText("110000");
//                break;
//            case "ac":
//                mCoverage.setText("115000");
//                break;
//            case "ba":
//                mCoverage.setText("205000");
//                break;
//            case "bb":
//                mCoverage.setText("210000");
//                break;
//            case "bc":
//                mCoverage.setText("215000");
//                break;
//            case "ca":
//                mCoverage.setText("305000");
//                break;
//            case "cb":
//                mCoverage.setText("310000");
//                break;
//            case "cc":
//                mCoverage.setText("315000");
//                break;
//        }
    }

    public class AddScenicPolicyTask extends AsyncTask<Void, Void, String> {

        ScenicPolicy mScenicPolicy = null;
        String mInsuredMan = null;
        String mIDCard = null;

        AddScenicPolicyTask(ScenicPolicy scenicPolicy, String insuredMan, String IDCard) {
            this.mScenicPolicy = scenicPolicy;
            this.mInsuredMan = insuredMan;
            this.mIDCard = IDCard;

        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("test", "in to do addpolicy in background!");
            try {
                // Simulate network access.
                resultString = c2s.addScenicPolicy(mScenicPolicy, mInsuredMan, mIDCard);

                Log.d("test", "add scenic policy resultString=" + resultString);
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
            Log.d("test", "in to on PostExecute!");

            if (result != null) {
                Log.d("test", result);
                try {
                    mScenicPolicy.setPolicyID(Integer.parseInt(result));
                    PolicyLab policyList = PolicyLab.get(result);
                    BasePolicy newPolicy = new BasePolicy(mScenicPolicy.getFee(), mScenicPolicy.getPolicyID(), "景区意外险", "生效中", mScenicPolicy.getScenicname());
                    policyList.addPolicy(newPolicy);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "购买成功", Toast.LENGTH_SHORT);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                preferences.edit()
                        .putBoolean("scenicSpotView", false)   //景区意外险
                        .putString("scenic_spot_city", null)
                        .putString("scenic_spot_name", null)
                        .apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("des", 3);
                startActivity(intent);

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

    public class GetFeeTask extends AsyncTask<Void, Void, String> {

        String scenicName, weather;

        GetFeeTask(String scenicname, String weather) {
            this.scenicName = scenicname;
            this.weather = weather;
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.d("test", "in to do get fee in background!");
            try {
                // Simulate network access.
                resultString = c2s.getFee(scenicName, weather);
                Log.d("test", "航班延误险的get fee的 resultString=" + resultString);
                return resultString;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Nothing";

        }

        @Override
        protected void onPostExecute(final String result) {
            //showProgress(false);
            Log.d("test", "in to on PostExecute!");

            if (result != null) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Fee fee = new Fee(jsonArray.getJSONObject(i).getString("deadordis"),
                                jsonArray.getJSONObject(i).getString("medical"),
                                jsonArray.getJSONObject(i).getString("fee"));
                        feeLab.add(fee);
                    }
                    update();
                    Log.d("test", "addFee" + feeLab.size());
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

            // showProgress(false);
        }
    }


    /*获取天气*/
    public boolean inSevenDays(CharSequence date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date1 = simpleDateFormat.parse(date + "");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        Calendar now = Calendar.getInstance();
        Log.d("debug", calendar + " " + now);
        long hours = (calendar.getTimeInMillis() - now.getTimeInMillis()) / (60 * 60 * 1000);
        long days = (hours + 24) / 24;

        Log.d("debug", days + "");
        if (days > 6) {
            Toast.makeText(getContext(), "无法预报天气", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**TODO
     * 监听mFilghtTime控件改变或者另外设置按钮触发查询天气
     */
    /**
     * 查询天气代号所对应的天气。
     */
    private void queryWeatherInfo(String cityName, String date/*日期用字符串?Date?*/) {
        String weatherCode = mWeatherDB.loadCitiesByName(cityName).getCity_code();

        Log.d("policy", "cityName" + cityName + "date" + date);
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
                            scenicweather.setText(prefs.getString("txt_d", "变化前") + "转" + prefs.getString("txt_n", "变化后"));//设置天气
                            getFee();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scenicweather.setText("查询天气失败");
                    }
                });
            }
        });
    }
}


/*
SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            preferences.edit()
                    .putBoolean("scenicSpotView", false)   //航空延误险
                    .putString("scenic_spot_city", null)
                    .putString("scenic_spot_name", null)
                    .apply();
 */