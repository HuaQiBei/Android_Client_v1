package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

public class LifeDetailFragment extends Fragment {

    private static final String ARG_LIFE_ID = "life_id";

    private Life mLife;
//    private View bt_addressSave;
//    private View bt_addAddress;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID lifeId = (UUID) getArguments().getSerializable(ARG_LIFE_ID);
        mLife = new Life();
        mLife.setTitle("设置为西安七日游");
    }

    public static LifeDetailFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIFE_ID, crimeId);

        LifeDetailFragment fragment = new LifeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_life_detail,container,false);
//        bt_addAddress=v.findViewById(R.id.AddAddress);
//
//        bt_addressSave=v.findViewById(R.id.addressSave);
//        bt_addressSave.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),MyInfoActivity.class);
//                startActivity(intent);
//            }
//        });
//        bt_addAddress.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),AddAddressActivity.class);
//                startActivity(intent);
//            }
//        });

        return v;
}


}
