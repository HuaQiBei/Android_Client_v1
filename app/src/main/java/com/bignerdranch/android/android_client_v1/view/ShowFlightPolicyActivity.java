package com.bignerdranch.android.android_client_v1.view;

import android.support.v4.app.Fragment;

import com.bignerdranch.android.android_client_v1.SingleFragmentActivity;


/**
 * Created by Administrator on 2016/8/22.
 */
public class ShowFlightPolicyActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ShowFlightPolicyFragment();
    }
}

