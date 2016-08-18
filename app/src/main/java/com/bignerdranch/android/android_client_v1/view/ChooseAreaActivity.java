package com.bignerdranch.android.android_client_v1.view;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.bignerdranch.android.android_client_v1.SingleFragmentActivity;

/**
 * Created by DELL on 2016/8/18.
 */
public class ChooseAreaActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ChooseAreaFragment();
    }
}
