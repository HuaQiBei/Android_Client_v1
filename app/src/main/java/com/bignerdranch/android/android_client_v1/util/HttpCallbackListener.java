package com.bignerdranch.android.android_client_v1.util;

/**
 * Created by DELL on 2016/8/18.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
