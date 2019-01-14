package com.miamor.Runnables;

import com.miamor.webservice.VendorService;

import org.json.JSONException;

public class VendorWriteReviewRunnable implements Runnable{
	public static interface VendorWriteReviewInterface{
		void SubmitCompleted();
	}

	VendorWriteReviewInterface vendorWriteReviewInterface;
	private String token;
	private String custId;
	private int vendorId;
	private String reviewText;
	private double rating;

	public VendorWriteReviewRunnable(String token,String custId,int vendorId, String reviewText, double rating, VendorWriteReviewInterface vendorWriteReviewInterface) {
		this.token=token;
		this.custId=custId;
		this.vendorId=vendorId;
		this.reviewText=reviewText;
		this.rating=rating;
		this.vendorWriteReviewInterface=vendorWriteReviewInterface;
	}
	
	@Override
	public void run() {
			VendorService vendor=new VendorService();
			try {
				vendor.submitReview(token,custId, vendorId, reviewText, rating);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			vendorWriteReviewInterface.SubmitCompleted();
	}
}
