package com.bignerdranch.android.android_client_v1.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bignerdranch.android.android_client_v1.SingleFragmentActivity;

import java.util.ArrayList;

public class AddFlightPolicyActivity extends SingleFragmentActivity {

    private static final String EXTRA_FLIGHT_DALAY_POLICY = "flight_policy";

    public static Intent newIntent(Context packageContext, ArrayList<String> par, String data) {
        Intent intent = new Intent(packageContext, AddFlightPolicyActivity.class);
        intent.putExtra(EXTRA_FLIGHT_DALAY_POLICY, par);
        intent.putExtra("data", data);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        ArrayList<String> par = getIntent().getStringArrayListExtra(EXTRA_FLIGHT_DALAY_POLICY);
        String data = getIntent().getStringExtra("data");
        return AddFlightPolicyFragment.newInstance(par, data);
    }
}
