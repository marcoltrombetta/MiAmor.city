package com.miamor.Runnables;

import com.miamor.Obj.ProfileCustomer;
import com.miamor.webservice.ProfileService;

import org.json.JSONException;

public class CustomerSubmitSettingRunnable implements Runnable{
	public static interface CustomerSubmitSettingInterface{
		void Completed();
	}

	CustomerSubmitSettingInterface customerSubmitSettingInterface;
	private String custId;
	private String token;
	private ProfileCustomer profileCustomer;

	public CustomerSubmitSettingRunnable(String token,String custId, ProfileCustomer profileCustomer, CustomerSubmitSettingInterface customerSubmitSettingInterface) {
		this.custId=custId;
		this.token=token;
		this.profileCustomer=profileCustomer;
		this.customerSubmitSettingInterface=customerSubmitSettingInterface;
	}
	
	@Override
	public void run() {
		try {
			ProfileService pro=new ProfileService();
			pro.submitSettings(token,custId,profileCustomer);
			customerSubmitSettingInterface.Completed();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
