package com.bignerdranch.android.android_client_v1.model;

import android.util.Log;

/**
 * Created by Elvira on 2016/8/26.
 */
public class FlightPolicy {

    private int policyID;
    private int policyHolder;
    private String mFlightDate;
    private String mFlightId;
    private String mFlightRoute;
    private String mFlightWeather;
    private int mFlightCheckBox;
    private int mFlightCoverage;
    private double mFlightFee;
    private String mState;
    private String mInsuredMan;
    private String mIDCard;

    public String getmInsuredMan() {
        return mInsuredMan;
    }

    public void setmInsuredMan(String mInsuredMan) {
        this.mInsuredMan = mInsuredMan;
    }

    public String getmIDCard() {
        return mIDCard;
    }

    public void setmIDCard(String mIDCard) {
        this.mIDCard = mIDCard;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public FlightPolicy(int policyID, int policyHolder, String flightDate, String flightId, String flightRoute, String flightWeather, int flightCheckBox, int flightCoverage, double flightFee, String state,String insuredMan,String insuredIDCard) {
        this.policyID = policyID;
        this.policyHolder = policyHolder;
        this.mFlightDate = flightDate;
        this.mFlightId = flightId;
        this.mFlightRoute = flightRoute;
        this.mFlightWeather = flightWeather;
        this.mFlightCheckBox = flightCheckBox;
        this.mFlightCoverage = flightCoverage;
        this.mFlightFee = flightFee;
        this.mState = state;
        this.mInsuredMan=insuredMan;
        this.mIDCard=insuredIDCard;
    }

    public int getPolicyID() {
        return policyID;
    }

    public void setPolicyID(int policyID) {
        this.policyID = policyID;
    }

    public int getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(int policyHolder) {
        this.policyHolder = policyHolder;
    }

    public String getFlightDate() {
        return mFlightDate;
    }

    public void setFlightDate(String flightDate) {
        mFlightDate = flightDate;
    }

    public String getFlightId() {
        return mFlightId;
    }

    public void setFlightId(String flightId) {
        mFlightId = flightId;
    }

    public String getFlightRoute() {
        return mFlightRoute;
    }

    public void setFlightRoute(String flightRoute) {
        mFlightRoute = flightRoute;
    }


    public String getFlightWeather() {
        return mFlightWeather;
    }

    public void setFlightWeather(String flightWeather) {
        mFlightWeather = flightWeather;
    }

    public int getFlightCheckBox() {
        return mFlightCheckBox;
    }

    public void setFlightCheckBox(int flightCheckBox) {
        mFlightCheckBox = flightCheckBox;
    }

    public int getFlightCoverage() {
        return mFlightCoverage;
    }

    public void setFlightCoverage(int flightCoverage) {
        mFlightCoverage = flightCoverage;
    }

    public double getFlightFee() {
        return mFlightFee;
    }

    public void setFlightFee(double flightFee) {
        mFlightFee = flightFee;
    }
}
