package com.bignerdranch.android.util;

import com.bignerdranch.android.android_client_v1.model.FlightPolicy;
import com.bignerdranch.android.android_client_v1.model.ScenicPolicy;
import com.bignerdranch.android.android_client_v1.model.FlightPolicy;
import org.json.JSONException;

import java.io.IOException;

public interface Connect2Server {

    public String login(String username, String password) throws Exception;

    public String addAddress(String receivename, String telephone, String mailcode, String district, String detaildis) throws IOException;

    public String addContacts(String contactname, String telephone, String IDcard) throws IOException;

    public String modifyEmail(String newemail) throws IOException;

    public String addScenicPolicy(ScenicPolicy policy,String insuredname, String insuredIDcard) throws IOException, JSONException;

    public String showScenicPolicy(int policyID,String policyName) throws IOException, JSONException;

    public String addInsuredman(String insuredname, String insuredIDcard) throws IOException;

    public String findAllPolicy(int userID) throws IOException, JSONException;

    public String addFlightPolicy(FlightPolicy policy) throws IOException, JSONException;

    public String findDelayRate(String flightCode, String startCity, String endCity) throws IOException, JSONException;
}
