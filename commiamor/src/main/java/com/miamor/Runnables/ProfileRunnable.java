package com.miamor.Runnables;


import org.json.JSONException;
import com.miamor.Obj.ProfileCustomer;
import com.miamor.webservice.CustomerService;

public class ProfileRunnable implements Runnable{
	public static interface ProfileInterface{
		void onStart();
		void Completed(ProfileCustomer response);
	}
	
	ProfileInterface profileInterface;
	String custId;
	String token;

	public ProfileRunnable(String custId, String token,ProfileInterface profileInterface) {
		this.profileInterface=profileInterface;
		this.custId=custId;
		this.token=token;
	}
	
	@Override
	public void run() {
			profileInterface.onStart();
			CustomerService cust=new CustomerService();
			ProfileCustomer customer=new ProfileCustomer();

		try {
			customer=cust.getCustomerDetails(custId,token);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		profileInterface.Completed(customer);
	}
	
}
