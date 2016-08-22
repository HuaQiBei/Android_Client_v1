package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddressFragment extends Fragment {

    private View bt_addressSave;
    private View bt_addAddress;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_address,container,false);
        bt_addAddress=v.findViewById(R.id.AddAddress);

        bt_addressSave=v.findViewById(R.id.addressSave);
        bt_addressSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyInfoActivity.class);
                startActivity(intent);
            }
        });
        bt_addAddress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddAddressActivity.class);
                startActivity(intent);
            }
        });

        return v;
}


}
