package com.miamor.Runnables;

import java.util.Collection;
import org.json.JSONException;
import com.miamor.Obj.Product;
import com.miamor.webservice.VendorProductService;

public class ProductRunnable implements Runnable{
	public static interface ProductInterface{
		void onStart();
		void Completed(Collection<Product> response);
	}
	
	ProductInterface productInterface;
	private int vendorId;
	private int categoryId;
	private int pageNum;
		
	public ProductRunnable(int vendorId,int categoryId,int pageNum,ProductInterface campaignInterface) {
		this.vendorId=vendorId;
		this.categoryId=categoryId;
		this.pageNum=pageNum;
		this.productInterface=campaignInterface;
	}
	
	@Override
	public void run() {
		try {
			productInterface.onStart();
			VendorProductService co=new VendorProductService();
			Collection<Product> response=co.getProductByVendorId(vendorId,categoryId, pageNum);
			productInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
