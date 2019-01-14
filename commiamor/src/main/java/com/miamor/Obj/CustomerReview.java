package com.miamor.Obj;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Marcot on 8/14/2015.
 */
public class CustomerReview  implements Serializable {

    private int VendorId ;

    private String Title ;

    private String ReviewText ;

    private float Rating ;

    private int HelpfulYesTotal ;

    private int HelpfulNoTotal ;
    private String CreatedOnUtc;

    private Vendor Vendor;

    public CustomerReview() {
    }

    public CustomerReview(int vendorId, String title, String reviewText, int rating, int helpfulYesTotal, int helpfulNoTotal) {
        VendorId = vendorId;
        Title = title;
        ReviewText = reviewText;
        Rating = rating;
        HelpfulYesTotal = helpfulYesTotal;
        HelpfulNoTotal = helpfulNoTotal;

    }

    public int getVendorId() {
        return VendorId;
    }

    public void setVendorId(int vendorId) {
        VendorId = vendorId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getReviewText() {
        return ReviewText;
    }

    public void setReviewText(String reviewText) {
        ReviewText = reviewText;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public int getHelpfulYesTotal() {
        return HelpfulYesTotal;
    }

    public void setHelpfulYesTotal(int helpfulYesTotal) {
        HelpfulYesTotal = helpfulYesTotal;
    }

    public int getHelpfulNoTotal() {
        return HelpfulNoTotal;
    }

    public void setHelpfulNoTotal(int helpfulNoTotal) {
        HelpfulNoTotal = helpfulNoTotal;
    }


    public com.miamor.Obj.Vendor getVendor() {
        return Vendor;
    }

    public void setVendor(com.miamor.Obj.Vendor vendor) {
        Vendor = vendor;
    }

    public Date getCreatedOnUtc() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date myDate = new Date();

        try {
            myDate = simpleDateFormat.parse(CreatedOnUtc.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return myDate;
    }

    public void setCreatedOnUtc(String createdOnUtc) {
        CreatedOnUtc = createdOnUtc;
    }

}
