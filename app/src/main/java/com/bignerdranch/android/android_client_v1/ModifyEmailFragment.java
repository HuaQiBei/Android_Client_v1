package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ModifyEmailFragment extends Fragment implements View.OnClickListener{

    private View bt_emailSave;
    private View et_newEmail;
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_modify_email,container,false);

        bt_emailSave=v.findViewById(R.id.emailSave);
        et_newEmail=v.findViewById(R.id.newEmail);

        bt_emailSave.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.emailSave:
                 Intent intent=new Intent(getActivity(),MyInfoActivity.class);
               startActivity(intent);

        }
    }
}

