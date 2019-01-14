package com.miamor.Runnables;

import com.miamor.Obj.Campaigns;
import com.miamor.webservice.CampaignService;

import org.json.JSONException;

public class CampaignDetailsRunnable implements Runnable{
	public static interface CampaingInterface{
		void onStart();
		void Completed(Campaigns response);
	}

	CampaingInterface campaingInterface;
	private int campaignId;
	private int productId;

	public CampaignDetailsRunnable(int campaignId, CampaingInterface campaingInterface) {
		this.campaignId=campaignId;
		this.campaingInterface=campaingInterface;
	}
	
	@Override
	public void run() {
		try {
			campaingInterface.onStart();
			CampaignService cat=new CampaignService();
			Campaigns response=cat.getCampaignById(campaignId);
			campaingInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
