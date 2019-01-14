package com.miamor.webservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miamor.Obj.Blog;
import com.miamor.Obj.BlogPicture;
import com.miamor.Obj.BlogPostPicture;
import com.miamor.Obj.Globals;
import com.miamor.Obj.Vendor;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marcot on 8/20/2015.
 */

public class BlogService {
    private Get get;

    public BlogService(){
        get=new Get();
    }

    public Collection<Blog> getAllBlogs(int pageNumber) throws JSONException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
        nameValuePairs.add(new BasicNameValuePair("PageNumber", Integer.toString(pageNumber)));

        String json=get.doGet(Globals.ServerUrl+"/Blog/MApp_GetBlogListing",nameValuePairs);

        return getRequestAllBlogs(json.toString());
    }

    public Blog getBlogById(int Id) throws JSONException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("AppSecret", Globals.AppSecret));
        nameValuePairs.add(new BasicNameValuePair("BlogPostId", Integer.toString(Id)));
        String json=get.doGet(Globals.ServerUrl+"/Blog/MApp_GetBlogDetails",nameValuePairs);

        return getBlog(json.toString());
    }

    private Blog getBlog(String request) throws JSONException{

        JSONObject json_data = new JSONObject(request);

        Collection<BlogPicture> blogsPic=new ArrayList<BlogPicture>();

        Blog blog=new Blog();

        blog.setTitle(json_data.getString("Title"));
        blog.setBody(json_data.getString("Body"));

        JSONArray jArr = json_data.getJSONArray("Pictures");

        Gson gson=new Gson();
        Type listType = new TypeToken<List<BlogPicture>>(){}.getType();
        blogsPic = (List<BlogPicture>) gson.fromJson(jArr.toString(), listType);

       /* for(int i=0;i<jArr.length();i++){
            JSONObject json_datapic = jArr.getJSONObject(i);
            JSONObject Pic = json_datapic.getJSONObject("Picture");

            BlogPicture picture=new BlogPicture();
            picture.setUrl(Pic.getString("BaseUrl"));
            picture.setId(Pic.getInt("Id"));

            BlogPostPicture blogPic=new BlogPostPicture();
            blogPic.setPicture(picture);
            blogsPic.add(blogPic);
        }*/

        blog.setBlogPostPictures(blogsPic);
        return blog;
    }

    private Collection<Blog> getRequestAllBlogs(String request) throws JSONException{
        Collection<Blog> blogs=new ArrayList<Blog>();
        JSONObject jObject = new JSONObject(request);
        JSONArray jArr = jObject.getJSONArray("Blogs");

        for(int i=0;i<jArr.length();i++){
            JSONObject json_data = jArr.getJSONObject(i);
            Blog blog=new Blog();
            blog.setTitle(json_data.getString("Title"));
            blog.setShortDescription(json_data.getString("ShortDescription"));
            blog.setCommentCount(json_data.getInt("CommentCount"));
            blog.setBody(json_data.getString("Body"));
            blog.setTags(json_data.getString("Tags"));
            blog.setTotalLikes(json_data.getInt("TotalLikes"));

            BlogPicture picture=new BlogPicture();
            JSONObject json_datapic=json_data.getJSONObject("DefaultPic");
            picture.setMediumBaseUrl(json_datapic.getString("BaseUrl"));
            picture.setId(json_datapic.getInt("Id"));

            blog.setDefaultPic(picture);

            blogs.add(blog);
        }

        return blogs;
    }

}
