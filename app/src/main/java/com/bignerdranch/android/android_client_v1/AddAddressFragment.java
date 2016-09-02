package com.bignerdranch.android.android_client_v1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

public class AddAddressFragment extends Fragment implements View.OnClickListener{

    private AddAddressTask mAddAddewssTask = null;
    //public static String resultString;
    public Connect2Server c2s=new Conn2ServerImp();

    private View bt_add_address_OK;
    private EditText receivename;
    private EditText telephone;
    private EditText mailcode;
    private EditText district;
    private EditText detaildis;

    private View mProgressView;
    private View mLoginFormView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_address, container, false);
        bt_add_address_OK=v.findViewById(R.id.add_address_OK);
        receivename=(EditText)v.findViewById(R.id.tv_address_name);
        telephone=(EditText)v.findViewById(R.id.tv_address_num);
        mailcode=(EditText)v.findViewById(R.id.tv_address_post_number);
        district=(EditText)v.findViewById(R.id.tv_address_district);
        detaildis=(EditText)v.findViewById(R.id.tv_address_detail);



        mLoginFormView = v.findViewById(R.id.login_form);
        mProgressView = v.findViewById(R.id.login_progress);

        bt_add_address_OK.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.add_address_OK:
            //保存；
            String receivenamestr=receivename.getText().toString();
            String telephonestr=telephone.getText().toString();
            String mailcodestr=mailcode.getText().toString();
            String districtstr=district.getText().toString();
            String detaildisstr=detaildis.getText().toString();
            if (mAddAddewssTask != null) {
                return;
            }
            showProgress(true);
            mAddAddewssTask = new AddAddressTask(receivenamestr,telephonestr,mailcodestr,districtstr,detaildisstr);//为后台传递参数
            mAddAddewssTask.execute((Void) null);

       }
    }

    public class AddAddressTask extends AsyncTask<Void, Void, String> {

        String mReceivenamestr;
        String mTelephonestr;
        String mMailcodestr;
        String mDistrictstr;
        String mDetaildisstr;

        AddAddressTask(String Receivenamestr, String Telephonestr, String Mailcodestr, String Districtstr, String Detaildisstr) {
            mReceivenamestr=Receivenamestr;
            mTelephonestr=Telephonestr;
            mMailcodestr=Mailcodestr;
            mDistrictstr=Districtstr;
            mDetaildisstr=Detaildisstr;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("test","in to do addaddress in background!");
            try {
                // Simulate network access.
                String resultString = c2s.addAddress(mReceivenamestr,mTelephonestr,mMailcodestr,mDistrictstr,mDetaildisstr);
                Log.d("test","resultString="+resultString);
                return resultString;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Noting";

        }

        @Override
        protected void onPostExecute(final String result) {
            mAddAddewssTask = null;
            showProgress(false);
            Log.d("test","in to on PostExecute!");

            if (result!=null) {
                Log.d("test",result);
                Intent intent=new Intent(getActivity(),MyInfoActivity.class);
                startActivity(intent);

            } else {
                Log.d("test","return nothing!");
                //getActivity().finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAddAddewssTask = null;
            showProgress(false);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}