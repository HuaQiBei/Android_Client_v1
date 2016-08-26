package com.bignerdranch.android.android_client_v1.view;

import android.support.v4.app.Fragment;

import com.bignerdranch.android.android_client_v1.SingleFragmentActivity;

public class AddFlightPolicyActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new AddFlightPolicyFragment();
    }
}
