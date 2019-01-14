package com.miamor.Runnables;

import com.miamor.Obj.Blog;
import com.miamor.webservice.BlogService;

import org.json.JSONException;

public class BlogDetailsRunnable implements Runnable{
	public static interface BlogInterface{
		void onStart();
		void Completed(Blog response);
	}

	BlogInterface blogInterface;
	private int blogId;
	private int productId;

	public BlogDetailsRunnable(int blogId, BlogInterface productInterface) {
		this.blogId=blogId;
		this.blogInterface=productInterface;
	}
	
	@Override
	public void run() {
		try {
			blogInterface.onStart();
			BlogService cat=new BlogService();
			Blog response=cat.getBlogById(blogId);
			blogInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
