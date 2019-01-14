package com.miamor.Runnables;

import com.miamor.webservice.VendorService;

import org.json.JSONException;

public class VendorBookmarkRunnable implements Runnable{
	public static interface VendorBookmarkInterface{
		void Completed(String response);
	}

	VendorBookmarkInterface vendorBookmarkInterface;
	private String token;
	private String custId;
	private int vendorId;

	public VendorBookmarkRunnable(String token, String custId, int vendorId, VendorBookmarkInterface vendorBookmarkInterface) {
		this.token=token;
		this.custId=custId;
		this.vendorId=vendorId;
		this.vendorBookmarkInterface=vendorBookmarkInterface;
	}
	
	@Override
	public void run() {
			VendorService vendor=new VendorService();
			String res="";
			try {
				res=vendor.bookmark(token,custId, vendorId);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		vendorBookmarkInterface.Completed(res);
	}
}
