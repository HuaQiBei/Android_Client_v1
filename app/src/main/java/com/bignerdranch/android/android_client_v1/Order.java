package com.bignerdranch.android.android_client_v1;

import java.util.UUID;

/**
 * Created by Elvira on 2016/8/10.
 */
public class Order {
    private UUID mId;
    private String mTitle;
    private String mKind;
    private String mPrice;
    private String mOrderNum;
    private String mOrderState;

    public Order() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getOrderNum() {
        return mOrderNum;
    }

    public void setOrderNum(String orderNum) {
        mOrderNum = orderNum;
    }

    public String getOrderState() {
        return mOrderState;
    }

    public void setOrderState(String orderState) {
        mOrderState = orderState;
    }
}
