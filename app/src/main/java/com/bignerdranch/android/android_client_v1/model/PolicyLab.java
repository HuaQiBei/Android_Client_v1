package com.bignerdranch.android.android_client_v1.model;

import android.content.Context;

import com.bignerdranch.android.android_client_v1.Policy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class PolicyLab {

    private static PolicyLab sPolicyLab;
    private List<BasePolicy> mPolicys;

    @Override
    public String toString() {
        String str = "";
        for (int j = 0; j < mPolicys.size(); j++) {
            str += "/" + mPolicys.get(j);

        }


        return "PolicyLab{" +
                "mPolicys=" + str +
                '}';
    }

    public static PolicyLab get(String policyArray) throws JSONException {
        if (sPolicyLab == null) {
            sPolicyLab = new PolicyLab(policyArray);
        }
        return sPolicyLab;
    }

    private PolicyLab(String policyArray) throws JSONException {
        mPolicys = new ArrayList<>();
        JSONArray ja = new JSONArray(policyArray);
        for (int i = 0; i < ja.length(); i++) {
            BasePolicy policy = new BasePolicy();
            JSONObject jo = ja.getJSONObject(i);
            policy.setPolicyName(jo.getString("policyname"));
            policy.setPolicyID(jo.getInt("policyID"));
            policy.setFee(jo.getDouble("fee"));
            policy.setPolicyDetail(jo.getString("detail"));
            policy.setState(jo.getString("state"));
            mPolicys.add(policy);
        }


    }

    public List<BasePolicy> getPolicys() {
        return mPolicys;
    }

    public List<BasePolicy> getPolicys(String state) {
        List<BasePolicy> statePolicys = new ArrayList<>();
        for (BasePolicy Policy : mPolicys
                ) {
            if (Policy.getState().equals(state))
                statePolicys.add(Policy);
        }

        return statePolicys;
    }

    public void setPolicys(List<BasePolicy> Policys) {
        this.mPolicys = Policys;
    }

    public void addPolicy(BasePolicy policy) {
        mPolicys.add(policy);
    }

    public static void dismiss() {
        sPolicyLab = null;
    }
}
