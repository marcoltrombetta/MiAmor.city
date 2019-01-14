package com.miamor.Runnables;

import com.miamor.Obj.VendorReview;
import com.miamor.webservice.VendorService;

import org.json.JSONException;

import java.util.Collection;

public class VendorReviewsRunnable implements Runnable{
	public static interface VendorReviewsInterface{
		void onStart();
		void Completed(Collection<VendorReview> response);
	}

	VendorReviewsInterface vendorReviewsInterface;
	private int vendorId=0;
	private int pageNum=1;

	public VendorReviewsRunnable(int vendorId,int pageNum, VendorReviewsInterface vendorReviewsInterface) {
		this.vendorId=vendorId;
		this.pageNum=pageNum;
		this.vendorReviewsInterface=vendorReviewsInterface;
	}
	
	@Override
	public void run() {
		try {
			vendorReviewsInterface.onStart();
			VendorService ven=new VendorService();
			Collection<VendorReview> response=ven.getVendorsReviews(vendorId,pageNum);
			vendorReviewsInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
