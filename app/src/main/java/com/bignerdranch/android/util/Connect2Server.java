package com.bignerdranch.android.util;


import java.io.IOException;

public interface Connect2Server {

	    public String login(String username, String password) throws Exception;
	    public String addAddress(String receivename,String telephone,String mailcode,String district,String detaildis) throws IOException;
	    public String addContacts(String contactname,String telephone,String IDcard) throws IOException;
		public String modifyEmail(String newemail) throws IOException;
}
