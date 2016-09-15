package com.bignerdranch.android.android_client_v1.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bignerdranch.android.android_client_v1.SingleFragmentActivity;

/**
 * Created by DELL on 2016/9/14.
 */
public class ObtainPayActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Bundle args = new Bundle();
        args.putInt("policyID", getIntent().getIntExtra("policyID", 0));
        args.putString("policyName", getIntent().getStringExtra("policyName"));
        Fragment fragment = new ObtainPayFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
