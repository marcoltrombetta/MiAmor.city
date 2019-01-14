package com.miamor.Runnables;

import java.util.Collection;
import org.json.JSONException;
import com.miamor.Obj.Category;
import com.miamor.webservice.CategoryService;

public class CategoryRunnable implements Runnable{
	public static interface CategoryInterface{
		void onStart();
		void Completed(Collection<Category> response);
	}
	
	CategoryInterface categoryInterface;
	private int categoryId;
	private int pageNum;
		
	public CategoryRunnable(int CategoryId,int pageNum,CategoryInterface categoryInterface) {
		this.categoryId=CategoryId;
		this.pageNum=pageNum;
		this.categoryInterface=categoryInterface;
	}
	
	@Override
	public void run() {
		try {
			categoryInterface.onStart();
			CategoryService cat=new CategoryService();
			Collection<Category> response=cat.getCategoryById(categoryId, pageNum);
			categoryInterface.Completed(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
