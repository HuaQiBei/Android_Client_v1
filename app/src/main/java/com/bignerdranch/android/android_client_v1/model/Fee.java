package com.bignerdranch.android.android_client_v1.model;

/**
 * Created by DELL on 2016/9/12.
 */
public class Fee {
    String rg_1_value;
    String rg_2_value;
    String fee;

    public Fee(String rg_1_value, String rg_2_value, String fee) {
        this.rg_1_value = rg_1_value;
        this.rg_2_value = rg_2_value;
        this.fee = fee;
    }

    public String getRg_1_value() {
        return rg_1_value;
    }

    public void setRg_1_value(String rg_1_value) {
        this.rg_1_value = rg_1_value;
    }

    public String getRg_2_value() {
        return rg_2_value;
    }

    public void setRg_2_value(String rg_2_value) {
        this.rg_2_value = rg_2_value;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
