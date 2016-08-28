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
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.android_client_v1.model.PolicyLab;
import com.bignerdranch.android.android_client_v1.service.AutoUpdateService;
import com.bignerdranch.android.android_client_v1.view.ShowScenicPolicyActivity;
import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

import org.json.JSONException;

/**
 *
 *
 * @功能说明 自定义TabHost
 *
 */
public class MainActivity extends AppCompatActivity{

    private FindAllPolicyTask mAuthTask=null;
    // public static String resultString;
    public Connect2Server c2s=new Conn2ServerImp();



    private int mPolicyTitle;
    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    // 定义一个布局
    private LayoutInflater layoutInflater;
    // 定义数组来存放Fragment界面
    private Class fragmentArray[] = { HomeFragment.class,
            LifeFragment.class, PolicyFragment.class,
            MineFragment.class };

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
        Log.d("test", "MainActivity onCreate");
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        if (!preferences.getBoolean("isLogin", false)) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }

        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userID=preferences.getInt("id",0);
        if(userID==0)
            Toast.makeText(this, "没取到用户ID", Toast.LENGTH_SHORT).show();
        if (mAuthTask != null) {
            return;
        }
        Log.d("test","click the commit button!");
        mAuthTask = new FindAllPolicyTask(userID);//为后台传递参数
        mAuthTask.execute((Void) null);

        initView();
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
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

    public void setCurrentTabByTag(String tag){
        mTabHost.setCurrentTabByTag(tag);
    }



//-----------------------------------


    public class FindAllPolicyTask extends AsyncTask<Void, Void, String> {

        int mUserID;

        FindAllPolicyTask(int userID) {
            this.mUserID=userID;

        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("test","in show policy in background!");
            try {
                // Simulate network access.
                String resultString = c2s.findAllPolicy(mUserID);

                Log.d("test","find all policy String="+resultString);

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
            if(result!=null){
                Log.d("test","in to on PostExecute!");
                try {
                    PolicyLab policyList = PolicyLab.get(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("test","save all policy sucessful!");
            } else {
                Log.d("test","return nothing!");
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





}

/*生成保单号
    int r1=(int)(Math.random()*(10));//产生2个0-9的随机数
    int r2=(int)(Math.random()*(10));
    long now = System.currentTimeMillis();//一个13位的时间戳
    String paymentID =String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);// 订单ID
*/