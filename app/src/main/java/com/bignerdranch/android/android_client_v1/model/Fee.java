package com.bignerdranch.android.android_client_v1.model;

/**
 * Created by DELL on 2016/9/12.
 */
public class Fee {
    String deadordis;
    String medical;
    String fee;

    public Fee(String deadordis, String medical, String fee) {
        this.deadordis = deadordis;
        this.medical = medical;
        this.fee = fee;
    }

    public String getDeadordis() {
        return deadordis;
    }

    public void setDeadordis(String deadordis) {
        this.deadordis = deadordis;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Fee{" +
                "deadordis='" + deadordis + '\'' +
                ", medical='" + medical + '\'' +
                ", fee='" + fee + '\'' +
                '}';
    }
}
