package com.miamor.Runnables;

import java.util.Collection;
import org.json.JSONException;
import com.miamor.Obj.Vendor;
import com.miamor.webservice.VendorService;

public class MapRunnable implements Runnable{
	public static interface VendorMapInterface{
		void onStart();
		void Completed(Collection<Vendor> response);
	}
	
	VendorMapInterface vendorMapInterface;
	private int categoryId;
	private int pageNum;
	private String searchFor;
	private double lat;
	private double lng;
	
	public MapRunnable(int CategoryId,int pageNum, String searchFor, double lat, double lng,VendorMapInterface vendorMapInterface) {
		this.categoryId=CategoryId;
		this.pageNum=pageNum;
		this.searchFor=searchFor;
		this.lat=lat;
		this.lng=lng;
		this.vendorMapInterface=vendorMapInterface;
	}
	
	@Override
	public void run() {
		try {
			vendorMapInterface.onStart();
			VendorService cat=new VendorService();
			Collection<Vendor> response=cat.getAllVendorsByCategory(categoryId, searchFor, pageNum, lat, lng);
			vendorMapInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
