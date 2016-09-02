package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //private static final String APPKEY = "164fb160b6716";
    //private static final String APPSECRET="028b98c2a52546db6caa7be8de6c3b6d";

    private static final String APPKEY = "165330232451a";
    private static final String APPSECRET="478eaddde32096a52c888c7cbecd669f";

    private Button login;
    private Button get_code;
    private EditText input_name;
    private EditText input_passw;
    private EditText input_again;
    private EditText input_phone;
    private EditText input_code;
    private EditText label;
    private String phone = "";
    int i = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_name = (EditText) findViewById(R.id.name);
        input_passw = (EditText) findViewById(R.id.password);
        input_again = (EditText) findViewById(R.id.again);
        input_phone = (EditText) findViewById(R.id.phone);
        input_code = (EditText) findViewById(R.id.code);
        label = (EditText) findViewById(R.id.label);

        get_code = (Button) findViewById(R.id.get_code);
        get_code.setOnClickListener(this);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        input_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String sname = input_name.getText().toString();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", sname);
                    map.put("flag", "checkname");
                    new NameCheckTask().execute(map);
                }

            }
        });


        // 启动短信验证sdk
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        EventHandler eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                get_code.setText("重新发送(" + i + ")");
                get_code.setClickable(false);
            } else if (msg.what == -8) {
                get_code.setText("获取验证码");
                get_code.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    Log.d("event","succ");
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(RegisterActivity.this, "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                        //向服务器传送数据
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("name", input_name.getText().toString());
                        map.put("password", input_passw.getText().toString());
                        map.put("phone", phone);
                        map.put("flag", "register");
                        new ConnectTask().execute(map);
                        //跳转
                        Intent intent = new Intent(RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(RegisterActivity.this, "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_code:
                if (input_phone.getText().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "请输手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    phone = input_phone.getText().toString().trim();
                }
                // 1. 通过规则判断手机号
                if (!judgePhoneNums(phone)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phone);

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                get_code.setClickable(false);
                get_code.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;
            case R.id.login:
                //检测输入是否为空
                if (input_name.getText().length() == 0) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (input_passw.getText().length() == 0) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (input_again.getText().length() == 0) {
                    Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
                } else if (!input_again.getText().toString().equals(input_passw.getText().toString())) {
                    Toast.makeText(this, "确认密码与密码不一致", Toast.LENGTH_SHORT).show();
                } else if (input_phone.getText().length() == 0) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (input_code.getText().length() == 0) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else {
                    SMSSDK.submitVerificationCode("86", phone, input_code
                            .getText().toString());
                }
                break;
        }
    }
        /**
         * 判断手机号码是否合理
         *
         * @param phoneNums
         */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(RegisterActivity.this, "手机号码输入有误！",Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断一个字符串的位数
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
    @Override
    public void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    class NameCheckTask extends ConnectTask {
        //检测用户名是否已经存在
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Log.d("test",response);
                if (response.equals("already")) {
                    label.setText("（用户名已存在）");
                    input_name.setText("");
                }else if (response.equals("ok")){
                    label.setText("（该用户名可用）");
                }
            }
        }
    }

}


