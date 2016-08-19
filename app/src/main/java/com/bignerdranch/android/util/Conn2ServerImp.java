package com.bignerdranch.android.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.text.StaticLayout;
import android.util.Log;

public class Conn2ServerImp implements Connect2Server {
	private static final String TAG = "Conn2ServerImp";
	private static final String urlSpec = "http://172.27.207.3:8080/Server4android/Search.do";



	@Override
	public String login(String username, String password) throws Exception {
           String str=null;
		Log.d("test","in login!");

		HttpURLConnection conn=initPost(urlSpec);
		DataOutputStream out = new DataOutputStream(conn
				.getOutputStream());

		SetPostParams.setPostParam("username",username);
		SetPostParams.setPostParam("password",password);
		SetPostParams.setPostParam("flag","login");
		out.writeBytes(SetPostParams.getResult().toString());
		out.flush();
		out.close();
		str=new String(getPostReturn(conn));
	      return str;
	}

	@Override
	public String addAddress(String receivename, String telephone, String mailcode, String district, String detaildis) throws IOException {
		String str=null;
		Log.d("test","in add address!");

		HttpURLConnection conn=initPost(urlSpec);
		DataOutputStream out = new DataOutputStream(conn
				.getOutputStream());

		SetPostParams.setPostParam("receivename",receivename);
		SetPostParams.setPostParam("telephone",telephone);
		SetPostParams.setPostParam("mailcode",mailcode);
		SetPostParams.setPostParam("district",district);
		SetPostParams.setPostParam("detaildis",detaildis);
		SetPostParams.setPostParam("flag","addaddress");
		out.writeBytes(SetPostParams.getResult().toString());
		out.flush();
		out.close();
		str=new String(getPostReturn(conn));
		return str;

	}

	@Override
	public String addContacts(String contactname, String telephone, String IDcard) throws IOException {
		String str=null;
		Log.d("test","in add contacts!");

		HttpURLConnection conn=initPost(urlSpec);
		DataOutputStream out = new DataOutputStream(conn
				.getOutputStream());

		SetPostParams.setPostParam("contactname",contactname);
		SetPostParams.setPostParam("telephone",telephone);
		SetPostParams.setPostParam("IDcard",IDcard);
		SetPostParams.setPostParam("flag","addcontact");
		out.writeBytes(SetPostParams.getResult().toString());
		out.flush();
		out.close();
		str=new String(getPostReturn(conn));
		return str;

	}

	@Override
	public String modifyEmail(String newemail) throws IOException {
		String str=null;
		Log.d("test","in modify email!");

		HttpURLConnection conn=initPost(urlSpec);
		DataOutputStream out = new DataOutputStream(conn
				.getOutputStream());
		SetPostParams.setPostParam("userID","12");
		SetPostParams.setPostParam("newemail",newemail);
		SetPostParams.setPostParam("flag","modifyemail");
		out.writeBytes(SetPostParams.getResult().toString());
		out.flush();
		out.close();
		str=new String(getPostReturn(conn));
		return str;

	}

	public HttpURLConnection initPost(String urlstr) throws IOException {

		URL url = new URL(urlstr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		connection.connect();
		return connection;
	}

	public byte[] getPostReturn(HttpURLConnection connection) throws IOException {

		try {
			ByteArrayOutputStream outter = new ByteArrayOutputStream();
			InputStream in = connection.getInputStream();
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new IOException(connection.getResponseMessage() +
						": with " +
						urlSpec);
			}
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = in.read(buffer)) > 0) {
				outter.write(buffer, 0, bytesRead);
			}
			outter.close();
			return outter.toByteArray();
		} finally {
			connection.disconnect();
		}
	}

}
