package com.miamor.Runnables;

import com.miamor.Obj.CustomerReview;
import com.miamor.webservice.CustomerService;

import org.json.JSONException;

import java.util.Collection;

public class CustomerReviewRunnable implements Runnable{
	public static interface ReviewInterface{
		void onStart();
		void Completed(Collection<CustomerReview> response);
	}

	ReviewInterface reviewInterface;
	private String custId;
	private int pageNum;
	private String token;

	public CustomerReviewRunnable(String custId, String token, int pageNum, ReviewInterface reviewInterface) {
		this.custId=custId;
		this.token=token;
		this.pageNum=pageNum;
		this.reviewInterface=reviewInterface;
	}
	
	@Override
	public void run() {
		try {
			reviewInterface.onStart();
			CustomerService co=new CustomerService();
			Collection<CustomerReview> response=co.getCustomerReviews(custId, token, pageNum);
			reviewInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
