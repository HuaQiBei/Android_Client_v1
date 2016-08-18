package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MineFragment extends Fragment implements View.OnClickListener {

    private View user;

    private static final int REQUEST_POLICY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        user = view.findViewById(R.id.username);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.view_policy).setOnClickListener(this);
        view.findViewById(R.id.view_policy2).setOnClickListener(this);
        view.findViewById(R.id.view_policy3).setOnClickListener(this);
        view.findViewById(R.id.view_policy4).setOnClickListener(this);
        view.findViewById(R.id.view_policy5).setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) getActivity()).setCurrentTabByTag("保单");
        switch (v.getId()) {
            case R.id.view_policy: {
                ((MainActivity) getActivity()).setPolicyTitle(0);
                break;
            }
            case R.id.view_policy2: {
                ((MainActivity) getActivity()).setPolicyTitle(1);
                break;
            }
            case R.id.view_policy3: {
                ((MainActivity) getActivity()).setPolicyTitle(2);
                break;
            }
            case R.id.view_policy4: {
                ((MainActivity) getActivity()).setPolicyTitle(3);
                break;
            }
            case R.id.view_policy5: {
                ((MainActivity) getActivity()).setPolicyTitle(4);
                break;
            }
        }
    }
}
