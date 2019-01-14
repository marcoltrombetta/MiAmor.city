package com.miamor.Obj;

import java.sql.Date;

public class ProfileCustomer extends Person{
	  private String MainAddress;
	  private double PointsScored;
		private VendorPictures Picture;

	public ProfileCustomer() {
		super();
	}

	public ProfileCustomer(String firstName, String lastName, String email, boolean gender,
			String mainAddress, int pointsScored) {
		super();

		MainAddress = mainAddress;
		PointsScored = pointsScored;
	}

	public String getMainAddress() {
		return MainAddress;
	}

	public void setMainAddress(String mainAddress) {
		MainAddress = mainAddress;
	}

	public double getPointsScored() {
		return PointsScored;
	}

	public void setPointsScored(double pointsScored) {
		PointsScored = pointsScored;
	}

	public VendorPictures getPicture() {
		return Picture;
	}

	public void setPicture(VendorPictures picture) {
		Picture = picture;
	}
}
