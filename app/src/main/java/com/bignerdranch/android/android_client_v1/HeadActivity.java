package com.bignerdranch.android.android_client_v1;

import android.support.v4.app.Fragment;

public class HeadActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new HeadFragment();
    }
}
