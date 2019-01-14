package com.miamor.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class Get {
	
	public  String doGet(String url,List<NameValuePair> nameValuePairs, Header header){
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet();
		String jsonResponse=null;

		httpget.setHeader(header);

		try {
			url=addParamsToUrl(url,nameValuePairs);
			 URI website = new URI(url);
			 httpget.setURI(website);

		 // HttpResponse is an interface just like HttpPost.
            //Therefore we can't initialize them
            HttpResponse httpResponse = httpclient.execute(httpget);
            
         // CONVERT RESPONSE TO STRING
            jsonResponse = EntityUtils.toString(httpResponse.getEntity());

		} catch (ClientProtocolException e) {
		    // TODO Auto-generated catch block
		} catch (IOException e) {
		    e.printStackTrace();
		}catch (URISyntaxException e){}
		
		return jsonResponse;
	}

	public  String doGet(String url,List<NameValuePair> nameValuePairs){
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();

		HttpParams httpParameters = httpclient.getParams();
		HttpConnectionParams.setTcpNoDelay(httpParameters, true);

		HttpGet httpget = new HttpGet();
		String jsonResponse= null;

		try {
			url=addParamsToUrl(url,nameValuePairs);
			URI website = new URI(url);
			httpget.setURI(website);

			// HttpResponse is an interface just like HttpPost.
			//Therefore we can't initialize them
			HttpResponse httpResponse = httpclient.execute(httpget);

			// CONVERT RESPONSE TO STRING
			jsonResponse = EntityUtils.toString(httpResponse.getEntity());


		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			e.printStackTrace();
		}catch (URISyntaxException e){}

		return jsonResponse;
	}
	
	private String addParamsToUrl(String url,List<NameValuePair> nameValuePairs){
		    if(!url.endsWith("?")){
		        url += "?";
		    }
		    
	    	String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
	    	url += paramString;
	    	    
	    return url;
	}
}
