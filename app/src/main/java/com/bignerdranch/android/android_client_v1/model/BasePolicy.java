package com.bignerdranch.android.android_client_v1.model;

/**
 * Created by Administrator on 2016/8/26.
 */
public class BasePolicy {
    private int mPolicyID;
    private double mFee;
    private String mPolicyName;
    private String mState;
    private String mPolicyDetail;
    public BasePolicy(){
        super();
    }
    public BasePolicy(double mFee, int mPolicyID, String mPolicyName, String mState, String mPolicyDetail) {
        this.mFee = mFee;
        this.mPolicyID = mPolicyID;
        this.mPolicyName = mPolicyName;
        this.mState = mState;
        this.mPolicyDetail = mPolicyDetail;
    }

    public int getPolicyID() {
        return mPolicyID;
    }

    public void setPolicyID(int mPolicyID) {
        this.mPolicyID = mPolicyID;
    }

    public double getFee() {
        return mFee;
    }

    public void setFee(double mFee) {
        this.mFee = mFee;
    }

    public String getPolicyName() {
        return mPolicyName;
    }

    public void setPolicyName(String mPolicyName) {
        this.mPolicyName = mPolicyName;
    }

    public String getState() {
        return mState;
    }

    public void setState(String mState) {
        this.mState = mState;
    }

    public String getPolicyDetail() {
        return mPolicyDetail;
    }

    public void setPolicyDetail(String mPolicyDetail) {
        this.mPolicyDetail = mPolicyDetail;
    }
}
