package com.miamor.Obj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Marcot on 8/13/2015.
 */
public class VendorReview {

    private int Id;
    private int VendorId;
    private String Title;
    private String ReviewText;
    private String CreatedOnUtc;
    private float Rating;
    private int ReviewsCount;
    private ProfileCustomer Customer;

    public VendorReview() {
    }

    public VendorReview(int id, int vendorId, String title, String reviewText, int rating, ProfileCustomer customer) {
        Id = id;
        VendorId = vendorId;
        Title = title;
        ReviewText = reviewText;
        Rating = rating;
        Customer = customer;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public ProfileCustomer getCustomer() {
        return Customer;
    }

    public void setCustomer(ProfileCustomer customer) {
        Customer = customer;
    }

    public int getReviewsCount() {
        return ReviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        ReviewsCount = reviewsCount;
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
