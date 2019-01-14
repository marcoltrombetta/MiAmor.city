package com.miamor.Obj;

/**
 * Created by marco on 02/12/15.
 */

public class CampaignPictures {
    private int Id;
    private String MediumBaseUrl;
    private String BigBaseUrl;
    private boolean isActive;

    public CampaignPictures(int id, String mediumBaseUrl, String bigBaseUrl, boolean isActive) {
        Id = id;
        MediumBaseUrl = mediumBaseUrl;
        BigBaseUrl = bigBaseUrl;
        this.isActive = isActive;
    }

    public CampaignPictures() {
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

   /* private Picture Picture;

    public Picture getPicture() {
        return Picture;
    }

    public void setPicture(Picture picture) {
        Picture = picture;
    }*/
}
