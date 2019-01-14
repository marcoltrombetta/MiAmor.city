package com.miamor.Obj;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Vendor implements Serializable{
	private int Id;
    private String Name;
    private String SiteUrl;
    private String ShortDescription;
    private String FullDescription;
    private String MetaKeywords;
    private String LogoImgPath;
    private boolean HasBranches;
    private int ApprovedRatingSum;
    private int NotApprovedRatingSum;
    private int ApprovedTotalReviews;
    private int NotApprovedTotalReviews;
    private int CouponNum;
    private boolean IsBookmarkedByCurrCustomer;
    private Collection<Addresses> Addresses;
	private Addresses Address;
    private Collection<VendorPictures> VendorPictures;
	private VendorOpeningHours openingHours;

	public Vendor() {
		super();
	}
	
	public Vendor(int id, String Name, String SiteUrl, String shortDescription, String fullDescription,
			String metaKeywords, String logoImgPath, boolean hasBranches, int approvedRatingSum,
			int notApprovedRatingSum, int approvedTotalReviews, int notApprovedTotalReviews, int couponNum,
			boolean isBookmarkedByCurrCustomer) {
		super();
		Id = id;
		this.Name = Name;
		this.SiteUrl = SiteUrl;
		ShortDescription = shortDescription;
		FullDescription = fullDescription;
		MetaKeywords = metaKeywords;
		LogoImgPath = logoImgPath;
		HasBranches = hasBranches;
		ApprovedRatingSum = approvedRatingSum;
		NotApprovedRatingSum = notApprovedRatingSum;
		ApprovedTotalReviews = approvedTotalReviews;
		NotApprovedTotalReviews = notApprovedTotalReviews;
		CouponNum = couponNum;
		IsBookmarkedByCurrCustomer = isBookmarkedByCurrCustomer;
	}

	public Collection<VendorPictures> getVendorPictures() {
		return VendorPictures;
	}

	public void setPicture(Collection<VendorPictures> pictures) {
		VendorPictures = pictures;
	}

	public Addresses getAddress() {
		return Address;
	}

	public void setAddress(Addresses address) {
		Address = address;
	}

	public Collection<Addresses> getAddresses() {
		return Addresses;
	}

	public void setAddresses(Collection<Addresses> addresses) {
		Addresses = addresses;
	}

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSiteUrl() {
		return SiteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		SiteUrl = siteUrl;
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
	public String getMetaKeywords() {
		return MetaKeywords;
	}
	public void setMetaKeywords(String metaKeywords) {
		MetaKeywords = metaKeywords;
	}
	public String getLogoImgPath() {
		return LogoImgPath;
	}
	public void setLogoImgPath(String logoImgPath) {
		LogoImgPath = logoImgPath;
	}
	public boolean isHasBranches() {
		return HasBranches;
	}
	public void setHasBranches(boolean hasBranches) {
		HasBranches = hasBranches;
	}
	public int getApprovedRatingSum() {
		return ApprovedRatingSum;
	}
	public void setApprovedRatingSum(int approvedRatingSum) {
		ApprovedRatingSum = approvedRatingSum;
	}
	public int getNotApprovedRatingSum() {
		return NotApprovedRatingSum;
	}
	public void setNotApprovedRatingSum(int notApprovedRatingSum) {
		NotApprovedRatingSum = notApprovedRatingSum;
	}
	public int getApprovedTotalReviews() {
		return ApprovedTotalReviews;
	}
	public void setApprovedTotalReviews(int approvedTotalReviews) {
		ApprovedTotalReviews = approvedTotalReviews;
	}
	public int getNotApprovedTotalReviews() {
		return NotApprovedTotalReviews;
	}
	public void setNotApprovedTotalReviews(int notApprovedTotalReviews) {
		NotApprovedTotalReviews = notApprovedTotalReviews;
	}
	public int getCouponNum() {
		return CouponNum;
	}
	public void setCouponNum(int couponNum) {
		CouponNum = couponNum;
	}
	public boolean isIsBookmarkedByCurrCustomer() {
		return IsBookmarkedByCurrCustomer;
	}
	public void setIsBookmarkedByCurrCustomer(boolean isBookmarkedByCurrCustomer) {
		IsBookmarkedByCurrCustomer = isBookmarkedByCurrCustomer;
	}

	public VendorOpeningHours getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(VendorOpeningHours openingHours) {
		this.openingHours = openingHours;
	}


	public boolean isBookmarkedByCurrCustomer() {
		return IsBookmarkedByCurrCustomer;
	}

	public String getOpeningCurrentDate(){

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		Date d = new Date();
		String dayOfTheWeek = sdf.format(d);
		String hours="";

		if(dayOfTheWeek.compareToIgnoreCase("monday")==0){
			hours=this.getOpeningHours().getMonday();
		}else if(dayOfTheWeek.compareToIgnoreCase("tuesday")==0){
			hours=this.getOpeningHours().getTuesday();
		}else if(dayOfTheWeek.compareToIgnoreCase("wednesday")==0){
			hours=this.getOpeningHours().getWednesday();
		}else if(dayOfTheWeek.compareToIgnoreCase("thursday")==0){
			hours=this.getOpeningHours().getThursday();
		}else if(dayOfTheWeek.compareToIgnoreCase("friday")==0){
			hours=this.getOpeningHours().getFriday();
		}else if(dayOfTheWeek.compareToIgnoreCase("saturday")==0){
			hours=this.getOpeningHours().getSaturday();
		}else if(dayOfTheWeek.compareToIgnoreCase("sunday")==0){
			hours=this.getOpeningHours().getSunday();
		}

		return hours;
	}
}
