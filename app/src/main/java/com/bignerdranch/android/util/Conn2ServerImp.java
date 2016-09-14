package com.bignerdranch.android.util;

import android.util.Log;

import com.bignerdranch.android.android_client_v1.model.FlightPolicy;
import com.bignerdranch.android.android_client_v1.model.ScenicPolicy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;


import com.bignerdranch.android.android_client_v1.model.ScenicPolicy;

/**
 * 对Conn2ServerImp类代码进行整理
 * 以addScenicPolicy方法中的格式为模板编写
 */
public class Conn2ServerImp implements Connect2Server {
    private static final String TAG = "Conn2ServerImp";
    private static final String urlSpec = "http://172.27.211.7:8080/Server4android/Search.do";


    @Override
    public String login(String username, String password) throws Exception {
        String str = null;
        Log.d("test", "in login!");

        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());

        SetPostParams.setPostParam("username", username);
        SetPostParams.setPostParam("password", password);
        SetPostParams.setPostParam("flag", "login");
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        str = new String(getPostReturn(conn));
        return str;
    }

    @Override
    public String addAddress(String receivename, String telephone, String mailcode, String district, String detaildis) throws IOException {
        String str = null;
        Log.d("test", "in add address!");

        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());

        SetPostParams.setPostParam("userID", "12");

        SetPostParams.setPostParam("receivename", receivename);
        SetPostParams.setPostParam("telephone", telephone);
        SetPostParams.setPostParam("mailcode", mailcode);
        SetPostParams.setPostParam("district", district);
        SetPostParams.setPostParam("detaildis", detaildis);
        SetPostParams.setPostParam("flag", "addaddress");
        Log.d("test", "address required:" + SetPostParams.getResult().toString());
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        str = new String(getPostReturn(conn));
        URLDecoder.decode(str, "utf-8");

        return str;

    }

    @Override
    public String addContacts(String contactname, String telephone, String IDcard) throws IOException {
        String str = null;
        Log.d("test", "in add contacts!");

        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());

        SetPostParams.setPostParam("userID", "12");

        SetPostParams.setPostParam("contactname", contactname);
        SetPostParams.setPostParam("telephone", telephone);
        SetPostParams.setPostParam("IDcard", IDcard);
        SetPostParams.setPostParam("flag", "addcontact");
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        str = new String(getPostReturn(conn));
        return str;

    }

    @Override
    public String modifyEmail(String newemail) throws IOException {
        String str = null;
        Log.d("test", "in modify email!");

        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());
        SetPostParams.setPostParam("userID", "12");
        SetPostParams.setPostParam("newemail", newemail);
        SetPostParams.setPostParam("flag", "modifyemail");
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        str = new String(getPostReturn(conn));
        return str;

    }

    @Override
    public String addScenicPolicy(ScenicPolicy policy,String insuredname, String insuredIDcard) throws IOException, JSONException {

        String str = null;
        Log.d("test", "in add Scenic policy!");

        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());
        JSONArray reqValue = new JSONArray().put(
                new JSONObject()
                        .put("scenicname", policy.getScenicname())
                        .put("startdate", policy.getStartdate())
                        .put("scenicweather", policy.getScenicweather())
                        .put("enddate", policy.getEnddate())
                        .put("policyID", policy.getPolicyID())
                        .put("insureduty", policy.getInsureduty())
                        .put("fee", policy.getFee())
                        .put("userID", policy.getPolicyholder())
                        .put("state", policy.getState())
                        .put("insuredname", insuredname)
                        .put("insuredIDcard", insuredIDcard)
                        .put("suminsured",policy.getSuminsured()));
        Log.d("test", reqValue.toString());
        SetPostParams.setPostParam("data", reqValue.toString());
        SetPostParams.setPostParam("flag", "addscenicpolicy");
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        str = new String(getPostReturn(conn));
        return str;

    }

    @Override
    public String showScenicPolicy(int policyID, String policyName) throws IOException, JSONException {
        String str = null;
        Log.d("test", "in show Policy!");

        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());
        SetPostParams.setPostParam("policyID", Integer.toString(policyID));
        SetPostParams.setPostParam("policyName", policyName);
        SetPostParams.setPostParam("flag", "showpolicy");
        Log.d("test", "policy required:" + SetPostParams.getResult().toString());
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        //str=new String(getPostReturn(conn));
        str = new String(getPostReturn(conn));
        Log.d("test", "response:" + str);
        return str;


    }

    @Override
    public String addInsuredman(String insuredname, String insuredIDcard) throws IOException {

        String str = null;
        Log.d("test", "in add Insured man!");

        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());
        SetPostParams.setPostParam("insuredname", insuredname);
        SetPostParams.setPostParam("insuredIDcard", insuredIDcard);
        SetPostParams.setPostParam("flag", "addinsuredman");
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        str = new String(getPostReturn(conn));
        return str;

    }

    @Override
    public String addFlightPolicy(FlightPolicy policy) throws IOException, JSONException {

        String str = null;
        Log.d("test", "in add Flight policy!");

        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());
        JSONArray reqValue = new JSONArray().put(
                new JSONObject()
                        .put("policyID", policy.getPolicyID())
                        .put("mFlightDate", policy.getFlightDate())
                        .put("mFlightId", policy.getFlightId())
                        .put("mFlightRoute", policy.getFlightRoute())
                        .put("mFlightWeather", policy.getFlightWeather())
                        .put("mFlightCheckBox", policy.getFlightCheckBox())
                        .put("mFlightCoverage", policy.getFlightCoverage())
                        .put("mFlightFee", policy.getFlightFee())
                        .put("userID", policy.getPolicyHolder())
                        .put("state", policy.getmState())
                        .put("insuredname",policy.getmInsuredMan())
                        .put("insuredIDcard",policy.getmIDCard()));
        Log.d("test", reqValue.toString());
        SetPostParams.setPostParam("data", reqValue.toString());
        SetPostParams.setPostParam("flag", "addflightpolicy");
        Log.d("test", "add flight policy required:" + SetPostParams.getResult().toString());
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        str = new String(getPostReturn(conn));
        return str;
    }

    @Override
    public String findAllPolicy(int userID) throws IOException, JSONException {
        String str = null;
        Log.d("test", "in find all Policy!");

        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());
        SetPostParams.setPostParam("userID", Integer.toString(userID));
        SetPostParams.setPostParam("flag", "findallpolicy");
        Log.d("test", "policy required:" + SetPostParams.getResult().toString());
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        str = new String(getPostReturn(conn));
        Log.d("test", "response:" + str);
        return str;

    }


    public HttpURLConnection initPost(String urlstr) throws IOException {

        URL url = new URL(urlstr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();
        return connection;

    }

    public byte[] getPostReturn(HttpURLConnection connection) throws IOException {

        try {
            ByteArrayOutputStream outter = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                outter.write(buffer, 0, bytesRead);
            }
            outter.close();
            return outter.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    @Override
    public String findDelayRate(String flightCode, String startCity, String endCity) throws IOException, JSONException {
        String str = null;
        Log.d("test", "in find delay rate!");

        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());
        SetPostParams.setPostParam("flightCode", flightCode);
        SetPostParams.setPostParam("startCity", startCity);
        SetPostParams.setPostParam("endCity", endCity);
        SetPostParams.setPostParam("flag", "finddelayrate");
        Log.d("test", "find delay rate required:" + SetPostParams.getResult().toString());
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        str = new String(getPostReturn(conn));
        Log.d("test", "response:" + str);
        return str;

    }

    @Override
    public String getFee(String scenicname, String weather) throws IOException, JSONException {
        String str = null;
        Log.d("test", "in find all Policy! INPUT" + scenicname + weather);
        HttpURLConnection conn = initPost(urlSpec);
        DataOutputStream out = new DataOutputStream(conn
                .getOutputStream());
        SetPostParams.setPostParam("scenicName", scenicname);
        SetPostParams.setPostParam("weather", weather);
        SetPostParams.setPostParam("flag", "findscenicfee");
        Log.d("test", "findscenicfee required:" + SetPostParams.getResult().toString());
        out.writeBytes(SetPostParams.getResult().toString());
        SetPostParams.dismiss();
        out.flush();
        out.close();
        str = new String(getPostReturn(conn));
        Log.d("test", "response:" + str);
        return str;
    }
}
