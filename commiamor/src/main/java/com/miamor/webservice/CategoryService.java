package com.miamor.webservice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miamor.Obj.Category;
import com.miamor.Obj.Globals;

public class CategoryService {
	private Post post;
	
	public CategoryService(){
		post=new Post();
	}
	
	public Collection<Category> getCategoryById(int CategoryId, int pageNum) throws JSONException{
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
	    nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
	    nameValuePairs.add(new BasicNameValuePair("categoryid", Integer.toString(CategoryId)));
	    nameValuePairs.add(new BasicNameValuePair("pageid", Integer.toString(pageNum)));

	    String json=post.doPost(Globals.ServerUrl+"/Vendorcategory/MApp_GetCategoriesByID",nameValuePairs);
	    
		return getRequest(json.toString());
	}
	
	private Collection<Category> getRequest(String request) throws JSONException{

		Collection<Category> cat=new ArrayList<Category>();

		Gson gson=new Gson();
		Type listType = new TypeToken<List<Category>>(){}.getType();
		cat = (List<Category>) gson.fromJson(request, listType);

		return cat;
	}
}
