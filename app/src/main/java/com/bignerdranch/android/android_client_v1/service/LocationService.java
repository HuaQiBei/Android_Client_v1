package com.bignerdranch.android.android_client_v1.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bignerdranch.android.android_client_v1.MainActivity;
import com.bignerdranch.android.android_client_v1.R;

public class LocationService extends Service {
    LocationClient mLocationClient;
    BDLocationListener myListener;
    BDNotifyListener mAirportNotifyer;
    BDNotifyListener mHuaShanNotifyer;
    NotificationManager manager;
    int address;     //0:没位置 1:在机场 2:在华山
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        address = 0;
        mLocationClient = new LocationClient(getBaseContext());     //声明LocationClient类
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); //通知管理器

        //壶口瀑布提醒相关代码
        mAirportNotifyer = new AirportLister();
        mAirportNotifyer.SetNotifyLocation(36.120094,110.461848,3000,"gps");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
        mLocationClient.registerNotify(mAirportNotifyer);

        //华山位置提醒相关代码
        mHuaShanNotifyer = new HuaShanLister();
        mHuaShanNotifyer.SetNotifyLocation(34.532199,110.088721,5000,"gps");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
        mLocationClient.registerNotify(mHuaShanNotifyer);

        new Thread(new Runnable() {
            @Override
            public void run() {
                myListener = new MyLocationListener();
                mLocationClient.registerLocationListener(myListener);    //注册监听函数
                initLocation();//设定定位参数
                mLocationClient.start();//开始定位
                Log.d("test", "开始定位");
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(true);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    //机场位置提醒实现
    class AirportLister extends BDNotifyListener {
        int i = 0;
        public void onNotify(BDLocation mlocation, float distance){
            i++;
            Log.d("test", "到达壶口瀑布");
            if (address != 1) {
                address = 1; //设置位置标识在机场
                //通知
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("who",1);
                intent.putExtra("city","延安市");
                intent.putExtra("name","壶口瀑布景区");
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                Notification notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle("智随保")
                        .setContentText("检测到您当前位于壶口瀑布景区，是否购买意外险？")
                        .setTicker("智随保通知")
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setSmallIcon(R.drawable.notify)
                        //.setContentIntent(pi)
                        .build();
                manager.notify(0, notification);
                //取消位置提醒
                //mLocationClient.removeNotifyEvent(mAirportNotifyer);
            }
            if (i == 3) {
                i = 0;
                mAirportNotifyer.SetNotifyLocation(36.120094,110.461848,3000,"gps");
            }
        }
    }

    //华山位置提醒实现
    class HuaShanLister extends BDNotifyListener {
        int i = 0;
        public void onNotify(BDLocation mlocation, float distance){
            i++;
            Log.d("test","到达华山");
            if (address != 2) {
                address = 2; //设置位置标识在华山
                //通知
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("who",1);
                intent.putExtra("city","华阴市");
                intent.putExtra("name","华山景区");
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                Notification notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle("智随保")
                        .setContentText("检测到您当前位于华山景区，是否购买登山意外险？")
                        .setTicker("智随保通知")
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setSmallIcon(R.drawable.notify)
                        .setContentIntent(pi)
                        .build();
                manager.notify(1, notification);
                //取消位置提醒
                //mLocationClient.removeNotifyEvent(mHuaShanNotifyer);
            }
            //Log.d("testi","i= "+i);
            if (i == 3) {
                i = 0;
                mHuaShanNotifyer.SetNotifyLocation(34.532199,110.088721,5000,"gps");
            }
        }
    }
}
