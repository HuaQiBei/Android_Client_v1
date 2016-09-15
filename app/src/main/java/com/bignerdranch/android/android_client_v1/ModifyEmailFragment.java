package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

public class ModifyEmailFragment extends Fragment implements View.OnClickListener {

    private ModifyEmailTask mAuthTask = null;
    public static String resultString;
    public Connect2Server c2s = new Conn2ServerImp();


    private View bt_emailSave;
    private View et_newEmail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modify_email, container, false);

        bt_emailSave = v.findViewById(R.id.emailSave);
        et_newEmail = v.findViewById(R.id.newEmail);

        bt_emailSave.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.emailSave:
                String newemailstr = ((EditText) et_newEmail).getText().toString();
                if (mAuthTask != null) {
                    return;
                }

                mAuthTask = new ModifyEmailTask(newemailstr);//为后台传递参数
                mAuthTask.execute((Void) null);

                getActivity().finish();

        }
    }

    public class ModifyEmailTask extends AsyncTask<Void, Void, String> {

        String mNewemailstr;


        ModifyEmailTask(String Newemailstr) {
            mNewemailstr = Newemailstr;

        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("test", "in to do mpdify email in background!");
            try {
                // Simulate network access.
                resultString = c2s.modifyEmail(mNewemailstr);
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

