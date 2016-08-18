package com.bignerdranch.android.android_client_v1;

import java.util.UUID;

/**
 * Created by DELL on 2016/8/8.
 */
public class Life {
    private UUID mId;
    private String mTitle;
    private String mComment;
    private String mPrice;
    private String mCategory;
    private String mDistance;
    public Life() {
        //Generate unique identifier
        mId = UUID.randomUUID();
    }
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }
}
