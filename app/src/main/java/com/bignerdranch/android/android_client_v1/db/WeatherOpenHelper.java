package com.bignerdranch.android.android_client_v1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DELL on 2016/8/18.
 */
public class WeatherOpenHelper extends SQLiteOpenHelper {

    /**
     * Province表建表语句
     */
    public static final String CREATE_PROVINCE = "create table Province ("
            + "id integer primary key autoincrement, "
            + "province_name text, "
            + "province_code text)";
    /**
     * City表建表语句
     */
    public static final String CREATE_CITY = "create table City ("
            + "id integer primary key autoincrement, "
            + "city_name text, "
            + "city_code text, "
            + "province_id integer)";
    /**
     * County表建表语句
     */
    public static final String CREATE_COUNTY = "create table County ("
            + "id integer primary key autoincrement, "
            + "county_name text, "
            + "county_code text, "
            + "city_id integer)";

    //创建城市表
    private static final String CREATE_ALL_CITY = "CREATE TABLE ALLCITY (ID INTEGER PRIMARY KEY,CITY_NAME_EN TEXT,CITY_NAME_CH TEXT,CITY_CODE TEXT)";
    //创建有无数据状态表
    private static final String DATA_STATE = "CREATE TABLE DATA_STATE (STATE INTEGER PRIMARY KEY)";
    //更新状态表数据为0表示暂无数据
    private static final String INSERT_DATA_STATE = "INSERT INTO DATA_STATE VALUES(0)";

    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("life", "建表");
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);

        db.execSQL(CREATE_ALL_CITY);
        db.execSQL(DATA_STATE);
        db.execSQL(INSERT_DATA_STATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
