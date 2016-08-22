package com.bignerdranch.android.android_client_v1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class ResetFragment extends Fragment implements View.OnClickListener{

    private Button save;
    private EditText old;
    private EditText now;
    private EditText again;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset, container, false);
        save = (Button)view.findViewById(R.id.password_save);
        save.setOnClickListener(this);
        old = (EditText)view.findViewById(R.id.password_old);
        now = (EditText)view.findViewById(R.id.password_new);
        again = (EditText)view.findViewById(R.id.password_again);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        String str_now = now.getText().toString();
        String str_again = again.getText().toString();

        Log.d("test",str_again);
        Log.d("test",str_now);
        if (old.length() == 0 || now.length() == 0 || again.length() == 0) {
            Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_SHORT).show();
        } else if (!str_again.equals(str_now)){
            Toast.makeText(getActivity(), "确认密码与新密码不一致！", Toast.LENGTH_SHORT).show();
        } else {
            password = again.getText().toString().trim();
            HashMap<String, String> map = new HashMap<String, String>();

            map.put("password", password);
            map.put("user_id","1");          //用户ID
            map.put("flag","alter_password");

            new ConnectTask().execute(map);
        }
    }
}
