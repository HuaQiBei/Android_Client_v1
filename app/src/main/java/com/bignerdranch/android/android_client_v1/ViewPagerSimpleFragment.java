package com.bignerdranch.android.android_client_v1;

/**
 * Created by Elvira on 2016/8/10.
 */

import android.content.Intent;
import android.os.AsyncTask;
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

import com.bignerdranch.android.android_client_v1.view.ShowScenicPolicyActivity;
import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ViewPagerSimpleFragment extends Fragment {
    private String mTitle;//接收用户传过来的title
    private static final String BUNDLE_TITLE = "title";//设置bundle的key
    private RecyclerView mPolicyRecyclerView;
    private PolicyAdapter mPolicyAdapter;

   PolicyHolder.ShowScenicPolicyTask mAuthTask=null;
    public static String resultString;
    public Connect2Server c2s=new Conn2ServerImp();


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
        if (view == null) {
            Log.d("test ", "ViewPagerSimpleFragment onCreateView()");
            //fragment内容
            view = inflater.inflate(R.layout.fragment_policy_list, container, false);
        }
        mPolicyRecyclerView = (RecyclerView) view.findViewById(R.id.policy_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mPolicyRecyclerView.setLayoutManager(linearLayoutManager);
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
        Log.d("test ", "ViewPagerSimpleFragment onStop()" + mTitle);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("test ", "ViewPagerSimpleFragment onPause()");
    }

    private void updateUI() {
        PolicyList policyList = PolicyList.get(getActivity());
        List<Policy> Policys;
        if (mTitle.equals("全部")) {
            Policys = policyList.getPolicys();
            Log.d("test ", "给我输出全部");
        } else {
            Policys = policyList.getPolicys(mTitle);
            Log.d("test ", "给我输出" + mTitle);
        }
        if (mPolicyAdapter == null) {
            mPolicyAdapter = new PolicyAdapter(Policys);
            mPolicyRecyclerView.setAdapter(mPolicyAdapter);
        } else {
            mPolicyRecyclerView.setAdapter(mPolicyAdapter);
            mPolicyAdapter.notifyDataSetChanged();
        }

    }

    private class PolicyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Policy mPolicy;
        private TextView mPolicyKind;
        private TextView mPolicyState;
        private TextView mPolicyPrice;
        private TextView mPolicyDetail;
        private Button mCheckPolicy;
        private Button mApply;
        private CardView mCardView;

        public PolicyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mPolicyKind = (TextView) itemView.findViewById(R.id.list_tv_PolicyKind);
            mPolicyState = (TextView) itemView.findViewById(R.id.list_tv_PolicyState);
            mPolicyDetail = (TextView) itemView.findViewById(R.id.list_tv_PolicyDetail);
            mPolicyPrice = (TextView) itemView.findViewById(R.id.list_tv_PolicyPrice);
            mCheckPolicy = (Button) itemView.findViewById(R.id.list_bt_Policy);
            mCheckPolicy.setOnClickListener(this);
            mApply = (Button) itemView.findViewById(R.id.list_bt_Apply);
            mCardView = (CardView) itemView.findViewById(R.id.mCardView);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mPolicy.getPolicyNum() + " clicked!", Toast.LENGTH_SHORT).show();
            switch (view.getId()){
                case R.id.list_bt_Policy:

                    int policyID=2;

                    if (mAuthTask != null) {
                        return;
                    }
                    Log.d("test","click the show detail button!");
                    mAuthTask = new ShowScenicPolicyTask(policyID);//为后台传递参数
                    mAuthTask.execute((Void) null);


                    break;

            }

        }
//-----------------------------------


    public class ShowScenicPolicyTask extends AsyncTask<Void, Void, String> {

        int mPolicyID;

        ShowScenicPolicyTask(int policyID) {
            this.mPolicyID=policyID;

        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("test","in show policy in background!");
            try {
                // Simulate network access.
                resultString = c2s.showScenicPolicy(mPolicyID);

                Log.d("test","resultString="+resultString);

                return resultString;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Noting";

        }

        @Override
        protected void onPostExecute(final String result) {
            mAuthTask = null;
            //showProgress(false);
            if(result!=null){
                Log.d("test","in to on PostExecute!");
                Log.d("test",result);
//                JSONArray respObjectArr = null;
//                JSONObject respJsonObj=null;

//                try {
//                    respObjectArr = new JSONArray(result);
//                    respJsonObj=respObjectArr.getJSONObject(0);
//
//                    scenicname.setText(respJsonObj.getString("scenicname"));
//                    scenicweather.setText(respJsonObj.getString("scenicweather"));
//                    startdate.setText(respJsonObj.getString("startdate"));
//                    enddate.setText(respJsonObj.getString("enddate"));
//                    insureduty.setText(respJsonObj.getString("insureduty"));
//                    fee.setText(Double.toString(respJsonObj.getDouble("fee")));
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


                Intent intent=new Intent(getActivity(),ShowScenicPolicyActivity.class);
                intent.putExtra("policydetail",resultString);
                startActivity(intent);
                Log.d("test","start show scenic policy activity sucessful!");
////                Intent intent=new Intent(getActivity(),MainActivity.class);
////                startActivity(intent);
            } else {
                Log.d("test","return nothing!");
                //getActivity().finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            // showProgress(false);
        }
    }
// --------------------------------------







        public void bindPolicy(Policy Policy) {
            mPolicy = Policy;

            mPolicyKind.setText(mPolicy.getKind());
            mPolicyState.setText(mPolicy.getPolicyState());
            mPolicyDetail.setText(mPolicy.getPolicyNum() + mPolicy.getTitle());
            mPolicyPrice.setText(mPolicy.getPrice());
            mApply.setVisibility(View.GONE);
            switch (mPolicy.getPolicyState()){
                case "待支付":{
                    mCheckPolicy.setText("去支付");
                    break;
                }
                case "生效中":{
                    mApply.setText("申请理赔");
                    mApply.setVisibility(View.VISIBLE);
                    break;
                }
                case "理赔中":{
                    mApply.setText("查看进度");
                    mApply.setVisibility(View.VISIBLE);
                    break;
                }
                default:

            }


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private class PolicyAdapter extends RecyclerView.Adapter<PolicyHolder> {

        private List<Policy> mPolicys;

        public PolicyAdapter(List<Policy> Policys) {
            mPolicys = Policys;
        }

        @Override
        public PolicyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Log.d("test", "" + "onCreateViewHolder");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_policy_list, parent, false);
            return new PolicyHolder(view);
        }

        @Override
        public void onBindViewHolder(PolicyHolder holder, int position) {
            Policy Policy = mPolicys.get(position);
            holder.bindPolicy(Policy);
        }

        @Override
        public int getItemCount() {
            return mPolicys.size();
        }
    }
}
