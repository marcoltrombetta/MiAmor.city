package com.miamor.Fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.miamor.R;
import com.miamor.Obj.Category;
import com.miamor.Runnables.CategoryRunnable;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

public class PlaceholderCategoryFragment extends ListFragment implements OnScrollListener{
	private List<Category> listData=new ArrayList<Category>();
	private  int currentPage=1;
	Button btnLoadMore;
	ListView lv;
	ProgressDialog progress;

	public PlaceholderCategoryFragment() {}
	
    CategoryRunnable.CategoryInterface categoryInterface=new CategoryRunnable.CategoryInterface() {
		
		@Override
		public void Completed(final Collection<Category>  response) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {		
					fillListViewData(response);
					progress.dismiss();
				  }
				});
		}
		
		@Override
		public void onStart() {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {		
		           progress.show();
				}
			});
		}
	};
   
	private void fillListViewData(Collection<Category> data){
		listData.addAll(data);
		
		 // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
 
        for(int i=0;i<listData.size();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("name", listData.get(i).getName());
            aList.add(hm);
        }
 
        // Keys used in Hashmap
        String[] from = { "name" };
 
        // Ids of views in listview_layout
        int[] to = { R.id.Itemdescription};
 
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item

		int pos=lv.getLastVisiblePosition();
		SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item, from, to);
		setListAdapter(adapter);
		lv.setSelection(pos);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_category, container, false);

		 listData.clear();
		return rootView;
	}
 	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.clear();
		inflater.inflate(R.menu.category, menu);
		
		// Associate searchable configuration with the SearchView
	   SearchManager searchManager =
	           (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView =
	            (SearchView) menu.findItem(R.id.search).getActionView();
	    searchView.setSearchableInfo(
	            searchManager.getSearchableInfo(getActivity().getComponentName()));
		
	    searchView.setIconifiedByDefault(false);

	    searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String query) {
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				Bundle b = new Bundle();
				b.putSerializable("values",new bundleParams(0,0,0,query));

				PlaceholderSearchFragment f2 = new PlaceholderSearchFragment();
				f2.setArguments(b);
				Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"search");

				return true;
			}
		});
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.mnuLogin) {

		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.setHasOptionsMenu(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.startSync();
	}

	  @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {

		  progress = new ProgressDialog(getActivity());
	         progress.setMessage(getResources().getString(R.string.loading));
		  
			btnLoadMore = new Button(getActivity());
		  // Creating a button - Load More
	        btnLoadMore.setText(getResources().getString(R.string.loadMore));
	         
	   	 // Getting listview from xml
	        lv = getListView();
	        
	        // Adding button to listview at footer
	        lv.addFooterView(btnLoadMore);    
	        lv.setOnScrollListener(this);
	        
	        btnLoadMore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					currentPage++;
					startSync();
				}
			});
		  
	        super.onViewCreated(view, savedInstanceState);
	    }
	  
	 @Override
	    public void onListItemClick(ListView l, View v, int position, long id) {
		 	super.onListItemClick(l, v, position, id);	   
		 
		 	Bundle b=new Bundle();
		 	b.putSerializable("values",new bundleParams(0,listData.get(position).getId(),0,""));

	 		PlaceholderSearchFragment f2=new PlaceholderSearchFragment();
	 		f2.setArguments(b);
		 	Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"search");
	    }

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		  if (scrollState == SCROLL_STATE_IDLE) {
         	 if (lv.getLastVisiblePosition() >= lv.getCount() - 1 ) {
                  currentPage++;  
                  this.startSync();
              }
         }
	}
  
	private void startSync(){
		new Thread(new CategoryRunnable(0, currentPage, categoryInterface)).start();
	}
}
