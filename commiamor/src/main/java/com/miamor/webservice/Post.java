package com.miamor.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

public class Post {

	public String doPost(String url1, List<NameValuePair> nameValuePairs, Header header) {
		String res="";
		try {
			URL url = new URL(url1);
			Map<String, Object> params = new LinkedHashMap<String, Object>();

			for(NameValuePair n:nameValuePairs){
				params.put(n.getName(),n.getValue());
			}

			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (postData.length() != 0) postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			String basicAuth = header.getValue();
			conn.setRequestProperty(header.getName(), basicAuth);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);

			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			for (int c = in.read(); c != -1; c = in.read())
				res=res+Character.toString((char)c);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}


	public String doPost(String url,List<NameValuePair> nameValuePairs){
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		String jsonResponse=null;

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			// HttpResponse is an interface just like HttpPost.
			//Therefore we can't initialize them
			HttpResponse httpResponse = httpclient.execute(httppost);

			// According to the JAVA API, InputStream constructor do nothing.
			//So we can't initialize InputStream although it is not an interface
			InputStream inputStream = httpResponse.getEntity().getContent();

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			StringBuilder stringBuilder = new StringBuilder();

			String bufferedStrChunk = null;

			while((bufferedStrChunk = bufferedReader.readLine()) != null){
				stringBuilder.append(bufferedStrChunk);
			}

			jsonResponse=stringBuilder.toString();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonResponse;
	}
}
