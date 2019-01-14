package com.miamor.Runnables;

import java.util.Collection;
import org.json.JSONException;

import com.miamor.Obj.Campaigns;
import com.miamor.webservice.CampaignService;

public class CampaignRunnable implements Runnable{
	public static interface CampaignInterface{
		void onStart();
		void Completed(Collection<Campaigns> response);
	}
	
	CampaignInterface campaignInterface;
	private int vendorId;
	private int pageNum;
		
	public CampaignRunnable(int vendorId,int pageNum,CampaignInterface campaignInterface) {
		this.vendorId=vendorId;
		this.pageNum=pageNum;
		this.campaignInterface=campaignInterface;
	}
	
	@Override
	public void run() {
		try {
			campaignInterface.onStart();
			CampaignService co=new CampaignService();
			Collection<Campaigns> response=co.getCampaignByVendorId(vendorId, pageNum);
			campaignInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
