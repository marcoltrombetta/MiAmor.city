package com.miamor.Obj;

/**
 * Created by marco on 28/12/15.
 */
public class ProductAttribute {

    private int Id;

    private int ProductTypeId;

    private String Name;

    private String Description ;

    private int ProductAttributeTypeId ;

    private boolean IsChooseEnable ;

    private ProductAttributeType ProductAttributeType;

    private ProductType ProductType;

    public ProductAttribute(int id, int productTypeId, String name, String description, int productAttributeTypeId, boolean isChooseEnable, com.miamor.Obj.ProductAttributeType productAttributeType, com.miamor.Obj.ProductType productType) {
        Id = id;
        ProductTypeId = productTypeId;
        Name = name;
        Description = description;
        ProductAttributeTypeId = productAttributeTypeId;
        IsChooseEnable = isChooseEnable;
        ProductAttributeType = productAttributeType;
        ProductType = productType;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProductTypeId() {
        return ProductTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        ProductTypeId = productTypeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getProductAttributeTypeId() {
        return ProductAttributeTypeId;
    }

    public void setProductAttributeTypeId(int productAttributeTypeId) {
        ProductAttributeTypeId = productAttributeTypeId;
    }

    public boolean isChooseEnable() {
        return IsChooseEnable;
    }

    public void setIsChooseEnable(boolean isChooseEnable) {
        IsChooseEnable = isChooseEnable;
    }

    public com.miamor.Obj.ProductAttributeType getProductAttributeType() {
        return ProductAttributeType;
    }

    public void setProductAttributeType(com.miamor.Obj.ProductAttributeType productAttributeType) {
        ProductAttributeType = productAttributeType;
    }

    public com.miamor.Obj.ProductType getProductType() {
        return ProductType;
    }

    public void setProductType(com.miamor.Obj.ProductType productType) {
        ProductType = productType;
    }
}
