package com.miamor.Obj;

public class BlogPicture {
	private int id;
	private boolean isActive;
	private String MediumBaseUrl;
	private String BigBaseUrl;

	public BlogPicture() {
		super();
	}
	public BlogPicture(int id, String url, boolean isActive) {
		super();
		this.id = id;
		this.isActive = isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
}
