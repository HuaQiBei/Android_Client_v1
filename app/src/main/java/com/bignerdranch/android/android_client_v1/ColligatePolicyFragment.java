package com.bignerdranch.android.android_client_v1;

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

import java.text.ParseException;
import java.util.Calendar;

public class ColligatePolicyFragment extends Fragment {
    Spinner mSpinner1;
    Spinner mSpinner2;
    Spinner mSpinner3;
    Spinner mSpinner4;
    Spinner mSpinner5;
    Spinner mSpinner6;
    Spinner mSpinner7;
    Spinner mSpinner8;
    TextView mBegin_date;
    TextView mEnd_date;

    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter3;
    ArrayAdapter<CharSequence> adapter4;
    ArrayAdapter<CharSequence> adapter5;
    ArrayAdapter<CharSequence> adapter6;
    ArrayAdapter<CharSequence> adapter7;
    ArrayAdapter<CharSequence> adapter8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_colligate_policy, container, false);

        mBegin_date = (TextView) view.findViewById(R.id.begin_date);
        mEnd_date = (TextView) view.findViewById(R.id.end_date);
        getDate();

        mSpinner1 = (Spinner) view.findViewById(R.id.spinner1);
        mSpinner2 = (Spinner) view.findViewById(R.id.spinner2);
        mSpinner3 = (Spinner) view.findViewById(R.id.spinner3);
        mSpinner4 = (Spinner) view.findViewById(R.id.spinner4);
        mSpinner5 = (Spinner) view.findViewById(R.id.spinner5);
        mSpinner6 = (Spinner) view.findViewById(R.id.spinner6);
        mSpinner7 = (Spinner) view.findViewById(R.id.spinner7);
        mSpinner8 = (Spinner) view.findViewById(R.id.spinner8);

        // 建立Adapter1  飞机延误保障
        adapter1 = ArrayAdapter.createFromResource(
                getActivity(),R.array.airport_delay,R.layout.spinner_item);
        adapter1.setDropDownViewResource(R.layout.spinner_item_dowm);
        mSpinner1.setAdapter(adapter1);

        // 建立Adapter2  意外原因
        adapter2 = ArrayAdapter.createFromResource(
                getActivity(),R.array.accident,R.layout.spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_item_dowm);
        mSpinner2.setAdapter(adapter2);

        // 建立Adapter3  意外医疗
        adapter3 = ArrayAdapter.createFromResource(
                getActivity(),R.array.accident,R.layout.spinner_item);
        adapter3.setDropDownViewResource(R.layout.spinner_item_dowm);
        mSpinner3.setAdapter(adapter3);

        // 建立Adapter4  意外原因
        adapter4 = ArrayAdapter.createFromResource(
                getActivity(),R.array.accident,R.layout.spinner_item);
        adapter4.setDropDownViewResource(R.layout.spinner_item_dowm);
        mSpinner4.setAdapter(adapter4);

        // 建立Adapter5  意外原因
        adapter5 = ArrayAdapter.createFromResource(
                getActivity(),R.array.accident,R.layout.spinner_item);
        adapter5.setDropDownViewResource(R.layout.spinner_item_dowm);
        mSpinner5.setAdapter(adapter5);

        // 建立Adapter6  意外原因
        adapter6 = ArrayAdapter.createFromResource(
                getActivity(),R.array.accident,R.layout.spinner_item);
        adapter6.setDropDownViewResource(R.layout.spinner_item_dowm);
        mSpinner6.setAdapter(adapter6);

        // 建立Adapter2  意外原因
        adapter7 = ArrayAdapter.createFromResource(
                getActivity(),R.array.accident,R.layout.spinner_item);
        adapter7.setDropDownViewResource(R.layout.spinner_item_dowm);
        mSpinner7.setAdapter(adapter7);

        // 建立Adapter2  意外原因
        adapter8 = ArrayAdapter.createFromResource(
                getActivity(),R.array.accident,R.layout.spinner_item);
        adapter8.setDropDownViewResource(R.layout.spinner_item_dowm);
        mSpinner8.setAdapter(adapter8);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /*获取航班日期*/
    private void getDate() {
        final Calendar c = Calendar.getInstance();
        mBegin_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, monthOfYear, dayOfMonth);
                                CharSequence date = DateFormat.format("yyy-MM-dd", c);
                                mBegin_date.setText(date);
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });
        mEnd_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, monthOfYear, dayOfMonth);
                                CharSequence date = DateFormat.format("yyy-MM-dd", c);
                                mEnd_date.setText(date);
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });
    }

}
