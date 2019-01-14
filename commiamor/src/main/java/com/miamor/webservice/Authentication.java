package com.miamor.webservice;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.miamor.Obj.AuthenticationRequest;
import com.miamor.Obj.Globals;
import com.miamor.Obj.LoginCustomer;
import com.miamor.Obj.RegisterCustomer;

public class Authentication {
	private Post post;
	
	public Authentication(){
		post=new Post();
	}
	
	public AuthenticationRequest doCustomerRegistration(RegisterCustomer registerCustomer) throws JSONException{
		AuthenticationRequest auth=new AuthenticationRequest();

	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
	    nameValuePairs.add(new BasicNameValuePair("Id", Integer.toString(registerCustomer.getId())));
	    nameValuePairs.add(new BasicNameValuePair("FirstName", registerCustomer.getFirstname()));
	    nameValuePairs.add(new BasicNameValuePair("LastName", registerCustomer.getLastname()));
	    nameValuePairs.add(new BasicNameValuePair("Email", registerCustomer.getEmail()));
	    nameValuePairs.add(new BasicNameValuePair("Password", registerCustomer.getPassword()));

	    String json=post.doPost(Globals.ServerUrl+"/Customer/RegisterCustomerApp",nameValuePairs);


		return getCustomerRequest(json.toString());
	}
	
	public AuthenticationRequest doCustomerLogin(LoginCustomer LoginCustomer) throws JSONException{
		AuthenticationRequest auth=new AuthenticationRequest();

	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
	    nameValuePairs.add(new BasicNameValuePair("Email", LoginCustomer.getEmail()));
	    nameValuePairs.add(new BasicNameValuePair("Password", LoginCustomer.getPassword()));
	    nameValuePairs.add(new BasicNameValuePair("RememberMe", Boolean.toString(true)));

	    String json=post.doPost(Globals.ServerUrl+"/Customer/LoginCustomerApp",nameValuePairs);
	    
		return getCustomerRequest(json.toString());
	}
	
	private AuthenticationRequest getCustomerRequest(String request) throws JSONException{
		
		AuthenticationRequest authReq=new AuthenticationRequest();
		JSONObject jObject = new JSONObject(request);
		JSONObject jObj = jObject.getJSONObject("data");

		if(!jObj.isNull("CustId") && !jObj.isNull("Token")){
			authReq.setCustId(jObj.getString("CustId"));
			authReq.setToke(jObj.getString("Token"));
			authReq.setFirstname(jObj.getString("FirstName"));
		}

		return authReq;
	}
}
