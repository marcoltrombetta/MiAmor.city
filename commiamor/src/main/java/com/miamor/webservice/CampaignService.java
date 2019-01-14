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
import com.miamor.Obj.Campaigns;
import com.miamor.Obj.CampaignsList;
import com.miamor.Obj.Globals;
import com.miamor.Obj.Picture;

public class CampaignService {
	private Get get;
	private Post post;
	
	public CampaignService(){
		get=new Get();
		post=new Post();
	}
	
	public Collection<Campaigns> getCampaignByVendorId(int VendorId, int pageNum) throws JSONException{
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
	    nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
	    nameValuePairs.add(new BasicNameValuePair("VendorId", Integer.toString(VendorId)));
	    nameValuePairs.add(new BasicNameValuePair("PageNumber", Integer.toString(pageNum)));

	    String json=get.doGet(Globals.ServerUrl+"/Vendor/MApp_GetNewVendorCampaigns",nameValuePairs);

		return getRequest(json.toString());
	}

	public Campaigns getCampaignById(int CampaignId) throws JSONException{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
		nameValuePairs.add(new BasicNameValuePair("CampaignId", Integer.toString(CampaignId)));

		String json=get.doGet(Globals.ServerUrl+"/Vendor/MApp_GetVendorCampaignById",nameValuePairs);

		return getRequestCampaign(json.toString());
	}

	public Collection<Campaigns> getCampaignByCustId(String CustId,String token, int pageNum) throws JSONException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("CustId", CustId));
		nameValuePairs.add(new BasicNameValuePair("PageNumber", Integer.toString(pageNum)));

		Header header=new BasicHeader("Authorization","Bearer "+token);
		String json=post.doPost(Globals.ServerUrl + "/Customer/MApp_GetCustomerCampaigns", nameValuePairs, header);

		return getRequestCustomerCampaigns(json.toString());
	}

	private Campaigns getRequestCampaign(String request) throws JSONException{

		Gson gson=new Gson();
		Campaigns campaigns = gson.fromJson(request, Campaigns.class);

		return campaigns;
	}

	private Collection<Campaigns> getRequest(String request) throws JSONException{

		CampaignsList cam=new CampaignsList();
		Gson gson=new Gson();
		cam = gson.fromJson(request, CampaignsList.class);

		return cam.getCampaigns();
	}

	private Collection<Picture> getCampaignPictures(JSONObject value) throws JSONException {
		Collection<Picture> vendorPicArr = new ArrayList<Picture>();
		JSONObject jsonObject = value;
		JSONArray arrPictures = jsonObject.getJSONArray("CampaignPictures");

		for (int i = 0; i < arrPictures.length(); i++) {
			JSONObject json_data1 = arrPictures.getJSONObject(i);
			if (!json_data1.isNull("Picture"))
			{
				JSONObject json_data = json_data1.getJSONObject("Picture");

				if(json_data.getInt("PictureSizeTypeId")==13) {
					Picture vendorPic = new Picture();
					vendorPic.setId(Integer.parseInt(json_data.getString("Id")));
					vendorPic.setBaseUrl(json_data.getString("BaseUrl"));
					vendorPic.setPictureSizeTypeId(json_data.getInt("PictureSizeTypeId"));
					vendorPicArr.add(vendorPic);
				}

			}
		}

		return vendorPicArr;
	}

	private Collection<Campaigns> getRequestCustomerCampaigns(String request) throws JSONException{

		Collection<Campaigns> cam=new ArrayList<Campaigns>();
		JSONObject jObject = new JSONObject(request);
		JSONArray jArr = jObject.getJSONArray("Coupons");

		for(int i=0;i<jArr.length();i++){
			JSONObject json_data = jArr.getJSONObject(i);
			Campaigns campaigns =new Campaigns();
			campaigns.setVendorId(Integer.parseInt(json_data.getString("VendorId")));
			campaigns.setName(json_data.getString("Name"));
			campaigns.setBackgroundImgPath(json_data.getString("BackgroundImgPath"));
			campaigns.setShortDescription(json_data.getString("ShortDescription"));
			cam.add(campaigns);
		}

		return cam;
	}
}
