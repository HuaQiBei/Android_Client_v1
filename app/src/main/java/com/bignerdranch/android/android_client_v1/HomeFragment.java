package com.bignerdranch.android.android_client_v1;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;


import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

	@ViewInject(R.id.index_home_viewpager)
	private WrapContentHeightViewPager viewPager;

	@ViewInject(R.id.index_home_rb1)//radiogroup 1组以及3个radiobutton
	private RadioButton rb1;
	@ViewInject(R.id.index_home_rb2)
	private RadioButton rb2;
	@ViewInject(R.id.index_home_rb3)
	private RadioButton rb3;

	private GridView gridView1;
	private GridView gridView2;
	private GridView gridView3;

	//接受处理消息
	private Handler handler=new Handler(new Handler.Callback() {//暂时先让秒数动起来

		@Override
		public boolean handleMessage(Message arg0) {
			 if(arg0.what==1){
				initViewPager();//初始化 viewpager 解决切换不显示的问题
			}
			return false;
		}
	});
	@Override
	public void onStart() {
		super.onStart();
		//initViewPager();
		Log.e("jhd","onStart");

		// TODO Auto-generated method stub  暂时不能用
		//checkGPSIsOpen();
	}




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view=inflater.inflate(R.layout.fragment_home, null);
		ViewUtils.inject(this, view);   //注入控件
		//获取数据并显示
		//topCity.setText(SharedUtils.getCityName(getActivity()));


		initGridView();

		Log.e("jhd", "onCreateView");

		handler.sendEmptyMessage(1);//发线程 初始化viewpager 解决切换页面导致viewpager中的内容为空

		return view;


		//return inflater.inflate(R.layout.fragment_home, container, false);
	}

	//gridview 的适配器
	public class GridViewAdapter extends BaseAdapter {

		//我的数据在utils包下的MyConstant中定义好了
		private LayoutInflater inflater;
		private int page;

		public GridViewAdapter(Context context, int page) {
			super();
			this.inflater = LayoutInflater.from(context);
			this.page=page;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(page!=-1){
				return 8;
			}else{
				return 0;//MyConstant.navSort.length;
			}
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {

			ViewHolder vh=null;
			if(convertView==null){
				vh=new ViewHolder();
				convertView=inflater.inflate(R.layout.index_home_grid_item, null);
				ViewUtils.inject(vh, convertView);
				convertView.setTag(vh);
			}
			else{
				vh=(ViewHolder) convertView.getTag();
			}

			//vh.iv_navsort.setImageResource(MyConstant.navSortImages[position+8*page]);
//			vh.tv_navsort.setText(MyConstant.navSort[position+8*page]);
//			if(position==8-1 && page==2){
//				vh.iv_navsort.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//						startActivity(new Intent(getActivity(), AllCategoryActivity.class));
//					}
//				});
//			}else{
//				vh.iv_navsort.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//						startActivity(new Intent(getActivity(), ActivityHomeList1.class));
//					}
//				});
//			}


			return convertView;
		}
	}

	//gridview 适配器的holder类
	private class ViewHolder{
		@ViewInject(R.id.index_home_iv_navsort)
		ImageView iv_navsort;
	}

	private void initGridView(){
		gridView1=(GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
		gridView1.setAdapter(new GridViewAdapter(getActivity(), 0));
		gridView2=(GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
		gridView2.setAdapter(new GridViewAdapter(getActivity(), 1));
		gridView3=(GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
		gridView3.setAdapter(new GridViewAdapter(getActivity(), 2));
	}

	private void initViewPager(){   //初始化viewpager
		List<View> list=new ArrayList<View>();  //以下实现动态添加三组gridview
		//		GridView gridView1=(GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
		//		gridView1.setAdapter(new GridViewAdapter(getActivity(), 0));
		//		GridView gridView2=(GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
		//		gridView2.setAdapter(new GridViewAdapter(getActivity(), 1));
		//		GridView gridView3=(GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
		//		gridView3.setAdapter(new GridViewAdapter(getActivity(), 2));
		list.add(gridView1);
		list.add(gridView2);
		list.add(gridView3);
		viewPager.setAdapter(new MyViewPagerAdapter(list));
		//viewPager .setOffscreenPageLimit(2);   //meiyong
		rb1.setChecked(true);//设置默认  下面的点选中的是第一个
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {  //实现划到那个页面，那个页面下面的点会被选中
				// TODO Auto-generated method stub
				if(position==0){
					rb1.setChecked(true);
				}else if(position==1){
					rb2.setChecked(true);
				}else if(position==2){
					rb3.setChecked(true);
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	//自定义viewpager的适配器
	private class MyViewPagerAdapter extends PagerAdapter{

		List<View> list;
		//List<String> titles;
		public MyViewPagerAdapter(List<View> list) {
			// TODO Auto-generated constructor stub

			this.list=list;
			//this.titles=titles;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		//  判断  当前的view 是否是  Object 对象
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(list.get(position));
			Log.e("jhd", "添加--"+position);

			return list.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub

			container.removeView(list.get(position));
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			//return titles.get(position);
			return "1";  //暂时没用的
		}
	}
	//	@Override
	//	public void onPause() {
	//		// TODO Auto-generated method stub
	//		super.onPause();
	//		Log.e("jhd", "onPause");
	//	}
	//	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e("jhd", "onStop");
	}





}
