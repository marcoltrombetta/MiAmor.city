package com.miamor.Runnables;

import com.miamor.Obj.VendorReview;
import com.miamor.webservice.VendorService;

import org.json.JSONException;

import java.util.Collection;

public class VendorReviewsDetailsRunnable implements Runnable{
	public static interface VendorReviewsDetailsInterface{
		void onStart();
		void Completed(VendorReview response);
	}

	VendorReviewsDetailsInterface vendorReviewsDetailsInterface;
	private int reviewId=0;

	public VendorReviewsDetailsRunnable(int reviewId, VendorReviewsDetailsInterface vendorReviewsDetailsInterface) {
		this.reviewId=reviewId;
		this.vendorReviewsDetailsInterface=vendorReviewsDetailsInterface;
	}
	
	@Override
	public void run() {
		try {
			vendorReviewsDetailsInterface.onStart();
			VendorService ven=new VendorService();
			VendorReview response=ven.getVendorsReviewsById(reviewId);
			vendorReviewsDetailsInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
