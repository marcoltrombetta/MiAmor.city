package com.miamor.Runnables;

import com.miamor.Obj.CustomerMessage;
import com.miamor.webservice.CustomerService;

import org.json.JSONException;

import java.util.Collection;

public class CustomerMessageRunnable implements Runnable{
	public static interface MessageInterface{
		void onStart();
		void Completed(Collection<CustomerMessage> response);
	}

	MessageInterface messageInterface;
	private String custId;
	private int pageNum;
	private String token;

	public CustomerMessageRunnable(String custId, String token, int pageNum, MessageInterface messageInterface) {
		this.custId=custId;
		this.token=token;
		this.pageNum=pageNum;
		this.messageInterface=messageInterface;
	}
	
	@Override
	public void run() {
		try {
			messageInterface.onStart();
			CustomerService co=new CustomerService();
			Collection<CustomerMessage> response=co.getCustomerMessages(custId, token, pageNum);
			messageInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
