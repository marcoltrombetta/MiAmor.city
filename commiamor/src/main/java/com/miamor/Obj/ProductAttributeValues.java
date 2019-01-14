package com.miamor.Obj;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by marco on 28/12/15.
 */
public class ProductAttributeValues {

    private int Id;

    private String Name;

    private ArrayList<String> AttributeValues;

    public ProductAttributeValues(int id, String name, ArrayList<String> productAttribute) {
        Id = id;
        Name = name;
        AttributeValues = productAttribute;
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

    public ArrayList<String> getProductAttribute() {
        return AttributeValues;
    }

    public void setProductAttribute(ArrayList<String> productAttribute) {
        AttributeValues = productAttribute;
    }
}
