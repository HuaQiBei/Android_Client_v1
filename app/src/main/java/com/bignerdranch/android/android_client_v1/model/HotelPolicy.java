package com.bignerdranch.android.android_client_v1.model;

/**
 * Created by Administrator on 2016/8/20.
 */

public class HotelPolicy {

    private int policyID;
    private String startdate;
    private String enddate;
    private int policyholder;
    private double fee;
    private String hotelname;
    private String hotelposition;
    private int insurednum;
    private String insureduty;

    public HotelPolicy(String startdate, String enddate, int policyholder, double fee, String hotelname, String hotelposition, int insurednum, String insureduty) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.policyholder = policyholder;
        this.fee = fee;
        this.hotelname = hotelname;
        this.hotelposition = hotelposition;
        this.insurednum = insurednum;
        this.insureduty = insureduty;
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
    public String getHotelname() {
        return hotelname;
    }
    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }
    public String getHotelposition() {
        return hotelposition;
    }
    public void setHotelposition(String hotelposition) {
        this.hotelposition = hotelposition;
    }
    public int getInsurednum() {
        return insurednum;
    }
    public void setInsurednum(int insurednum) {
        this.insurednum = insurednum;
    }
    public String getInsureduty() {
        return insureduty;
    }
    public void setInsureduty(String insureduty) {
        this.insureduty = insureduty;
    }

}

