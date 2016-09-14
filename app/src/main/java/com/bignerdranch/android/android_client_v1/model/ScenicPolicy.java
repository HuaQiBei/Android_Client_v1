package com.bignerdranch.android.android_client_v1.model;

import android.util.Log;

/**
 * Created by Administrator on 2016/8/22.
 */
public class ScenicPolicy {
    private int policyID;
    private String startdate;
    private String enddate;
    private int policyholder;
    private double fee;
    private String scenicname;
    private String scenicweather;
    private String  insureduty;
    private String state;
    private int suminsured;

    public int getSuminsured() {
        return suminsured;
    }

    public void setSuminsured(int suminsured) {
        this.suminsured = suminsured;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ScenicPolicy(int policyID, String startdate, String enddate, int policyholder, double fee, String scenicname, String scenicweather, String insureduty, String state, int suminsured) {
        Log.d("test","in to scenic policy1");
        this.policyID = policyID;
        this.startdate = startdate;
        Log.d("test","in to scenic policy1");
        this.enddate = enddate;
        this.policyholder = policyholder;
        Log.d("test","in to scenic policy1");
        this.fee = fee;
        this.scenicname = scenicname;
        Log.d("test","in to scenic policy1");
        this.scenicweather = scenicweather;
        this.insureduty = insureduty;
        Log.d("test","in to scenic policy1");
        this.state = state;
        this.suminsured = suminsured;
    }

    public int getPolicyID() {
        return policyID;
    }

    public void setPolicyID(int policyID) {
        this.policyID = policyID;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public int getPolicyholder() {
        return policyholder;
    }

    public void setPolicyholder(int policyholder) {
        this.policyholder = policyholder;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getScenicname() {
        return scenicname;
    }

    public void setScenicname(String scenicname) {
        this.scenicname = scenicname;
    }

    public String getScenicweather() {
        return scenicweather;
    }

    public void setScenicweather(String scenicweather) {
        this.scenicweather = scenicweather;
    }

    public String getInsureduty() {
        return insureduty;
    }

    public void setInsureduty(String insureduty) {
        this.insureduty = insureduty;
    }

    @Override
    public String toString() {
        return "ScenicPolicy{" +
                "policyID=" + policyID +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", policyholder=" + policyholder +
                ", fee=" + fee +
                ", scenicname='" + scenicname + '\'' +
                ", scenicweather='" + scenicweather + '\'' +
                ", insureduty='" + insureduty + '\'' +
                ", state='" + state + '\'' +
                ", suminsured=" + suminsured +
                '}';
    }
}


