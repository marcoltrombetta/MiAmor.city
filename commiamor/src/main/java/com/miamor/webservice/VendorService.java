package com.miamor.webservice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miamor.Obj.Addresses;
import com.miamor.Obj.Globals;
import com.miamor.Obj.Picture;
import com.miamor.Obj.Product;
import com.miamor.Obj.ProfileCustomer;
import com.miamor.Obj.Vendor;
import com.miamor.Obj.VendorOpeningHours;
import com.miamor.Obj.VendorPictures;
import com.miamor.Obj.VendorReview;

public class VendorService {
	private Get get;
	private Post post;
	
	public VendorService(){
		get=new Get();
		post=new Post();
	}
	
	public Collection<Vendor> getAllVendorsByCategory(int categoryId, String searchFor, int pageNum , double lat, double lng) throws JSONException{
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
	    nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
	    nameValuePairs.add(new BasicNameValuePair("CategoryId", Integer.toString(categoryId)));
	    nameValuePairs.add(new BasicNameValuePair("SearchFor", searchFor));
	    nameValuePairs.add(new BasicNameValuePair("PageNumber", Integer.toString(pageNum)));
	    nameValuePairs.add(new BasicNameValuePair("Lat", Double.toString(lat)));
	    nameValuePairs.add(new BasicNameValuePair("Lgt", Double.toString(lng)));
	    
	    String json=get.doGet(Globals.ServerUrl+"/Vendor/MApp_GetVendorsByCategoryID",nameValuePairs);
	    
		return getRequestAllVendors(json.toString());
	}
	
	public Vendor getVendorsById(int vendorId, String custId) throws JSONException{
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
	    nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
	    nameValuePairs.add(new BasicNameValuePair("VendorId", Integer.toString(vendorId)));
		nameValuePairs.add(new BasicNameValuePair("CustId", custId));
	  	    
	  //  String json=get.doGet(Globals.ServerUrl+"/Vendor/MApp_GetVendorByID",nameValuePairs);
		String json=get.doGet(Globals.ServerUrl+"/Vendor/MApp_GetVendorDetails",nameValuePairs);
		return getRequestVendor(json.toString(), vendorId);
	}

	public VendorOpeningHours getVendorExtraDetails(int vendorId) throws JSONException{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
		nameValuePairs.add(new BasicNameValuePair("Id", Integer.toString(vendorId)));

		String json=get.doGet(Globals.ServerUrl+"/Vendor/MApp_GetVenorExtraDetails",nameValuePairs);

		return getVendorOpeningHours(json.toString());
	}

	public Collection<VendorReview> getVendorsReviews(int vendorId,int pageNum) throws JSONException{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
		nameValuePairs.add(new BasicNameValuePair("VendorId", Integer.toString(vendorId)));
		nameValuePairs.add(new BasicNameValuePair("PageNumber", Integer.toString(pageNum)));

		String json=get.doGet(Globals.ServerUrl + "/Vendor/MApp_GetVendorReviews", nameValuePairs);

		return getRequestVendorReviews(json.toString());
	}

	public void submitReview(String token,String CustId,int vendorId, String reviewText, double rating) throws JSONException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		nameValuePairs.add(new BasicNameValuePair("VendorId", Integer.toString(vendorId)));
		nameValuePairs.add(new BasicNameValuePair("CustId", CustId));
		nameValuePairs.add(new BasicNameValuePair("RatingStarNum", Double.toString(rating)));
		nameValuePairs.add(new BasicNameValuePair("ReviewText", reviewText));

		Header header=new BasicHeader("Authorization", "Bearer " + token);
		String json = post.doPost(Globals.ServerUrl + "/Vendor/MApp_SubmitVendorReview", nameValuePairs, header);
	}

	public VendorReview getVendorsReviewsById(int reviewId) throws JSONException{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
		nameValuePairs.add(new BasicNameValuePair("ReviewId", Integer.toString(reviewId)));

		String json=get.doGet(Globals.ServerUrl + "/Vendor/MApp_GetVendorReview", nameValuePairs);

		return getRequestVendorReview(json);
	}

	private Vendor getRequestVendor(String request,int vendorId) throws JSONException {
		Vendor ven=new Vendor();
		Gson gson=new Gson();
		ven = gson.fromJson(request, Vendor.class);
		ven.setId(vendorId);

		VendorOpeningHours op=getVendorExtraDetails(ven.getId());
		ven.setOpeningHours(op);

		return ven;
	}

	private VendorOpeningHours getVendorOpeningHours(String request) throws JSONException {
		JSONObject jsonObject = new JSONObject(request);
		VendorOpeningHours open=new VendorOpeningHours();

		if(!jsonObject.isNull("VendorOpeningHours")) {
			JSONObject json_data = jsonObject.getJSONObject("VendorOpeningHours");

			open.setMonday(json_data.getString("Monday"));
			open.setTuesday(json_data.getString("Tuesday"));
			open.setWednesday(json_data.getString("Wednesday"));
			open.setThursday(json_data.getString("Thursday"));
			open.setFriday(json_data.getString("Friday"));
			open.setSaturday(json_data.getString("Saturday"));
			open.setSunday(json_data.getString("Sunday"));
		}

		return open;
	}

	private Collection<VendorPictures> getVendorPictures(JSONObject value) throws JSONException {
		Collection<VendorPictures> vendorPicArr=new ArrayList<VendorPictures>();
		JSONObject jsonObject = value;
		JSONArray arrPictures = jsonObject.getJSONArray("VendorPictures");

		for(int i=0;i<arrPictures.length();i++)
		{
			JSONObject json_data1 = arrPictures.getJSONObject(i);
			JSONObject json_data = json_data1.getJSONObject("Picture");
			VendorPictures vendorPic=new VendorPictures();
			vendorPic.setId(Integer.parseInt(json_data.getString("Id")));
			vendorPic.setPicture(new Picture(json_data.getString("BaseUrl"),false));
			vendorPicArr.add(vendorPic);
		}

		return vendorPicArr;
	}

	private VendorPictures getVendorPicture(JSONObject value) throws JSONException {
		JSONObject jsonObject = value;

		JSONObject json_data = jsonObject.getJSONObject("Picture");
		VendorPictures vendorPic=new VendorPictures();
		Picture pic=new Picture();
		pic.setBaseUrl(json_data.getString("BaseUrl"));
		pic.setId(Integer.parseInt(json_data.getString("Id")));
		vendorPic.setPicture(pic);

		return vendorPic;
	}

	private Collection<VendorPictures> getVendorPictures_Category(JSONObject value) throws JSONException {
		Collection<VendorPictures> vendorPicArr=new ArrayList<VendorPictures>();
		JSONObject jsonObject = value;
		JSONArray arrPictures = jsonObject.getJSONArray("VendorPictures");

		for(int i=0;i<arrPictures.length();i++)
		{
			JSONObject json_data = arrPictures.getJSONObject(i);
			VendorPictures vendorPic=new VendorPictures();
			vendorPic.setId(Integer.parseInt(json_data.getString("Id")));
			Picture pic=new Picture();
			pic.setBaseUrl(json_data.getString("BaseUrl"));
			pic.setId(Integer.parseInt(json_data.getString("Id")));
			vendorPic.setPicture(pic);
			vendorPicArr.add(vendorPic);
		}

		return vendorPicArr;
	}

	private Collection<Vendor> getRequestAllVendors(String request) throws JSONException{

		Collection<Vendor> ven=new ArrayList<Vendor>();
		 
		JSONObject jsonObject = new JSONObject(request);
		JSONArray arr = jsonObject.getJSONArray("VendorBox");

		Gson gson=new Gson();
		Type listType = new TypeToken<List<Vendor>>(){}.getType();
		ven = (List<Vendor>) gson.fromJson(arr.toString(), listType);

		return ven;
	}
	
	private Collection<Addresses> getAddresses(JSONObject value) throws JSONException{
		Collection<Addresses> ven=new ArrayList<Addresses>();
		
		JSONObject jsonObject = value;
		JSONArray arr  = jsonObject.getJSONArray("Addresses");
		
		for(int i=0;i<arr.length();i++)
		{
			JSONObject json_data = arr.getJSONObject(i);
			  Addresses vendor=new Addresses();
	            vendor.setId(Integer.parseInt(json_data.getString("Id")));
				vendor.setPhoneNumber(json_data.getString("PhoneNumber"));
				vendor.setAddress1(json_data.getString("Address1"));

	            if(!json_data.equals("Latitude")){
	            	vendor.setLatitude(json_data.getDouble("Latitude"));
	            }
	            
	            if(!json_data.equals("Longitude")){
	            	vendor.setLongitude(json_data.getDouble("Longitude"));
	            }
	            
	    		ven.add(vendor);
		}

		return ven;
	}

	private Collection<VendorReview> getRequestVendorReviews(String request) throws JSONException {
		Collection<VendorReview> vr=new ArrayList<VendorReview>();

		JSONObject jsonObject = new JSONObject(request);
		//JSONObject jObj = jsonObject.getJSONObject("data");
		JSONArray jArr = jsonObject.getJSONArray("VendorReviewBox");

		Gson gson=new Gson();
		Type listType = new TypeToken<List<VendorReview>>(){}.getType();
		vr = (List<VendorReview>) gson.fromJson(jArr.toString(), listType);

		return vr;
	}

	private VendorReview getRequestVendorReview(String request) throws JSONException {
		VendorReview vr=new VendorReview();
		JSONObject jsonObject = new JSONObject(request);

		Gson gson=new Gson();
		vr = gson.fromJson(request, VendorReview.class);

		return vr;
	}

	public String bookmark(String token,String CustId,int vendorId) throws JSONException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("VendorId", Integer.toString(vendorId)));
		nameValuePairs.add(new BasicNameValuePair("CustId", CustId));

		Header header=new BasicHeader("Authorization", "Bearer " + token);
		String json = post.doPost(Globals.ServerUrl+"/Vendor/MApp_AddCustomerBookmark", nameValuePairs, header);

		return json;
	}
}
