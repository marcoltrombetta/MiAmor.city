package com.miamor.webservice;

import com.miamor.Obj.Globals;
import com.miamor.Obj.ProfileCustomer;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ProfileService {
    private Post post;

    public ProfileService(){
        post=new Post();
    }

    public void submitSettings(String token,String CustId, ProfileCustomer profileCustomer) throws JSONException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("Email", profileCustomer.getEmail()));
        nameValuePairs.add(new BasicNameValuePair("FirstName", profileCustomer.getFirstName()));
        nameValuePairs.add(new BasicNameValuePair("LastName", profileCustomer.getLastName()));
        nameValuePairs.add(new BasicNameValuePair("DateOfBirth", ""));

        Header header=new BasicHeader("Authorization", "Bearer " + token);
        String json = post.doPost(Globals.ServerUrl+"/Vendor/MApp_SaveCustomerProfile", nameValuePairs, header);
    }
}
