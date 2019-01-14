package com.miamor.Runnables;

import org.json.JSONException;
import com.miamor.Obj.AuthenticationRequest;
import com.miamor.Obj.RegisterCustomer;
import com.miamor.webservice.Authentication;

public class RegisterRunnable implements Runnable{
	public static interface RegisterInterface{
		void RegisterCompleted(AuthenticationRequest response);
	}
	
	private RegisterCustomer registerCustomer;
	private RegisterInterface registerInterface;
	
	public RegisterRunnable(RegisterCustomer registerCustomer,RegisterInterface registerInterface) {
		this.registerCustomer=registerCustomer;
		this.registerInterface=registerInterface;
	}
	
	@Override
	public void run() {
		try {
			Authentication auth=new Authentication();
			AuthenticationRequest response=auth.doCustomerRegistration(registerCustomer);
			registerInterface.RegisterCompleted(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
