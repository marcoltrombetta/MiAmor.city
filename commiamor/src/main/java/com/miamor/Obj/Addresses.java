package com.miamor.Obj;

public class Addresses {
	private int Id;
	private double Latitude;
	private double Longitude;
	private String PhoneNumber;
	private String Address1;

	public Addresses() {
		super();
	}

	public Addresses(int id, double latitude, double longitude, String phoneNumber, String address1) {
		Id = id;
		Latitude = latitude;
		Longitude = longitude;
		PhoneNumber = phoneNumber;
		Address1 = address1;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getAddress1() {
		return Address1;
	}

	public void setAddress1(String address1) {
		Address1 = address1;
	}
}
