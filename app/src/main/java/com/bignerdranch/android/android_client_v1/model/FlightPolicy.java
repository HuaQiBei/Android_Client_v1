package com.bignerdranch.android.android_client_v1.model;

/**
 * Created by Elvira on 2016/8/26.
 */
public class FlightPolicy {

    private int policyID;
    private int policyHolder;
    private String mFlightDate;
    private int mFlightId;
    private String mFlightRoute;
    private String mFlightTime;
    private String mFlightWeather;
    private int mFlightCheckBox;
    private int mFlightCoverage;
    private double mFlightFee;

    public FlightPolicy(int policyID, int policyHolder, String flightDate, int flightId, String flightRoute, String flightTime, String flightWeather, int flightCheckBox, int flightCoverage, double flightFee) {
        this.policyID = policyID;
        this.policyHolder = policyHolder;
        this.mFlightDate = flightDate;
        this.mFlightId = flightId;
        this.mFlightRoute = flightRoute;
        this.mFlightTime = flightTime;
        this.mFlightWeather = flightWeather;
        this.mFlightCheckBox = flightCheckBox;
        this.mFlightCoverage = flightCoverage;
        this.mFlightFee = flightFee;
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

    public int getFlightId() {
        return mFlightId;
    }

    public void setFlightId(int flightId) {
        mFlightId = flightId;
    }

    public String getFlightRoute() {
        return mFlightRoute;
    }

    public void setFlightRoute(String flightRoute) {
        mFlightRoute = flightRoute;
    }

    public String getFlightTime() {
        return mFlightTime;
    }

    public void setFlightTime(String flightTime) {
        mFlightTime = flightTime;
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
