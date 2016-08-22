package com.bignerdranch.android.android_client_v1;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LocationApplication extends Application {
    public LocationClient mLocationClient;//定位SDK的核心类
    public MyLocationListener mMyLocationListener;//定义监听类

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new LocationClient(this.getApplicationContext());

        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
    }

    /**
     * 实现实位回调监听
     */
    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        option.setCoorType("bd09ll");//设置百度经纬度坐标系格式
        option.setScanSpan(100);//设置发起定位请求的间隔时间为1000ms
        option.setProdName("LocationDemo");
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
//			sb.append("time : ");
//			sb.append(location.getTime());//获得当前时间
//			sb.append("\nerror code : ");
//			sb.append(location.getLocType());//获得erro code得知定位现状
            sb.append("纬度 : ");
            sb.append(location.getLatitude());//获得纬度
            sb.append("，经度 : ");
            sb.append(location.getLongitude());//获得经度
//
            if (location.getLocType() == BDLocation.TypeGpsLocation) {//通过GPS定位
                sb.append("，速度 : ");
                sb.append(location.getSpeed());//获得速度
                sb.append("，卫星 : ");
                sb.append(location.getSatelliteNumber());

                sb.append("，位置： : ");
                sb.append(location.getAddrStr());//获得当前地址
                sb.append("，方位 : ");
                sb.append(location.getDirection());//获得方位
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {//通过网络连接定位
                sb.append("，位置 : ");
                sb.append(location.getAddrStr());//获得当前地址
            }

            Log.i("BaiduLocationApiDem", sb.toString());


        }
    }
}