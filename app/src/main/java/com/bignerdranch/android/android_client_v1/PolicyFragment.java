package com.bignerdranch.android.android_client_v1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolicyFragment extends Fragment {

    private View view = null;

    private ViewPagerIndicator mIndicator;
    private ViewPager mViewPager;

    private List<String> mTitles = Arrays.asList("全部", "待支付", "生效中", "理赔中", "已失效");
    private List<ViewPagerSimpleFragment> mContents = new ArrayList<>();

    private FragmentPagerAdapter mAdapter;

    private static final String BUNDLE_TITLE = "title";//设置bundle的key

    private String mTitle;//接收用户传过来的title

    public static PolicyFragment newInstance(String title) {

        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);


        PolicyFragment fragment = new PolicyFragment();
        fragment.setArguments(bundle);
        Log.d("life","newInstance+"+bundle.getString(BUNDLE_TITLE));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_policy, container, false);
        Log.d("life", "PolicyFragment onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            Log.d("life","getArguments");
            mTitle = getArguments().getString(BUNDLE_TITLE);
        }
        Log.d("life","全部"+mTitle);
        super.onViewCreated(view, savedInstanceState);
        initViews();
        mIndicator.setVisibleTabCount(5);
        mIndicator.setTabItemTitles(mTitles);
        mIndicator.setViewPager(mViewPager, 0);
    }

    @Override
    public void onResume() {
        Log.d("life", "PolicyFragment onResume");
        super.onResume();
        //find
        updateUI();
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d("life", "PolicyFragment onStop");
    }

    private void updateUI() {

        if (mAdapter == null) {
            Log.d("life", "mAdapter == null");
            initData();
        } else {
            Log.d("life", "mAdapter != null");
        }
        mViewPager.setAdapter(mAdapter);
    }

    private void initViews() {
        mViewPager = (ViewPager) view.findViewById(R.id.id_viewpager);
        mIndicator = (ViewPagerIndicator) view.findViewById(R.id.id_indicator);
    }


    private void initData() {//getActivity().getSupportFragmentManager()
        for (String title : mTitles) {
            Log.d("life", "来一个" + title);
            ViewPagerSimpleFragment fragment = ViewPagerSimpleFragment.newInstance(title);
            mContents.add(fragment);
        }
        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
    }
}
