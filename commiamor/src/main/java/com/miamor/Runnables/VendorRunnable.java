package com.miamor.Runnables;

import java.util.Collection;
import org.json.JSONException;
import com.miamor.Obj.Vendor;
import com.miamor.webservice.VendorService;

public class VendorRunnable implements Runnable{
	public static interface VendorInterface{
		void onStart();
		void Completed(Collection<Vendor> response);
	}
	
	VendorInterface vendorInterface;
	private int categoryId;
	private int pageNum;
	private String searchFor;
	private double lat;
	private double lng;
	
	public VendorRunnable(int CategoryId,int pageNum, String searchFor, double lat, double lng,VendorInterface vendorInterface) {
		this.categoryId=CategoryId;
		this.pageNum=pageNum;
		this.searchFor=searchFor;
		this.lat=lat;
		this.lng=lng;
		this.vendorInterface=vendorInterface;
	}
	
	@Override
	public void run() {
		try {
			vendorInterface.onStart();
			VendorService cat=new VendorService();
			Collection<Vendor> response=cat.getAllVendorsByCategory(categoryId, searchFor, pageNum, lat, lng);
			vendorInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
