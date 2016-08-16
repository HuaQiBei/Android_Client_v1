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

	private View view=null;

	private ViewPagerIndicator mIndicator;
	private ViewPager mViewPager;

	private List<String> mTitles = Arrays.asList("全部", "待支付", "生效中", "理赔中", "已失效");
	private List<ViewPagerSimpleFragment> mContents = new ArrayList<>();

	private FragmentPagerAdapter mAdapter;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_policy, container, false);
		Log.d("test","PolicyFragment onCreateView");
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews();
		mIndicator.setVisibleTabCount(5);
		mIndicator.setTabItemTitles(mTitles);
		mIndicator.setViewPager(mViewPager, 0);
	}

	@Override
	public void onResume() {
		Log.d("test","PolicyFragment onResume");
		super.onResume();
		//find
		updateUI();
	}

	@Override
	public void onStop() {
		super.onStop();

		Log.d("test","PolicyFragment onStop");
	}

	private void updateUI(){

		if (mAdapter == null){
			initData();
			mViewPager.setAdapter(mAdapter);
		}else {
			mViewPager.setAdapter(mAdapter);
		}
	}

	private void initViews() {
		mViewPager = (ViewPager) view.findViewById(R.id.id_viewpager);
		mIndicator = (ViewPagerIndicator) view.findViewById(R.id.id_indicator);
		for (String title : mTitles) {
			Log.d("test","来一个"+title);
			ViewPagerSimpleFragment fragment = ViewPagerSimpleFragment.newInstance(title);
			mContents.add(fragment);
		}
	}


	private void initData() {//getActivity().getSupportFragmentManager()
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
