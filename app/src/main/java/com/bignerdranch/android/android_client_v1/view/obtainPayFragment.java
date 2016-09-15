package com.bignerdranch.android.android_client_v1.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.bignerdranch.android.android_client_v1.R;

import java.text.ParseException;
import java.util.Calendar;

public class ObtainPayFragment extends Fragment {
    Spinner mSpinner;
    Spinner mSpinner2;
    View up_photo;
    private TextView policy_NO;
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
        policy_NO = (TextView)view .findViewById(R.id.policy_NO);
        policy_NO.setText(getArguments().getInt("policyID")+"");

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
