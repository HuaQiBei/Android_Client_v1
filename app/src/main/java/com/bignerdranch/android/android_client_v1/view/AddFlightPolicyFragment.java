package com.bignerdranch.android.android_client_v1.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.android_client_v1.db.WeatherDB;
import com.bignerdranch.android.android_client_v1.model.BasePolicy;
import com.bignerdranch.android.android_client_v1.model.PolicyLab;
import com.bignerdranch.android.android_client_v1.util.HttpCallbackListener;
import com.bignerdranch.android.android_client_v1.util.HttpUtil;
import com.bignerdranch.android.android_client_v1.util.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.bignerdranch.android.android_client_v1.MainActivity;
import com.bignerdranch.android.android_client_v1.R;
import com.bignerdranch.android.android_client_v1.model.FlightPolicy;
import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

import org.json.JSONException;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFlightPolicyFragment extends Fragment {

    private WeatherDB mWeatherDB;//数据库操作对象


    private AddFlightPolicyTask mFlightTask = null;
    public static String resultString;
    public Connect2Server c2s = new Conn2ServerImp();

    private EditText mFlightDate;
    private EditText mFlightId;
    private EditText mFlightRoute;
    private EditText mFlightWeather;
    private Button mFlightGetWeather;
    private CheckBox mOneHour;
    private CheckBox mFourHours;
    private CheckBox mCancelFlight;
    private TextView mFlightCoverage;
    private TextView mFlightFee;
    private View add_flightPolicy_OK;
    private static final String ARG_FLIGHT_DAILAY_POLICY = "flight_policy";
    private ArrayList<String> par;

    public static AddFlightPolicyFragment newInstance(ArrayList<String> par) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_FLIGHT_DAILAY_POLICY, par);
        AddFlightPolicyFragment fragment = new AddFlightPolicyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWeatherDB = WeatherDB.getInstance(getActivity());//获取数据库处理对象
        //先检查本地是否已同步过城市数据，如果没有，则从服务器同步
        if (mWeatherDB.checkDataState() == 0) {
            Log.d("policy", "没有All城市列表");
            queryCitiesFromServer();
        }

        par = getArguments().getStringArrayList(ARG_FLIGHT_DAILAY_POLICY);
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

    //从服务器取出所有的城市信息
    private void queryCitiesFromServer() {
        String address = "https://api.heweather.com/x3/citylist?search=allchina&key=" + WeatherActivity.WEATHER_KEY;
        MyProgressDialog.showProgressDialog(getActivity());
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if (Utility.handleAllCityResponse(mWeatherDB, response)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyProgressDialog.closeProgressDialog();
                            mWeatherDB.updateDataState();
                        }
                    });
                }
            }

            @Override
            public void onError(final Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyProgressDialog.closeProgressDialog();
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT);
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
        mFlightGetWeather = (Button) v.findViewById(R.id.get_flight_weather);
        mOneHour = (CheckBox) v.findViewById(R.id.one_hour_checked);
        mFourHours = (CheckBox) v.findViewById(R.id.four_hour_checked);
        mCancelFlight = (CheckBox) v.findViewById(R.id.flight_cancel_checked);
        mFlightCoverage = (TextView) v.findViewById(R.id.policy_coverage);
        mFlightFee = (TextView) v.findViewById(R.id.policyfee);
        add_flightPolicy_OK = v.findViewById(R.id.add_fightPolicy_OK);

        getDate();
        calculatePolicyCoverage();


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
                FlightPolicy policy = new FlightPolicy(
                        77, 13,
                        flight_date_str, flight_id_str,
                        flight_route_str, flight_weather_str, flight_cb_str,
                        Integer.parseInt(flight_coverage_str), Double.parseDouble(policyfee_str)
                        , "生效中");

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
                                    }else {
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
                update();
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
                update();
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
                update();
            }
        });

    }

    public void update() {
        switch (flag[0] + flag[1] + flag[2]) {
            case 1:
                mFlightCoverage.setText("200");
                break;
            case 2:
                mFlightCoverage.setText("400");
                break;
            case 3:
                mFlightCoverage.setText("600");
                break;
            case 4:
                mFlightCoverage.setText("200");
                break;
            case 5:
                mFlightCoverage.setText("200");
                break;
            case 6:
                mFlightCoverage.setText("400");
                break;
            case 7:
                mFlightCoverage.setText("600");
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
            // TODO: attempt authentication against a network service.
            Log.d("test", "in to do add flight policy in background!");
            try {
                // Simulate network access.
                resultString = c2s.addFlightPolicy(mFlightPolicy);

                Log.d("test", "航班延误险的resultString=" + resultString);
                return resultString;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Noting";

        }

        @Override
        protected void onPostExecute(final String result) {
            mFlightTask = null;
            //showProgress(false);
            Log.d("test", "in to on PostExecute!");

            if (result != null) {
                Log.d("test", result);
                try {
                    PolicyLab policyList = PolicyLab.get(result);
                    BasePolicy newPolicy = new BasePolicy(mFlightPolicy.getFlightFee(), mFlightPolicy.getPolicyID(), "航班延误险", "生效中", mFlightPolicy.getFlightRoute());
                    policyList.addPolicy(newPolicy);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
