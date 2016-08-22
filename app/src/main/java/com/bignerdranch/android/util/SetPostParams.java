package com.bignerdranch.android.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/8/12.
 */



public class SetPostParams {

    public static String result="";



    public static void setPostParam(String key,String value) throws UnsupportedEncodingException {
           String str="";
        str="&"+key+"="+ URLEncoder.encode(value, "UTF-8");
        result+=str;

    }

    public static String getResult() {
        return result.substring(1);
    }


}
