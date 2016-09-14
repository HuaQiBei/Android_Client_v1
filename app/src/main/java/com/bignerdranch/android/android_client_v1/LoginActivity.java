package com.bignerdranch.android.android_client_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView login;
    private ImageView register;
    private EditText name;
    private EditText passw;
    String sname;
    private static ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (ImageView)findViewById(R.id.login);
        register = (ImageView)findViewById(R.id.register);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        name = (EditText)findViewById(R.id.login_username);
        name.setText(preferences.getString("name", "请输入账号"));
        passw = (EditText)findViewById(R.id.login_password);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (name.getText().length() == 0) {
                    Toast.makeText(this,"请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (passw.getText().length() == 0) {
                    Toast.makeText(this,"请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    sname = name.getText().toString();
                    HashMap<String, String> map = new HashMap<String, String>();
                    //提交的参数
                    map.put("name", sname);
                    map.put("password", passw.getText().toString());
                    map.put("flag", "login");

                    new LoginTask().execute(map);
                    if (dialog == null) {
                        dialog = new ProgressDialog(this);
                        // dialog=new ProgressDialog(SearchActivity.this);
                    }
                    dialog.setTitle("请耐心等待");
                    dialog.setMessage("查询中...");
                    dialog.setCancelable(false);
                    dialog.show();
                }
                break;
            case R.id.register:
                //跳转
                Intent intent = new Intent(this,
                        RegisterActivity.class);
                startActivity(intent);
                break;
        }



    }

    class LoginTask extends ConnectTask {
        //执行完后更新UI
        @Override
        protected void onPostExecute(Boolean result) {
            Log.d("test","on post Execute!");
            dialog.dismiss();
            if (result) {
                if (response.equals("null")) {
                    Toast.makeText(LoginActivity.this,"该用户不存在或输入密码错误", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"登陆成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = PreferenceManager
                            .getDefaultSharedPreferences(LoginActivity.this).edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("name", sname);
                    editor.putInt("id", Integer.valueOf(response));
                    Log.d("test",Integer.valueOf(response)+"");
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}


