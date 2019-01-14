package com.miamor.Obj;

public class VendorPictures extends Picture{
	private int id;
	private Picture Picture;

	public VendorPictures(int id,Picture picture) {
		this.id = id;
		Picture = picture;
	}

	public VendorPictures() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Picture getPicture() {
		return Picture;
	}

	public void setPicture(Picture picture) {
		Picture = picture;
	}
}
