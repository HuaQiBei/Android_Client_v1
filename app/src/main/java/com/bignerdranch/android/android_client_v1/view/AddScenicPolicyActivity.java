package com.bignerdranch.android.android_client_v1.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.bignerdranch.android.android_client_v1.SingleFragmentActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AddScenicPolicyActivity extends SingleFragmentActivity {
    private static final String EXTRA_SCENIC_POLICY = "scenic_policy";

    public static Intent newIntent(Context packageContext, ArrayList<String> par){
        Intent intent = new Intent(packageContext, AddScenicPolicyActivity.class);
        intent.putExtra(EXTRA_SCENIC_POLICY, par);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        ArrayList<String> par = getIntent().getStringArrayListExtra(EXTRA_SCENIC_POLICY);
        return AddScenicPolicyFragment.newInstance(par);
    }
}
