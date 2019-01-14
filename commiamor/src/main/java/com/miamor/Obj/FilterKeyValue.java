package com.miamor.Obj;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by marco on 02/11/15.
 */

public class FilterKeyValue implements Serializable {
    private String myKey;
    private String myValue ;

    public FilterKeyValue(String myKey, String myValue) {
        this.myKey = myKey;
        this.myValue = myValue;
    }

    public FilterKeyValue() {}

    public String getMyKey() {
        return myKey;
    }

    public void setMyKey(String myKey) {
        this.myKey = myKey;
    }

    public String getMyValue() {
        return myValue;
    }

    public void setMyValue(String myValue) {
        this.myValue = myValue;
    }
}
