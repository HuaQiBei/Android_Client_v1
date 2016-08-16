package com.bignerdranch.android.android_client_v1;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Elvira on 2016/8/10.
 */
public class OrderList {
    private static OrderList sOrderList;
    public List<Order> mOrders;

    public OrderList(Context context) {
        String states[] = {"待支付", "生效中", "理赔中", "已失效"};
        mOrders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Order order = new Order();
            order.setOrderNum("Order #" + i + ";");
            order.setTitle("七天连锁酒店");
            order.setKind("酒店意外险");
            order.setOrderState(states[i % 4]);
            order.setPrice("$12.90");
            mOrders.add(order);
        }
    }

    public static OrderList get(Context context) {
        if (sOrderList == null) {
            sOrderList = new OrderList(context);
        }
        return sOrderList;
    }

    public List<Order> getOrders() {
        return mOrders;
    }

    public List<Order> getOrders(String state) {
        List<Order> stateOrders = new ArrayList<>();
        for (Order order:mOrders
             ) {
            if (order.getOrderState().equals(state))
                stateOrders.add(order);
        }

        return stateOrders;
    }

    public Order getOrder(UUID id) {
        for (Order order : mOrders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }
}
