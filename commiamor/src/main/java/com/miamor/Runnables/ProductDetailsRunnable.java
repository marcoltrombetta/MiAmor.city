package com.miamor.Runnables;

import com.miamor.Obj.Product;
import com.miamor.webservice.VendorProductService;

import org.json.JSONException;

public class ProductDetailsRunnable implements Runnable{
	public static interface ProductInterface{
		void onStart();
		void Completed(Product response);
	}

	ProductInterface productInterface;
	private int vendorId;
	private int productId;

	public ProductDetailsRunnable(int vendorId,int productId, ProductInterface productInterface) {
		this.vendorId=vendorId;
		this.productId=productId;
		this.productInterface=productInterface;
	}
	
	@Override
	public void run() {
		try {
			productInterface.onStart();
			VendorProductService cat=new VendorProductService();
			Product response=cat.getProductById(vendorId,productId);
			productInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
