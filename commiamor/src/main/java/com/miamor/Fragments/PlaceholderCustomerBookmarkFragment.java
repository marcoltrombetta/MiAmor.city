package com.miamor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miamor.ListAdapter.CustomerBookmarkListAdapter;
import com.miamor.Obj.CustomerBookmark;
import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.miamor.Runnables.CustomerBookmarksRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlaceholderCustomerBookmarkFragment extends Fragment implements OnScrollListener{
	private List<CustomerBookmark> listData=new ArrayList<CustomerBookmark>();
	ListView lv;
	private int currentPage=1;

	private bundleParams params;

	public PlaceholderCustomerBookmarkFragment() {}

		CustomerBookmarksRunnable.CustomerBookmarksInterface customerBookmarksInterface=new CustomerBookmarksRunnable.CustomerBookmarksInterface() {
			
			@Override
			public void Completed(final Collection<CustomerBookmark>  response) {
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

	private void fillListViewData(Collection<CustomerBookmark>  response) {
		if (response != null){
			listData.addAll(response);
		}
		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		//SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item_image, from, to);

		int pos=lv.getLastVisiblePosition();
		CustomerBookmarkListAdapter adapter = new CustomerBookmarkListAdapter(getActivity(),getActivity().getBaseContext(),R.layout.list_item_customerbookmark, listData);
		lv.setAdapter(adapter);
		lv.setSelection(pos);
		adapter.notifyDataSetChanged();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_customerbookmark, container, false);
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
	        lv = (ListView)view.findViewById(R.id.CustomerBookmarkList);
	        
	        // Adding button to listview at footer
	        lv.setOnScrollListener(this);

		 /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				   Bundle b = new Bundle();
				   params.setVendorId(listData.get(position).getVendor().getId());
				   b.putSerializable("values", params);

				   PlaceholderVendorDetailsFragment f2 = new PlaceholderVendorDetailsFragment();
				   f2.setArguments(b);
				   Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "vendordetails");
			   }
		   });*/

		   this.startSync();

		  super.onViewCreated(view, savedInstanceState);
	   }

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();

		Authentication auth=new Authentication(getActivity().getApplicationContext());

		if(!auth.isLogged()){
			inflater.inflate(R.menu.profile, menu);
		}else{
			inflater.inflate(R.menu.profile_logout, menu	);
		}
		//super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.mnuLoginProfile) {
			PlaceholderAuthenticationFragment f2=new PlaceholderAuthenticationFragment();
			Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"authentication");
		}else if(id == R.id.mnuLogoutProfile){
			PlaceholderAuthenticationFragment f2=new PlaceholderAuthenticationFragment();
			Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"authentication");
			Authentication auth=new Authentication(getActivity().getApplicationContext());
			auth.clear();
		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		
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
		Authentication auth=new Authentication(getActivity());
		new Thread(new CustomerBookmarksRunnable(auth.read().getCustId(),auth.read().getToke(),currentPage,customerBookmarksInterface)).start();
	}
}
