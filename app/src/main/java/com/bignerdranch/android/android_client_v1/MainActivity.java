package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.bignerdranch.android.android_client_v1.db.WeatherDB;
import com.bignerdranch.android.android_client_v1.model.PolicyLab;
import com.bignerdranch.android.android_client_v1.service.AutoUpdateService;
import com.bignerdranch.android.android_client_v1.service.LocationService;
import com.bignerdranch.android.android_client_v1.util.HttpCallbackListener;
import com.bignerdranch.android.android_client_v1.util.HttpUtil;
import com.bignerdranch.android.android_client_v1.util.Utility;
import com.bignerdranch.android.android_client_v1.view.ShowScenicPolicyActivity;
import com.bignerdranch.android.android_client_v1.view.WeatherActivity;
import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * @功能说明 自定义TabHost
 */
public class MainActivity extends AppCompatActivity {

    private WeatherDB mWeatherDB;//数据库操作对象

    private FindAllPolicyTask mAuthTask = null;
    // public static String resultString;
    public Connect2Server c2s = new Conn2ServerImp();


    private int mPolicyTitle;
    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    // 定义一个布局
    private LayoutInflater layoutInflater;
    // 定义数组来存放Fragment界面
    private Class fragmentArray[] = {HomeFragment.class,
            LifeFragment.class, PolicyFragment.class,
            MineFragment.class};

    // 定义数组来存放按钮图片
    private int mImageViewArray[] = {
            R.drawable.main_tab_item_home,
            R.drawable.main_tab_item_life,
            R.drawable.main_tab_item_policy,
            R.drawable.main_tab_item_mine};

    // Tab选项卡的文字
    private String mTextViewArray[] = {
            "主页",
            "生活",
            "保单",
            "我的"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        SDKInitializer.initialize(getApplicationContext());
        Log.d("test", "MainActivity onCreate");
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.getBoolean("isLogin", false)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //启动定位服务
        Intent startIntent = new Intent(this, LocationService.class);
        startService(startIntent); // 启动服务
        Log.d("test", "service started");
        setContentView(R.layout.activity_main);

        int userID = preferences.getInt("id", 0);
        if (userID == 0)
            Toast.makeText(this, "没取到用户ID", Toast.LENGTH_SHORT).show();
        if (mAuthTask != null) {
            return;
        }
        Log.d("test", "Main auto exe!");
        mAuthTask = new FindAllPolicyTask(userID);//为后台传递参数
        mAuthTask.execute((Void) null);

        mWeatherDB = WeatherDB.getInstance(this);//获取数据库处理对象
        //先检查本地是否已同步过城市数据，如果没有，则从服务器同步
        if (mWeatherDB.checkDataState() == 0) {
            Log.d("policy", "没有All城市列表");
            queryCitiesFromServer();
        }

        initView();
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
        ArrayList<String> par = getIntent().getStringArrayListExtra("flight_policy");
        if (par != null) {
            setCurrentTabByTag("生活");
            preferences.edit()
                    .putBoolean("flightDelayView", true)   //航空延误险//TODO 记得把这些清空
                    .putString("flightNo", par.get(0))
                    .putString("flightStartCity", par.get(1))
                    .putString("flightEndCity", par.get(2))
                    .apply();
        }
        //获取调用该activity的activity类型
        if (getIntent().getIntExtra("who", 0) == 1) {   //who == 1 来自景区通知的调用
            String scenic_spot_city = getIntent().getStringExtra("city");
            String scenic_spot_name = getIntent().getStringExtra("name");

            setCurrentTabByTag("生活");
            preferences.edit()
                    .putBoolean("scenicSpotView", true) //景区意外险
                    .putString("scenic_spot_city", scenic_spot_city)
                    .putString("scenic_spot_name", scenic_spot_name)
                    .apply();
        }
        if (getIntent().getIntExtra("des", 0) == 3) {
            setCurrentTabByTag("保单");

        }
    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        // 实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //去分割线
        final TabWidget tabWidget = mTabHost.getTabWidget();
        //tabWidget.setStripEnabled(false);
        tabWidget.setDividerDrawable(null);


        // 得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabSpec tabSpec = mTabHost.newTabSpec(mTextViewArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
//            // 设置Tab按钮的背景
//            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(1);
//                    .setBackgroundResource(R.drawable.main_tab_item_bg);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextViewArray[index]);

        return view;
    }

    public int getPolicyTitle() {
        return mPolicyTitle;
    }

    public void setPolicyTitle(int policyTitle) {
        mPolicyTitle = policyTitle;
    }

    public void setCurrentTabByTag(String tag) {
        mTabHost.setCurrentTabByTag(tag);
    }


//-----------------------------------


    public class FindAllPolicyTask extends AsyncTask<Void, Void, String> {

        int mUserID;

        FindAllPolicyTask(int userID) {
            this.mUserID = userID;

        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("test", "in find all policy in background!");
            try {
                // Simulate network access.
                String resultString = c2s.findAllPolicy(mUserID);

                Log.d("test", "find all policy String=" + resultString);

                return resultString;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Noting";

        }

        @Override
        protected void onPostExecute(final String result) {
            mAuthTask = null;
            //showProgress(false);
            if (result != null) {
                Log.d("test", "in to on find all policy PostExecute!");
                try {
                    PolicyLab policyList = PolicyLab.get(result);
                    Log.d("test", "单例大小：" + policyList.getPolicys().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("test", "save all policy sucessful!");
            } else {
                Log.d("test", "return nothing!");
                //getActivity().finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            // showProgress(false);
        }
    }
// --------------------------------------


    //从服务器取出所有的城市信息
    private void queryCitiesFromServer() {
        String address = "https://api.heweather.com/x3/citylist?search=allchina&key=" + WeatherActivity.WEATHER_KEY;
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if (Utility.handleAllCityResponse(mWeatherDB, response)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWeatherDB.updateDataState();
                        }
                    });
                }
            }

            @Override
            public void onError(final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "加载城市失败", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }
}

/*生成保单号
    int r1=(int)(Math.random()*(10));//产生2个0-9的随机数
    int r2=(int)(Math.random()*(10));
    long now = System.currentTimeMillis();//一个13位的时间戳
    String paymentID =String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);// 订单ID
*/
