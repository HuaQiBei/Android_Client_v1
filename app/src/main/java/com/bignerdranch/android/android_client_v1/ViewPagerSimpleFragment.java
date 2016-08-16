package com.bignerdranch.android.android_client_v1;

/**
 * Created by Elvira on 2016/8/10.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewPagerSimpleFragment extends Fragment {
    private String mTitle;//接收用户传过来的title
    private static final String BUNDLE_TITLE = "title";//设置bundle的key
    private RecyclerView mOrderRecyclerView;
    private OrderAdapter mOrderAdapter;


    View view = null;
    /**
     * fragment一般使用newInstance方法new出实例
     */
    public static ViewPagerSimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);

        ViewPagerSimpleFragment fragment = new ViewPagerSimpleFragment();
        /**
         * 提供结构参数给这个Fragment。只能在Fragment被依附到Activity之前被调用(这句话可以这样理解，
         * setArgument方法的使用必须要在FragmentTransaction 的commit之前使用。 )，也就是说
         * 你应该在构造fragment之后立刻调用它。这里提供的参数将在fragment destroy 和creation被保留。
         *
         *
         * 官方推荐Fragment.setArguments(Bundle bundle)这种方式来传递参数，而不推荐通过构造方法直接来传递参数
         * 这是因为假如Activity重新创建（横竖屏切换）时，会重新构建它所管理的Fragment，原先的Fragment的字段值将会全
         * 部丢失，但是通过Fragment.setArguments(Bundle bundle)方法设置的bundle会保留下来。所以尽量使用
         * Fragment.setArguments(Bundle bundle)方式来传递参数
         */
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(BUNDLE_TITLE);
        }
        if(view == null) {
            Log.d("test ", "ViewPagerSimpleFragment onCreateView()");
            //fragment内容
            view = inflater.inflate(R.layout.fragment_order_list, container, false);
        }
        mOrderRecyclerView = (RecyclerView) view.findViewById(R.id.order_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mOrderRecyclerView.setLayoutManager(linearLayoutManager);
        updateUI();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("test ", "ViewPagerSimpleFragment onResume()");
        updateUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test ", "ViewPagerSimpleFragment onDestroy()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("test ", "ViewPagerSimpleFragment onStop()"+mTitle);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("test ", "ViewPagerSimpleFragment onPause()");
    }

    private void updateUI() {
        OrderList orderList = OrderList.get(getActivity());
        List<Order> orders;
        if (mTitle.equals("全部")) {
            orders = orderList.getOrders();
            Log.d("test ", "给我输出全部");
        }else {
            orders = orderList.getOrders(mTitle);
            Log.d("test ", "给我输出"+mTitle);
        }
        if (mOrderAdapter == null) {
            mOrderAdapter = new OrderAdapter(orders);
            mOrderRecyclerView.setAdapter(mOrderAdapter);
        } else {
            mOrderRecyclerView.setAdapter(mOrderAdapter);
            mOrderAdapter.notifyDataSetChanged();
        }

    }

    private class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Order mOrder;
        private TextView mOrderKind;
        private TextView mOrderState;
        private TextView mOrderPrice;
        private TextView mOrderDetail;
        private Button mCheckOrder;
        private Button mApply;
        private CardView mCardView;

        public OrderHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mOrderKind = (TextView) itemView.findViewById(R.id.list_tv_OrderKind);
            mOrderState = (TextView) itemView.findViewById(R.id.list_tv_OrderState);
            mOrderDetail = (TextView) itemView.findViewById(R.id.list_tv_OrderDetail);
            mOrderPrice = (TextView) itemView.findViewById(R.id.list_tv_OrderPrice);
            mCheckOrder = (Button) itemView.findViewById(R.id.list_bt_Order);
            mApply = (Button) itemView.findViewById(R.id.list_bt_Apply);
            mCardView = (CardView) itemView.findViewById(R.id.mCardView);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mOrder.getOrderNum() + " clicked!", Toast.LENGTH_SHORT).show();
        }

        public void bindOrder(Order order) {
            mOrder = order;

            mOrderKind.setText(mOrder.getKind());
            mOrderState.setText(mOrder.getOrderState());
            mOrderDetail.setText(mOrder.getOrderNum() + mOrder.getTitle());
            mOrderPrice.setText(mOrder.getPrice());
            mApply.setVisibility(View.GONE);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

        private List<Order> mOrders;

        public OrderAdapter(List<Order> orders) {
            mOrders = orders;
        }

        @Override
        public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d("test", ""+"onCreateViewHolder");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_order_list, parent, false);
            return new OrderHolder(view);
        }

        @Override
        public void onBindViewHolder(OrderHolder holder, int position) {
            Order order = mOrders.get(position);
            holder.bindOrder(order);
        }

        @Override
        public int getItemCount() {
            return mOrders.size();
        }
    }
}
