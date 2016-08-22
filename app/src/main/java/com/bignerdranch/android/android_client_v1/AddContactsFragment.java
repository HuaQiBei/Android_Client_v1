package com.bignerdranch.android.android_client_v1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bignerdranch.android.util.Conn2ServerImp;
import com.bignerdranch.android.util.Connect2Server;

public class AddContactsFragment extends Fragment {

    private AddContactsTask mAuthTask = null;
    public static String resultString;
    public Connect2Server c2s=new Conn2ServerImp();


    private View bt_add_contacts_OK;
    private EditText contactname;
    private EditText telephone;
    private EditText IDcard;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_contacts, container, false);
        bt_add_contacts_OK = v.findViewById(R.id.add_contacts_OK);

        contactname=(EditText)v.findViewById(R.id.tv_contacts_name);
        telephone=(EditText)v.findViewById(R.id.tv_contacts_num);
        IDcard=(EditText)v.findViewById(R.id.tv_contacts_ID);


        bt_add_contacts_OK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String contactnamestr=contactname.getText().toString();
                String telephonestr=telephone.getText().toString();
                String IDcardstr=IDcard.getText().toString();
                if (mAuthTask != null) {
                    return;
                }

                mAuthTask = new AddContactsTask(contactnamestr,telephonestr,IDcardstr);//为后台传递参数
                mAuthTask.execute((Void) null);

                Intent intent = new Intent(getActivity(),ContactActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    public class AddContactsTask extends AsyncTask<Void, Void, String> {

        String mContactnamestr;
        String mTelephonestr;
        String mIDcardstr;

        AddContactsTask(String Contactnamestr, String Telephonestr, String IDcardstr) {
            mContactnamestr=Contactnamestr;
            mTelephonestr=Telephonestr;
            mIDcardstr=IDcardstr;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("test","in to do addcontact in background!");
            try {
                // Simulate network access.
                resultString = c2s.addContacts(mContactnamestr,mTelephonestr,mIDcardstr);
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
            Log.d("test","in to on PostExecute!");

            if (result!=null) {
                Log.d("test",result);
                Intent intent=new Intent(getActivity(),MyInfoActivity.class);
                startActivity(intent);

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



}
