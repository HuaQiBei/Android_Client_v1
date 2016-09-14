package com.bignerdranch.android.android_client_v1.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.android_client_v1.R;

/**
 * Created by DELL on 2016/9/14.
 */
public class ObtainPayFragment extends Fragment {

    private TextView policy_NO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_obtain_pay, container, false);
        policy_NO = (TextView)v.findViewById(R.id.policy_NO);
        policy_NO.setText(getArguments().getInt("policyID")+"");
        return v;
    }
}
