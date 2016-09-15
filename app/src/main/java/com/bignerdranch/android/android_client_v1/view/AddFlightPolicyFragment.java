package com.bignerdranch.android.android_client_v1.view;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.android_client_v1.MainActivity;
import com.bignerdranch.android.android_client_v1.R;
import com.bignerdranch.android.android_client_v1.db.WeatherDB;
import com.bignerdranch.android.android_client_v1.model.BasePolicy;
import com.bignerdranch.android.android_client_v1.model.FlightPolicy;
import com.bignerdranch.android.android_client_v1.model.PolicyLab;
import com.bignerdranch.android.android_client_v1.util.HttpCallbackListener;
import com.bignerdranch.android.android_client_v1.util.HttpUtil;
import com.bignerdranch.android.android_client_v1.util.Utility;
import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFlightPolicyFragment extends Fragment {

    private WeatherDB mWeatherDB;//数据库操作对象


    private AddFlightPolicyTask mFlightTask = null;
    public String resultString;
    public Connect2Server c2s = new Conn2ServerImp();
    JSONObject delayRate;

    private EditText mFlightDate;
    private EditText mFlightId;
    private EditText mFlightRoute;
    private EditText mFlightWeather;
    private CheckBox mOneHour;
    private CheckBox mFourHours;
    private CheckBox mCancelFlight;
    private TextView mFlightCoverage;
    private TextView mFlightFee;
    private TextView flight_fee;
    private ImageView mAdd;
    private View add_flightPolicy_OK;
    private static final String ARG_FLIGHT_DAILAY_POLICY = "flight_policy";
    private ArrayList<String> par;
    private String delayData;
    private EditText insured_person;
    private EditText insured_idcard;

    public static AddFlightPolicyFragment newInstance(ArrayList<String> par, String data) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_FLIGHT_DAILAY_POLICY, par);
        args.putString("data", data);
        AddFlightPolicyFragment fragment = new AddFlightPolicyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWeatherDB = WeatherDB.getInstance(getActivity());//获取数据库处理对象

        par = getArguments().getStringArrayList(ARG_FLIGHT_DAILAY_POLICY);
        String delayData = getArguments().getString("data");
        try {
            delayRate = new JSONObject(delayData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("delay", delayData);
        Log.d("policy", par.get(0));//航班号
        Log.d("policy", par.get(1));//起点
        Log.d("policy", par.get(2));//终点

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
                            mFlightWeather.setText(prefs.getString("txt_d", "变化前") + "转" + prefs.getString("txt_n", "变化后"));//设置天气
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFlightWeather.setText("查询天气失败");
                    }
                });
            }
        });
    }



    private int coverage = 0;
    private int[] flag = {0, 0, 0};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_flightpolicy, container, false);

        mFlightDate = (EditText) v.findViewById(R.id.flight_date);
        mFlightId = (EditText) v.findViewById(R.id.flight_id);
        mFlightId.setText(par.get(0));
        mFlightRoute = (EditText) v.findViewById(R.id.flight_route);
        mFlightRoute.setText(par.get(1) + " " + par.get(2));
        mFlightWeather = (EditText) v.findViewById(R.id.flight_weather);
        mOneHour = (CheckBox) v.findViewById(R.id.one_hour_checked);
        mFourHours = (CheckBox) v.findViewById(R.id.four_hour_checked);
        mCancelFlight = (CheckBox) v.findViewById(R.id.flight_cancel_checked);
        mFlightCoverage = (TextView) v.findViewById(R.id.policy_coverage);
        mFlightFee = (TextView) v.findViewById(R.id.policyfee);
        flight_fee = (TextView) v.findViewById(R.id.flight_fee);
        add_flightPolicy_OK = v.findViewById(R.id.add_fightPolicy_OK);
        insured_person = (EditText) v.findViewById(R.id.insured_person);
        insured_idcard = (EditText) v.findViewById(R.id.insured_idcard);

        getDate();
        calculatePolicyCoverage();

        mAdd = (ImageView) v.findViewById(R.id.addFlightPolicyMan);
        final ViewGroup group = (ViewGroup) v.findViewById(R.id.insured_list);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.item_insured, container , false);
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

        add_flightPolicy_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "click the flight commit button!");
                String flight_date_str = mFlightDate.getText().toString();
                String flight_id_str = mFlightId.getText().toString();
                String flight_route_str = mFlightRoute.getText().toString();
                String flight_weather_str = mFlightWeather.getText().toString();
                int flight_cb_str = flag[0] + flag[1] + flag[2];
                String flight_coverage_str = mFlightCoverage.getText().toString();
                String policyfee_str = mFlightFee.getText().toString();
                String insuredman=insured_person.getText().toString();
                String insuredIDCard=insured_idcard.getText().toString();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                FlightPolicy policy = new FlightPolicy(
                        100, preferences.getInt("id", 0),
                        flight_date_str, flight_id_str,
                        flight_route_str, flight_weather_str, flight_cb_str,
                        Integer.parseInt(flight_coverage_str), Double.parseDouble(policyfee_str)
                        , "生效中",insuredman,insuredIDCard);

                if (mFlightTask != null) {
                    return;
                }
                mFlightTask = new AddFlightPolicyTask(policy);//为后台传递参数
                mFlightTask.execute((Void) null);


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

    /*获取航班日期*/
    private void getDate() {
        final Calendar c = Calendar.getInstance();
        mFlightDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, monthOfYear, dayOfMonth);
                                CharSequence date = DateFormat.format("yyy-MM-dd", c);
                                mFlightDate.setText(date);
                                try {
                                    if (inSevenDays(date)) {
                                        queryWeatherInfo(par.get(1), date + "");
                                    } else {
                                        mFlightWeather.setText("暂无天气预报");
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });
    }

    /*计算保额*/
    private void calculatePolicyCoverage() {
        mOneHour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mOneHour.isChecked()) {
                    flag[0] = 1;
                } else {
                    flag[0] = 0;
                }
                try {
                    update();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mFourHours.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mFourHours.isChecked()) {
                    flag[1] = 2;
                } else {
                    flag[1] = 0;
                }
                try {
                    update();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mCancelFlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCancelFlight.isChecked()) {
                    flag[2] = 4;
                } else {
                    flag[2] = 0;
                }
                try {
                    update();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void update() throws JSONException {
        switch (flag[0] + flag[1] + flag[2]) {
            case 1:
                mFlightCoverage.setText("200");
                mFlightFee.setText(delayRate.getString("12"));
                flight_fee.setText(delayRate.getString("12"));
                break;
            case 2:
                mFlightCoverage.setText("400");
                mFlightFee.setText(delayRate.getString("13"));
                flight_fee.setText(delayRate.getString("13"));
                break;
            case 3:
                mFlightCoverage.setText("600");
                mFlightFee.setText(delayRate.getString("17"));
                flight_fee.setText(delayRate.getString("17"));
                break;
            case 4:
                mFlightCoverage.setText("200");
                mFlightFee.setText(delayRate.getString("14"));
                flight_fee.setText(delayRate.getString("14"));
                break;
            case 5:
                mFlightCoverage.setText("200");
                mFlightFee.setText(delayRate.getString("15"));
                flight_fee.setText(delayRate.getString("15"));
                break;
            case 6:
                mFlightCoverage.setText("400");
                mFlightFee.setText(delayRate.getString("16"));
                flight_fee.setText(delayRate.getString("16"));
                break;
            case 7:
                mFlightCoverage.setText("600");
                mFlightFee.setText(delayRate.getString("18"));
                flight_fee.setText(delayRate.getString("18"));
                break;
            default:
                mFlightCoverage.setText("0");
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public class AddFlightPolicyTask extends AsyncTask<Void, Void, String> {

        FlightPolicy mFlightPolicy = null;

        AddFlightPolicyTask(FlightPolicy flightPolicy) {
            this.mFlightPolicy = flightPolicy;

        }

        @Override
        protected String doInBackground(Void... params) {
            Log.d("test", "in to do add flight policy in background!");
            try {
                // Simulate network access.
                resultString = c2s.addFlightPolicy(mFlightPolicy);

                Log.d("test", "航班延误险的resultString=" + resultString);
                return resultString;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Nothing";

        }

        @Override
        protected void onPostExecute(final String result) {
            mFlightTask = null;
            //showProgress(false);
            Log.d("test", "in to on PostExecute!");

            if (result != null) {
                Log.d("test", result);
                try {
                    mFlightPolicy.setPolicyID(Integer.parseInt(result));
                    PolicyLab policyList = PolicyLab.get(result);
                    BasePolicy newPolicy = new BasePolicy(mFlightPolicy.getFlightFee(), mFlightPolicy.getPolicyID(), "航班延误险", "生效中", mFlightPolicy.getFlightRoute());
                    policyList.addPolicy(newPolicy);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getContext(), "购买成功", Toast.LENGTH_SHORT);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                preferences.edit()
                        .putBoolean("flightDelayView", false)   //航班延误险
                        .putString("flightNo", null)
                        .putString("flightStartCity", null)
                        .putString("flightEndCity", null)
                        .apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            } else {
                Log.d("test", "return nothing!");
                //getActivity().finish();
            }
        }

        @Override
        protected void onCancelled() {
            mFlightTask = null;
            // showProgress(false);
        }
    }

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
}
