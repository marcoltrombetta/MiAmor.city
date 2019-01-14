package com.miamor.Obj;

import java.util.Collection;

public class Product {
	private int Id;
    private String Name ;
    private String ShortDescription ;
    private String FullDescription;
    private float Price ;
    private Picture Picture;
	private Collection<ProductPicture> Pictures;
	private Collection<ProductAttributeValues> ProductPageAttributeValues;

	public Product() {
		super();
	}

	public Product(int id, String name, String shortDescription, String fullDescription, float price, Picture pictures) {
		this.Id = id;
		Name = name;
		ShortDescription = shortDescription;
		FullDescription = fullDescription;
		Price = price;
		this.Picture = pictures;
	}

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		this.Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
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
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}
	public Picture getPicture() {
		return Picture;
	}
	public void setPicture(Picture pictures) {
		this.Picture = pictures;
	}

	public Collection<ProductPicture> getPictures() {
		return Pictures;
	}

	public void setPictures(Collection<ProductPicture> pictures) {
		Pictures = pictures;
	}

	public Collection<com.miamor.Obj.ProductAttributeValues> getProductAttributeValues() {
		return ProductPageAttributeValues;
	}

	public void setProductAttributeValues(Collection<com.miamor.Obj.ProductAttributeValues> productAttributeValues) {
		ProductPageAttributeValues = productAttributeValues;
	}
}
