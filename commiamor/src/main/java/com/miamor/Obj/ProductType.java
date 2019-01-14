package com.miamor.Obj;

/**
 * Created by marco on 28/12/15.
 */
public class ProductType {

    private int Id;

    private String Name;


    private boolean Active ;


    private boolean Deleted;

    public ProductType(int id, String name, boolean active, boolean deleted) {
        Id = id;
        Name = name;
        Active = active;
        Deleted = deleted;
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

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public boolean isDeleted() {
        return Deleted;
    }

    public void setDeleted(boolean deleted) {
        Deleted = deleted;
    }
}
