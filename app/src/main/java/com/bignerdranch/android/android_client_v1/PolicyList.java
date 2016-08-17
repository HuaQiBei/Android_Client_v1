package com.bignerdranch.android.android_client_v1;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Elvira on 2016/8/10.
 */
public class PolicyList {
    private static PolicyList sPolicyList;
    public List<Policy> mPolicys;

    public PolicyList(Context context) {
        String states[] = {"待支付", "生效中", "理赔中", "已失效"};
        mPolicys = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Policy Policy = new Policy();
            Policy.setPolicyNum("Policy #" + i + ";");
            Policy.setTitle("七天连锁酒店");
            Policy.setKind("酒店意外险");
            Policy.setPolicyState(states[i % 4]);
            Policy.setPrice("$12.90");
            mPolicys.add(Policy);
        }
    }

    public static PolicyList get(Context context) {
        if (sPolicyList == null) {
            sPolicyList = new PolicyList(context);
        }
        return sPolicyList;
    }

    public List<Policy> getPolicys() {
        return mPolicys;
    }

    public List<Policy> getPolicys(String state) {
        List<Policy> statePolicys = new ArrayList<>();
        for (Policy Policy:mPolicys
             ) {
            if (Policy.getPolicyState().equals(state))
                statePolicys.add(Policy);
        }

        return statePolicys;
    }

    public Policy getPolicy(UUID id) {
        for (Policy Policy : mPolicys) {
            if (Policy.getId().equals(id)) {
                return Policy;
            }
        }
        return null;
    }
}
