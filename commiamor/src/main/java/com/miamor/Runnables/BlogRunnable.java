package com.miamor.Runnables;

import com.miamor.Obj.Blog;
import com.miamor.webservice.BlogService;

import org.json.JSONException;

import java.util.Collection;

public class BlogRunnable implements Runnable{
	public static interface BlogInterface{
		void onStart();
		void Completed(Collection<Blog> response);
	}

	BlogInterface blogInterface;
	private int pageNum;

	public BlogRunnable(int pageNum, BlogInterface blogInterface) {
		this.pageNum=pageNum;
		this.blogInterface=blogInterface;
	}
	
	@Override
	public void run() {
		try {
			blogInterface.onStart();
			BlogService co=new BlogService();
			Collection<Blog> response=co.getAllBlogs(pageNum);
			blogInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
