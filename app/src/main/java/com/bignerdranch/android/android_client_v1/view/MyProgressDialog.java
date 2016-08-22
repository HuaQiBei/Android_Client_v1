package com.bignerdranch.android.android_client_v1.view;

import android.content.Context;
import android.util.Log;
import android.app.ProgressDialog;

/**
 * Created by DELL on 2016/8/19.
 */
public class MyProgressDialog {
    private static android.app.ProgressDialog progressDialog;

    public static void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public static void showProgressDialog(Context context) {
        Log.d("life", "showProgressDialog");
        if (progressDialog == null) {
            progressDialog = new android.app.ProgressDialog(context);
            progressDialog.setMessage("正在加载中····");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
}
