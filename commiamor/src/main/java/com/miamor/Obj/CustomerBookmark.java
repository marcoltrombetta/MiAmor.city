package com.miamor.Obj;

/**
 * Created by marco on 16/12/15.
 */
public class CustomerBookmark {
    private int CustomerId;
    private Vendor Vendor;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public com.miamor.Obj.Vendor getVendor() {
        return Vendor;
    }

    public void setVendor(com.miamor.Obj.Vendor vendor) {
        Vendor = vendor;
    }
}
