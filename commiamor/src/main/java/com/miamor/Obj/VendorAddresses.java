package com.miamor.Obj;

public class VendorAddresses {
	private int id;
	private double latitude;
	private double longitude;
	private String phoneNumber;
	private String address1;

	public VendorAddresses() {
		super();
	}
	public VendorAddresses(int id, double latitude, double longitude) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String pohneNumber) {
		this.phoneNumber = pohneNumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address) {
		this.address1 = address;
	}
}
