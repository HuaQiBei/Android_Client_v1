package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactFragment extends Fragment {
        private View bt_contactsSave;
        private View bt_addContacts;
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v=inflater.inflate(R.layout.fragment_contacts,container,false);
            bt_addContacts=v.findViewById(R.id.addContacts);

            bt_contactsSave=v.findViewById(R.id.contactsSave);
            bt_contactsSave.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            bt_addContacts.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),AddContactsActivity.class);
                    startActivity(intent);
                }
            });
            final AppCompatActivity activity = (AppCompatActivity) getActivity();

            Toolbar toolbar = (Toolbar) v.findViewById(R.id.id_toolbar);
            activity.setSupportActionBar(toolbar);

            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                }
            });
            return v;
        }


}
