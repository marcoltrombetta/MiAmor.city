package com.miamor.Runnables;

import org.json.JSONException;
import com.miamor.Obj.Vendor;
import com.miamor.webservice.VendorService;

public class VendorDetailsRunnable implements Runnable{
	public static interface VendorInterface{
		void onStart();
		void Completed(Vendor response);
	}
	
	VendorInterface vendorInterface;
	private int vendorId;
	private String custId;
	
	public VendorDetailsRunnable(int vendorId,String custId,VendorInterface vendorInterface) {
		this.vendorId=vendorId;
		this.custId=custId;
		this.vendorInterface=vendorInterface;
	}
	
	@Override
	public void run() {
		try {
			vendorInterface.onStart();
			VendorService cat=new VendorService();
			Vendor response=cat.getVendorsById(vendorId,custId);
			vendorInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
