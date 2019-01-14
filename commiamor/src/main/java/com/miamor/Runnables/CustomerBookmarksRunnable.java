package com.miamor.Runnables;

import com.miamor.Obj.Blog;
import com.miamor.Obj.CustomerBookmark;
import com.miamor.webservice.BlogService;
import com.miamor.webservice.CustomerService;

import org.json.JSONException;

import java.util.Collection;

public class CustomerBookmarksRunnable implements Runnable{
	public static interface CustomerBookmarksInterface{
		void onStart();
		void Completed(Collection<CustomerBookmark> response);
	}

	CustomerBookmarksInterface customerBookmarksInterface;
	private String custId;
	private int pageNum;
	private String token;

	public CustomerBookmarksRunnable(String custId, String token, int pageNum, CustomerBookmarksInterface customerBookmarksInterface) {
		this.custId=custId;
		this.token=token;
		this.pageNum=pageNum;
		this.customerBookmarksInterface=customerBookmarksInterface;
	}

	@Override
	public void run() {
		try {
			customerBookmarksInterface.onStart();
			CustomerService co=new CustomerService();
			Collection<CustomerBookmark> response=co.getCustomerBookmarks(custId, pageNum,token);
			customerBookmarksInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
