package com.miamor.Obj;

import java.sql.Date;
import java.util.Collection;

public class CampaignsList {
	private int Id;
	private Collection<Campaigns> Campaigns;

	public CampaignsList() {
		super();
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Collection<Campaigns> getCampaigns() {
		return Campaigns;
	}

	public void setCampaigns(Collection<Campaigns> campaigns) {
		Campaigns = campaigns;
	}
}
