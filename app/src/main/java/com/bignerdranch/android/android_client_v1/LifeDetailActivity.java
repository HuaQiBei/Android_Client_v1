package com.bignerdranch.android.android_client_v1;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.UUID;

public class LifeDetailActivity extends SingleFragmentActivity {


    private static final String EXTRA_LIFE_ID = "com.bignerdranch.android.android_client_v1.life_id";
    @Override
    protected Fragment createFragment() {
        UUID lifeId = (UUID)getIntent().getSerializableExtra(EXTRA_LIFE_ID);
        return LifeDetailFragment.newInstance(lifeId);
    }
    public static Intent newIntent(Context packageContext, UUID lifeId){
        Intent intent = new Intent(packageContext,LifeDetailActivity.class);
        intent.putExtra(EXTRA_LIFE_ID,lifeId);
        return intent;
    }
}
