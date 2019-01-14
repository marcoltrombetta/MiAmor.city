package com.miamor.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miamor.ListAdapter.BlogListAdapter;
import com.miamor.Obj.Blog;
import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.miamor.Runnables.BlogRunnable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlaceholderBlogFragment extends ListFragment implements OnScrollListener{
	private List<Blog> listData=new ArrayList<Blog>();
	ListView lv;
	private int currentPage=1;

	private bundleParams params;

	public PlaceholderBlogFragment() {}

	public static PlaceholderBlogFragment newInstance() {
		PlaceholderBlogFragment f = new PlaceholderBlogFragment();
		return f;
	}

		BlogRunnable.BlogInterface blogInterface=new BlogRunnable.BlogInterface() {
			
			@Override
			public void Completed(final Collection<Blog>  response) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
							fillListViewData(response);
					  }
					});
			}
			
			@Override
			public void onStart() {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {		
			          
					}
				});
			}
		};

	private void fillListViewData(Collection<Blog>  response) {
		if (response != null){
			listData.addAll(response);
		}
		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		//SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item_image, from, to);
		BlogListAdapter adapter = new BlogListAdapter(getActivity(),getActivity().getBaseContext(),R.layout.list_item_blog, listData);
		setListAdapter(adapter);

		adapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_coupon, container, false);
		 listData.clear();
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().getActionBar().setHomeButtonEnabled(true);
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
		   Bundle args = getArguments();

		   if(args!=null) {
			   params = (bundleParams) args.getSerializable("values");
		   }else{
			   params=new bundleParams();
		   }

	   	 // Getting listview from xml
	        lv = getListView();
	        
	        // Adding button to listview at footer
	        lv.setOnScrollListener(this);

	    	this.startSync();

		   lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				   Bundle b = new Bundle();
				   params.setBlogId(listData.get(position).getId());
				   b.putSerializable("values", params);

				   PlaceholderBlogDetailsFragment f2 = new PlaceholderBlogDetailsFragment();
				   f2.setArguments(b);

				   Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "vendorblogdetails");
			   }
		   });

	        super.onViewCreated(view, savedInstanceState);
	   }

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		//super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		
	}

	private void startSync(){
		new Thread(new BlogRunnable(currentPage, blogInterface)).start();
	}
}
