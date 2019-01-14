package com.miamor.Runnables;

import com.miamor.Obj.Campaigns;
import com.miamor.webservice.CampaignService;
import org.json.JSONException;
import java.util.Collection;

public class CustomerCampaignRunnable implements Runnable{
	public static interface CampaignInterface{
		void onStart();
		void Completed(Collection<Campaigns> response);
	}

	CampaignInterface campaignInterface;
	private String custId;
	private int pageNum;
	private String token;

	public CustomerCampaignRunnable(String custId,String token, int pageNum, CampaignInterface campaignInterface) {
		this.custId=custId;
		this.token=token;
		this.pageNum=pageNum;
		this.campaignInterface=campaignInterface;
	}
	
	@Override
	public void run() {
		try {
			campaignInterface.onStart();
			CampaignService co=new CampaignService();
			Collection<Campaigns> response=co.getCampaignByCustId(custId,token,pageNum);
			campaignInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
