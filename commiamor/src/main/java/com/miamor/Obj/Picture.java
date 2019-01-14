package com.miamor.Obj;

/**
 * Created by marco on 24/11/15.
 */
public class Picture {
    private int Id;
    private String BaseUrl;
    private boolean IsActive;
    private int PictureSizeTypeId;

    public Picture() {
        super();
    }
    public Picture(String url, boolean isActive) {
        super();
        this.BaseUrl = url;
        this.IsActive = isActive;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBaseUrl() {
        return BaseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        BaseUrl = baseUrl;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setIsActive(boolean isActive) {
        IsActive = isActive;
    }

    public int getPictureSizeTypeId() {
        return PictureSizeTypeId;
    }

    public void setPictureSizeTypeId(int PictureSizeTypeId) {
        PictureSizeTypeId = PictureSizeTypeId;
    }
}
