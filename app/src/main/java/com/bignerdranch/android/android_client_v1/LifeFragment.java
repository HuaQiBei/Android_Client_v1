package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class LifeFragment extends Fragment {

    private RecyclerView mLifeRecyclerView;
    private LifeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("test", "LifeFragment onCreateView");
        View view = inflater.inflate(//android.R.layout.simple_list_item_1,
                R.layout.fragment_life,
                container, false);
        return view;
    }

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mLifeRecyclerView = (RecyclerView) view.findViewById(R.id.life_recycler_view);
		mLifeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		updateUI();

	}

	@Override
	public void onResume() {
		Log.d("test","LifeFragment onResume");
		super.onResume();
		updateUI();
	}

	@Override
	public void onStop() {
		Log.d("test","LifeFragment onStop");
		super.onStop();
	}

	private void updateUI(){
		List<Life> mLifes = new ArrayList<>();
		for (int i = 0; i < 10; i++){
			Life Life = new Life();
			Life.setTitle("Life #" + i);
			mLifes.add(Life);
		}
		List<Life> Lifes = mLifes;

		if (mAdapter == null){
			mAdapter = new LifeAdapter(Lifes);
			mLifeRecyclerView.setAdapter(mAdapter);
		}else {
			mLifeRecyclerView.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();

		}



	}

	private class LifeHolder extends RecyclerView.ViewHolder
			implements View.OnClickListener
	{


		private Life mLife;

		//public TextView mTitleTextView;
		private TextView mTitleTextView;

		public LifeHolder(View itemView){
			super(itemView);

			itemView.setOnClickListener(this);

			//mTitleTextView = (TextView)itemView;
			mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_life_title_text_view);
		}

		public void bindLife(Life life){
			mLife = life;

			mTitleTextView.setText(mLife.getTitle());

		}


		@Override
		public void onClick(View v) {
			Intent intent = LifeDetailActivity.newIntent(getActivity(),mLife.getId());
			startActivity(intent);

			// startActivityForResult(intent,REQUEST_Life);

		}
	}


	private class LifeAdapter extends RecyclerView.Adapter<LifeHolder>{

		private List<Life> mLifeList;

		public LifeAdapter(List<Life> Lifes){
			mLifeList = Lifes;
		}

		@Override
		public LifeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			Log.d("test","onCreateViewHolder");
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
			View view = layoutInflater
					.inflate(R.layout.item_life_list, parent, false);
			return new LifeHolder(view);
		}

		@Override
		public void onBindViewHolder(LifeHolder holder, int position) {
			Log.d("life","position"+position);

			Life life = mLifeList.get(position);
			//holder.mTitleTextView.setText("测试商品");
			holder.bindLife(life);
		}

		@Override
		public int getItemCount() {
			return mLifeList.size();
		}
	}

}
