package com.bignerdranch.android.android_client_v1.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.TextView;

import com.bignerdranch.android.android_client_v1.MainActivity;
import com.bignerdranch.android.android_client_v1.R;
import com.bignerdranch.android.android_client_v1.model.FlightPolicy;
import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

import java.util.Calendar;

public class AddFlightPolicyFragment extends Fragment {

    private AddFlightPolicyTask mAuthTask = null;
    public static String resultString;
    public Connect2Server c2s = new Conn2ServerImp();

    private EditText mFlightDate;
    private EditText mFlightId;
    private EditText mFlightRoute;
    private EditText mFlightTime;
    private EditText mFlightWeather;
    private Button mFlightGetWeather;
    private CheckBox mOneHour;
    private CheckBox mFourHours;
    private CheckBox mCancelFlight;
    private TextView mFlightCoverage;
    private TextView mFlightFee;
    private View add_flightPolicy_OK;

    private int coverage = 0;
    private int[] flag = {0, 0, 0};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_flightpolicy, container, false);

        mFlightDate = (EditText) v.findViewById(R.id.flight_date);
        mFlightId = (EditText) v.findViewById(R.id.flight_id);
        mFlightRoute = (EditText) v.findViewById(R.id.flight_route);
        mFlightTime = (EditText) v.findViewById(R.id.flight_time);
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

                String flight_date_str = mFlightDate.getText().toString();
                String flight_id_str = mFlightId.getText().toString();
                String flight_time_str = mFlightTime.getText().toString();
                String flight_route_str = mFlightRoute.getText().toString();
                String flight_weather_str = mFlightWeather.getText().toString();
                int flight_cb_str = flag[0] + flag[1] + flag[2];
                String flight_coverage_str = mFlightCoverage.getText().toString();
                String policyfee_str = mFlightFee.getText().toString();

                FlightPolicy policy = new FlightPolicy(
                        1233333, 1111,
                        flight_date_str, Integer.parseInt(flight_id_str),
                        flight_route_str, flight_time_str, flight_weather_str, flight_cb_str,
                        Integer.parseInt(flight_coverage_str), Double.parseDouble(policyfee_str)
                );


                if (mAuthTask != null) {
                    return;
                }
                Log.d("test", "click the commit button!");
                mAuthTask = new AddFlightPolicyTask(policy);//为后台传递参数
                mAuthTask.execute((Void) null);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
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
                                mFlightDate.setText(DateFormat.format("yyy-MM-dd", c));
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
            Log.d("test", "in to do addpolicy in background!");
            try {
                // Simulate network access.
                resultString = c2s.addFlightPolicy(mFlightPolicy);

                Log.d("test", "resultString=" + resultString);
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
                Intent intent = new Intent(getActivity(), MainActivity.class);
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
}
