package com.bignerdranch.android.android_client_v1;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/8/10.
 */
public class AddPolicyActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new AddPolicyFragment();
    }
}
