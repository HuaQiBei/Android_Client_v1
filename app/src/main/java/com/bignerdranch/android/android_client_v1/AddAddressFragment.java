package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddAddressFragment extends Fragment implements View.OnClickListener{
    private View bt_add_address_OK;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_address, container, false);
        bt_add_address_OK=v.findViewById(R.id.add_address_OK);
        bt_add_address_OK.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.add_address_OK:
            //保存；
            Intent intent=new Intent(getActivity(),MyInfoActivity.class);
            startActivity(intent);
    }
    }
}