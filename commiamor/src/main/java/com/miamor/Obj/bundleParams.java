package com.miamor.Obj;

import java.io.Serializable;

/**
 * Created by marco on 06/11/15.
 */
public class bundleParams implements Serializable {
    private int vendorId;
    private int categoryId;
    private int productId;
    private String searchFor;
    private int blogId;
    private int couponId;
    private int reviewId;

    public bundleParams(int vendorId, int categoryId, int productId, String searchFor) {
        this.vendorId = vendorId;
        this.categoryId = categoryId;
        this.productId = productId;
        this.searchFor = searchFor;
    }

    public bundleParams() {
        this.vendorId=0;
        this.categoryId=0;
        this.productId=0;
        this.searchFor="";
        this.blogId=0;
        this.couponId=0;
        this.reviewId=0;
    }


    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
}
