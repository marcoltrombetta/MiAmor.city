package com.miamor.webservice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miamor.Obj.Globals;
import com.miamor.Obj.Picture;
import com.miamor.Obj.Product;
import com.miamor.Obj.ProductPicture;

public class VendorProductService {
	private Get get;
	private Post post;
	
	public VendorProductService(){
		get=new Get();
		post=new Post();
	}
	
	public Collection<Product> getProductByVendorId(int VendorId,int CategoryId, int pageNum) throws JSONException{
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	    nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
	    nameValuePairs.add(new BasicNameValuePair("VendorId", Integer.toString(VendorId)));
	    nameValuePairs.add(new BasicNameValuePair("CategoryId", Integer.toString(CategoryId)));
	    nameValuePairs.add(new BasicNameValuePair("PageId", Integer.toString(pageNum)));

	    String json=get.doGet(Globals.ServerUrl+"/Vendor/MApp_GetVendorProducts",nameValuePairs);
	    
		return getRequest(json.toString());
	}

	public Product getProductById(int VendorId, int ProductId) throws JSONException{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
		nameValuePairs.add(new BasicNameValuePair("VendorId", Integer.toString(VendorId)));
		nameValuePairs.add(new BasicNameValuePair("Id", Integer.toString(ProductId)));

		String json=post.doPost(Globals.ServerUrl + "/Vendor/MApp_GetVendorProduct", nameValuePairs);

		return getRequest_Prod(json.toString());
	}

	private Product getRequest_Prod(String request) throws JSONException{

		Product product=new Product();
		Gson gson=new Gson();
		product = gson.fromJson(request, Product.class);

		return product;
	}
	
	private Collection<Product> getRequest(String request) throws JSONException{

		Collection<Product> prod=new ArrayList<Product>();

		Gson gson=new Gson();
		Type listType = new TypeToken<List<Product>>(){}.getType();
		prod = (List<Product>) gson.fromJson(request, listType);

		return prod;
	}

	private Collection<ProductPicture> getVendorProductPictures(JSONObject value) throws JSONException {
		Collection<ProductPicture> productPicArr=new ArrayList<ProductPicture>();
		JSONObject jsonObject = value;

		if(!jsonObject.isNull("Picture")) {
			JSONObject json_data = jsonObject.getJSONObject("Picture");
			ProductPicture productPic = new ProductPicture();
			productPic.setId(Integer.parseInt(json_data.getString("Id")));
			productPic.setMediumBaseUrl(json_data.getString("BaseUrl"));
			productPicArr.add(productPic);
		}

		return productPicArr;
	}

	private Collection<Picture> getProductPictures(JSONObject value) throws JSONException {
		Collection<Picture> productPicArr=new ArrayList<Picture>();
		JSONObject jObject = value;

		JSONArray jArr = jObject.getJSONArray("Pictures");

		for(int i=0;i<jArr.length();i++) {
			JSONObject json_data = jArr.getJSONObject(i);
			Picture productPic = new Picture();
			productPic.setId(Integer.parseInt(json_data.getString("Id")));
			productPic.setBaseUrl(json_data.getString("MediumBaseUrl"));
			productPicArr.add(productPic);
		}

		return productPicArr;
	}
}
