package com.bignerdranch.android.android_client_v1;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 *
 *
 * @功能说明 自定义TabHost
 *
 */
public class MainActivity extends AppCompatActivity{


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
        Log.d("test","MainActivity onCreate");
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        initView();
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
}
