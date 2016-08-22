package com.bignerdranch.android.android_client_v1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class NameFragment extends Fragment implements View.OnClickListener{

    private Button save;
    private EditText input;
    private String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name, container, false);
        save = (Button)view.findViewById(R.id.name_save);
        save.setOnClickListener(this);
        input = (EditText)view.findViewById(R.id.name_input);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.id_toolbar);
        activity.setSupportActionBar(toolbar);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (input.length() == 0) {
            Toast.makeText(getActivity(), "请输入昵称", Toast.LENGTH_SHORT).show();
        }else {
            name = input.getText().toString().trim();
            HashMap<String, String> map = new HashMap<String, String>();

            map.put("name", name);
            map.put("user_id","1");          //用户ID
            map.put("flag","alter_name");

            new ConnectTask().execute(map);
        }
    }
}
