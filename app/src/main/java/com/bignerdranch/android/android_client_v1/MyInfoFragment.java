package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyInfoFragment extends Fragment {

    private View head;
    private View name;
    private View reset;
    private View phone;
    private View email;
    private View address;
    private View other;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("我的资料");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo, container, false);
        head = view.findViewById(R.id.head);
        name = view.findViewById(R.id.name);
        reset = view.findViewById(R.id.reset);
        phone = view.findViewById(R.id.phone);
        email =view.findViewById(R.id.email);
        address = view.findViewById(R.id.address);
        other = view.findViewById(R.id.other);

        head.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HeadActivity.class);
                startActivity(intent);
            }
        });
        name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NameActivity.class);
                startActivity(intent);
            }
        });
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ResetActivity.class);
                startActivity(intent);

            }
        });
        phone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PhoneActivity.class);
                startActivity(intent);
            }
        });
        email.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ModifyEmailActivity.class);
                startActivity(intent);
            }
        });
        address.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddressActivity.class);
                startActivity(intent);
            }
        });
        other.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ContactActivity.class);
                startActivity(intent);
            }
        });
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    private void updateUI() {

    }


}
