package com.bignerdranch.android.android_client_v1.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.android_client_v1.R;
import com.bignerdranch.android.android_client_v1.model.City;
import com.bignerdranch.android.android_client_v1.model.County;
import com.bignerdranch.android.android_client_v1.model.Province;
import com.bignerdranch.android.android_client_v1.model.WeatherDB;
import com.bignerdranch.android.android_client_v1.util.HttpCallbackListner;
import com.bignerdranch.android.android_client_v1.util.HttpUtil;
import com.bignerdranch.android.android_client_v1.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/8/18.
 */
public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView mTitleText;
    private RecyclerView mAreaRecyclerView;
    // private ArrayAdapter<String> mAdapter;
    private WeatherDB mWeatherDB;
    private List<String> dataList = new ArrayList<>();
    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;
    private Province mSelectedProvince;
    private City mSelectedCity;
    private int mCurrentLevel;

    private AreaAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_area, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAreaRecyclerView = (RecyclerView) view.findViewById(R.id.area_recycler_view);
        mAreaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTitleText = (TextView) view.findViewById(R.id.area_title_text);
        mWeatherDB = WeatherDB.getInstance(getActivity());
        mAdapter = new AreaAdapter();
        mAreaRecyclerView.setAdapter(mAdapter);
        queryProvinces();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.KEYCODE_BACK){
//                    Log.d("life","返回键");
//                    if (mCurrentLevel == LEVEL_CITY){
//
//                        queryProvinces();
//                    } else if (mCurrentLevel == LEVEL_COUNTY) {
//                        queryCities();
//                    }else
//                        getActivity().finish();
//                    return true;
//                }
//                return false;
//            }
//
//        });
//    }

    private class AreaHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView mItemText;

        public AreaHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mItemText = (TextView) itemView.findViewById(android.R.id.text1);
        }

        @Override
        public void onClick(View v) {
            Log.d("life", "" + getAdapterPosition());
            if (mCurrentLevel == LEVEL_PROVINCE) {
                mSelectedProvince = mProvinceList.get(getAdapterPosition());
                queryCities();
            } else if (mCurrentLevel == LEVEL_CITY) {
                mSelectedCity = mCityList.get(getAdapterPosition());
                queryCounties();
            }
        }
    }

    private class AreaAdapter extends RecyclerView.Adapter<AreaHolder> {
        @Override
        public AreaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new AreaHolder(view);
        }

        @Override
        public void onBindViewHolder(AreaHolder holder, int position) {
            String data = dataList.get(position);
            holder.mItemText.setText(data);
        }


        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有再去服务器查询
     */
    private void queryProvinces() {
        mProvinceList = mWeatherDB.loadProvinces();
        if (mProvinceList.size() > 0) {
            dataList.clear();
            for (Province province :
                    mProvinceList) {
                dataList.add(province.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            mTitleText.setText("中国");
            mCurrentLevel = LEVEL_PROVINCE;
        } else {
            queryFromServer(null, "province");
        }
    }

    /**
     * 查询省内所有的市，优先从数据库查询，如果没有再去服务器查询
     */
    private void queryCities() {
        mCityList = mWeatherDB.loadCities(mSelectedProvince.getId());
        if (mCityList.size() > 0) {
            dataList.clear();
            for (City city :
                    mCityList) {
                dataList.add(city.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mTitleText.setText(mSelectedProvince.getProvinceName());
            mCurrentLevel = LEVEL_CITY;
        } else {
            queryFromServer(mSelectedProvince.getProvinceCode(), "city");

        }
    }

    /**
     * 查询市内的县，优先从数据库查询，如果没有再去服务器查询
     */
    private void queryCounties() {
        mCountyList = mWeatherDB.loadCounties(mSelectedCity.getId());
        Log.d("life", mSelectedCity.getCityName() + " " + mCountyList.size());
        if (mCountyList.size() > 0) {
            dataList.clear();
            for (County county :
                    mCountyList) {
                dataList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mTitleText.setText(mSelectedCity.getCityName());
            mCurrentLevel = LEVEL_COUNTY;
        } else {
            queryFromServer(mSelectedCity.getCityCode(), "county");
        }
    }

    private void queryFromServer(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            Log.d("life", "code");
            address = "http://www.weather.com.cn/data/list3/city" + code +
                    ".xml";
        } else {
            Log.d("life", "! code");
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        //MyProgressDialog.showProgressDialog(getActivity());
        HttpUtil.sendHttpRequest(address, new HttpCallbackListner() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                Log.d("life", type);
                if ("province".equals(type)) {
                    Log.d("life", "province");
                    result = Utility.handleProvincesResponse(mWeatherDB, response);
                } else if ("city".equals(type)) {
                    result = Utility.handleCitiesResponse(mWeatherDB, response, mSelectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountysResponse(mWeatherDB, response, mSelectedCity.getId());
                }
                if (result) {
                    //runOnUiThread回到主线程处理逻辑
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //MyProgressDialog.closeProgressDialog();
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }


            @Override
            public void onError(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT);
                    }
                });

            }
        });
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        Log.d("life", "showProgressDialog");
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载中····");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
}
