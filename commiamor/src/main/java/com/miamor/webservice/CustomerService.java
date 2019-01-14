package com.miamor.webservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miamor.Obj.CustomerBookmark;
import com.miamor.Obj.CustomerMessage;
import com.miamor.Obj.CustomerReview;
import com.miamor.Obj.Globals;
import com.miamor.Obj.Product;
import com.miamor.Obj.ProfileCustomer;
import com.miamor.Obj.Vendor;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marcot on 8/14/2015.
 */

public class CustomerService {

    private Post post;
    private Get get;

    public CustomerService(){
        post=new Post();
        get=new Get();
    }

    public Collection<CustomerMessage> getCustomerMessages(String CustId,String token, int pageNum) throws JSONException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("CustId", CustId));
        nameValuePairs.add(new BasicNameValuePair("PageNumber", Integer.toString(pageNum)));

        Header header=new BasicHeader("Authorization", "Bearer " + token);
        String json = post.doPost(Globals.ServerUrl+"/Customer/MApp_GetCustomerMessages", nameValuePairs, header);

        return getRequest(json.toString());
    }

    public Collection<CustomerReview> getCustomerReviews(String CustId,String token, int pageNum) throws JSONException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("CustId", CustId));
        nameValuePairs.add(new BasicNameValuePair("PageNumber", Integer.toString(pageNum)));

        Header header=new BasicHeader("Authorization","Bearer "+token);
        String json=post.doPost(Globals.ServerUrl+"/Customer/MApp_GetCustomerReviews", nameValuePairs, header);

        return getRequestReviews(json.toString());
    }

    public ProfileCustomer getCustomerDetails(String CustId,String token) throws JSONException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("CustId", CustId));

        Header header=new BasicHeader("Authorization","Bearer "+token);
        String json=post.doPost(Globals.ServerUrl+"/Customer/GetCustomerDetailsApp", nameValuePairs, header);

        return getRequestDetails(json.toString());
    }

    public Collection<CustomerBookmark> getCustomerBookmarks(String custId,int pageNum,String token) throws JSONException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("CustId", custId));
        nameValuePairs.add(new BasicNameValuePair("PageNumber",  Integer.toString(pageNum)));

        Header header=new BasicHeader("Authorization","Bearer "+token);
        String json=get.doGet(Globals.ServerUrl + "/Customer/MApp_GetCustomerBookmarks", nameValuePairs, header);

        return getRequestBookmarks(json.toString());
    }

    public void setCustomerDetails(ProfileCustomer prof,String custId,String token) throws JSONException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("CustId", custId));
        nameValuePairs.add(new BasicNameValuePair("FirstName", prof.getFirstName()));
        nameValuePairs.add(new BasicNameValuePair("LastName", prof.getLastName()));
        nameValuePairs.add(new BasicNameValuePair("Email", prof.getEmail()));

        Header header=new BasicHeader("Authorization","Bearer "+token);
        String json=post.doPost(Globals.ServerUrl+"/Customer/MApp_SaveCustomerProfile", nameValuePairs, header);
    }

    private Collection<CustomerBookmark> getRequestBookmarks(String request) throws JSONException{
        JSONObject jsonObject = new JSONObject(request);
        JSONArray jArr = jsonObject.getJSONArray("CustomerBookmark");

        Collection<CustomerBookmark> bookmarks=new ArrayList<CustomerBookmark>();

        Gson gson=new Gson();
        Type listType = new TypeToken<List<CustomerBookmark>>(){}.getType();
        bookmarks = (List<CustomerBookmark>) gson.fromJson(jArr.toString(), listType);

        return bookmarks;
    }

    private ProfileCustomer getRequestDetails(String request) throws JSONException{

        if(request=="" || request==null){
            return null;
        }

        JSONObject jObject = new JSONObject(request);
        JSONObject json_data = jObject.getJSONObject("data");

        ProfileCustomer cust=new ProfileCustomer();
        cust.setFirstName(json_data.getString("FirstName"));
        cust.setLastName(json_data.getString("LastName"));
        cust.setEmail(json_data.getString("Email"));
        cust.setMainAddress(json_data.getString("MainAddress"));
        cust.setPointsScored(json_data.getInt("PointsScored"));

        return cust;
    }

    private Collection<CustomerReview> getRequestReviews(String request) throws JSONException{

        Collection<CustomerReview> car=new ArrayList<CustomerReview>();
        JSONObject jObject = new JSONObject(request);
        JSONArray jArr = jObject.getJSONArray("VendorReviewBox");

        Gson gson=new Gson();
        Type listType = new TypeToken<List<CustomerReview>>(){}.getType();
        car = (List<CustomerReview>) gson.fromJson(jArr.toString(), listType);

        return car;
    }

    private Collection<CustomerMessage> getRequest(String request) throws JSONException{

        Collection<CustomerMessage> cam=new ArrayList<CustomerMessage>();
        JSONObject jObject = new JSONObject(request);
        JSONArray jArr = jObject.getJSONArray("CustomerMessageModel");

        for(int i=0;i<jArr.length();i++){
            JSONObject json_data = jArr.getJSONObject(i);
            CustomerMessage custMes=new CustomerMessage();
            custMes.setVendorId(Integer.parseInt(json_data.getString("VendorId")));
            custMes.setTitle(json_data.getString("Title"));
            custMes.setMsgTxt(json_data.getString("MsgTxt"));

            cam.add(custMes);
        }

        return cam;
    }
}
