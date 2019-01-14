package com.miamor.Obj;

public class ProductPicture {
	private int Id;
	private String MediumBaseUrl;
	private String BigBaseUrl;
	private boolean isActive;

	public ProductPicture(int id, String mediumBaseUrl, String bigBaseUrl, boolean isActive) {
		Id = id;
		MediumBaseUrl = mediumBaseUrl;
		BigBaseUrl = bigBaseUrl;
		this.isActive = isActive;
	}

	public ProductPicture() {
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getMediumBaseUrl() {
		return MediumBaseUrl;
	}

	public void setMediumBaseUrl(String mediumBaseUrl) {
		MediumBaseUrl = mediumBaseUrl;
	}

	public String getBigBaseUrl() {
		return BigBaseUrl;
	}

	public void setBigBaseUrl(String bigBaseUrl) {
		BigBaseUrl = bigBaseUrl;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
