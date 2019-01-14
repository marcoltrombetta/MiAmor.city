package com.miamor.Runnables;

import org.json.JSONException;
import com.miamor.Obj.AuthenticationRequest;
import com.miamor.Obj.LoginCustomer;
import com.miamor.webservice.Authentication;

public class LoginRunnable implements Runnable{
	public static interface LoginInterface{
		void LoginCompleted(AuthenticationRequest response);
	}
	
	LoginInterface loginInterface;
	private LoginCustomer login;
		
	public LoginRunnable(LoginCustomer loginCustomer,LoginInterface loginInterface) {
		this.login=loginCustomer;
		this.loginInterface=loginInterface;
	}
	
	@Override
	public void run() {
		try {
			Authentication auth=new Authentication();
			AuthenticationRequest response=auth.doCustomerLogin(login);
			loginInterface.LoginCompleted(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
