package com.bignerdranch.android.android_client_v1;

import android.os.Bundle;
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
import java.util.UUID;


public class LifeFragment extends Fragment {

	private RecyclerView mLifeRecyclerView;
	private LifeAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,  ViewGroup container,
							 Bundle savedInstanceState) {


		((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title2);

		View view = inflater.inflate(//android.R.layout.simple_list_item_1,
				R.layout.fragment_life,
				container, false);


		mLifeRecyclerView = (RecyclerView) view.findViewById(R.id.life_recycler_view);
		mLifeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		updateUI();

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		//updateUI();
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
			mAdapter.notifyDataSetChanged();

		}



	}

	private class LifeHolder extends RecyclerView.ViewHolder
			//implements View.OnClickListener
	{


		private Life mLife;

		//public TextView mTitleTextView;
		private TextView mTitleTextView;

		public LifeHolder(View itemView){
			super(itemView);

//			itemView.setOnClickListener(this);

			//mTitleTextView = (TextView)itemView;
			mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_life_title_text_view);
		}

		public void bindLife(Life life){
			mLife = life;

			mTitleTextView.setText(mLife.getTitle());

		}


//		@Override
//		public void onClick(View v) {
//			// Toast.makeText(getActivity(),mLife.getTitle() + " clicked!",Toast.LENGTH_SHORT)
//			//.show();
//			// Intent intent = LifeActivity.newIntent(getActivity(),mLife.getId());
//			Intent intent = LifePagerActivity.newIntent(getActivity(),mLife.getId());
//			refresh_id = mLife.getId();
//			startActivity(intent);
//			// startActivityForResult(intent,REQUEST_Life);
//
//		}
	}


	private class LifeAdapter extends RecyclerView.Adapter<LifeHolder>{

		private List<Life> mLifes;

		public LifeAdapter(List<Life> Lifes){
			mLifes = Lifes;
		}

		@Override
		public LifeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			Log.d("test","onCreateViewHolder");
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
			View view = layoutInflater
					.inflate(R.layout.list_item_life, parent, false);
			return new LifeHolder(view);
		}

		@Override
		public void onBindViewHolder(LifeHolder holder, int position) {

			Life life = mLifes.get(position);
			//holder.mTitleTextView.setText("测试商品");
			holder.bindLife(life);
		}

		@Override
		public int getItemCount() {
			return mLifes.size();
		}
	}

}
