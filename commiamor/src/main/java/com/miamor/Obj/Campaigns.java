package com.miamor.Obj;

import java.util.Collection;
import java.util.Date;

public class Campaigns {
	private int Id;
	private int VendorId;
	private String Name;
	private String BackgroundImgPath;
	private String ShortDescription;
	private String FullDescription;
	private String StartDateTime;
	private String EndDateTime;
	private String ExpirationDateTime;
	private Collection<CampaignPictures> Pictures;
	private int CouponPrice;
	private int CouponDiscount;

	public Campaigns() {
		super();
	}

	public Campaigns(int id, int vendorId, String name, String backgroundImgPath, String shortDescription, String fullDescription, String startDateTime, String endDateTime, String expirationDateTime, Collection<CampaignPictures> campaignPictures, int couponPrice, int couponDiscount) {
		Id = id;
		VendorId = vendorId;
		Name = name;
		BackgroundImgPath = backgroundImgPath;
		ShortDescription = shortDescription;
		FullDescription = fullDescription;
		StartDateTime = startDateTime;
		EndDateTime = endDateTime;
		ExpirationDateTime = expirationDateTime;
		Pictures = campaignPictures;
		CouponPrice = couponPrice;
		CouponDiscount = couponDiscount;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getVendorId() {
		return VendorId;
	}

	public void setVendorId(int vendorId) {
		VendorId = vendorId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getBackgroundImgPath() {
		return BackgroundImgPath;
	}

	public void setBackgroundImgPath(String backgroundImgPath) {
		BackgroundImgPath = backgroundImgPath;
	}

	public String getShortDescription() {
		return ShortDescription;
	}

	public void setShortDescription(String shortDescription) {
		ShortDescription = shortDescription;
	}

	public String getFullDescription() {
		return FullDescription;
	}

	public void setFullDescription(String fullDescription) {
		FullDescription = fullDescription;
	}

	public String getStartDateTime() {
		return StartDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		StartDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return EndDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		EndDateTime = endDateTime;
	}

	public String getExpirationDateTime() {
		return ExpirationDateTime;
	}

	public void setExpirationDateTime(String expirationDateTime) {
		ExpirationDateTime = expirationDateTime;
	}

	public Collection<CampaignPictures> getPictures() {
		return Pictures;
	}

	public void setPictures(Collection<CampaignPictures> campaignPictures) {
		Pictures = campaignPictures;
	}

	public int getCouponPrice() {
		return CouponPrice;
	}

	public void setCouponPrice(int couponPrice) {
		CouponPrice = couponPrice;
	}

	public int getCouponDiscount() {
		return CouponDiscount;
	}

	public void setCouponDiscount(int couponDiscount) {
		CouponDiscount = couponDiscount;
	}
}
