package com.bignerdranch.android.android_client_v1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ObtainPayFragment extends Fragment {
    Spinner mSpinner;
    Spinner mSpinner2;
    View up_photo;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obtain_pay, container, false);

        mSpinner = (Spinner) view.findViewById(R.id.spinner);
        mSpinner2 = (Spinner) view.findViewById(R.id.spinner2);

        // 建立Adapter1  保险类型
        adapter = ArrayAdapter.createFromResource(
                getActivity(),R.array.insurance_type,R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item_dowm);

        mSpinner .setAdapter(adapter);

        // 建立Adapter2  意外原因
        adapter2 = ArrayAdapter.createFromResource(
                getActivity(),R.array.reason,R.layout.spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_item_dowm);

        mSpinner2.setAdapter(adapter2);

        //点击上传图片
        up_photo = view.findViewById(R.id.up_photo);
        up_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
