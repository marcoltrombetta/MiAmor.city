package com.miamor.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.miamor.ListAdapter.CustomerMessageListAdapter;
import com.miamor.Obj.CustomerMessage;
import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.miamor.Runnables.CustomerMessageRunnable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlaceholderCustomerMessageFragment extends ListFragment implements OnScrollListener{
	private List<CustomerMessage> listData=new ArrayList<CustomerMessage>();
	ListView lv;
	private int currentPage=1;
	Authentication auth;

	public PlaceholderCustomerMessageFragment() {}
		
	 CustomerMessageRunnable.MessageInterface messageInterface=new CustomerMessageRunnable.MessageInterface() {
			
			@Override
			public void Completed(final Collection<CustomerMessage>  response) {
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

	private void fillListViewData(Collection<CustomerMessage>  response) {
		if (response != null){
			listData.addAll(response);
		}
		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		//SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item_image, from, to);
		CustomerMessageListAdapter adapter = new CustomerMessageListAdapter(getActivity(),getActivity().getBaseContext(),R.layout.list_item_message, listData);
		setListAdapter(adapter);

		adapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_custmessage, container, false);
		 listData.clear();
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {

	   	 // Getting listview from xml
	        lv = getListView();
	        
	        // Adding button to listview at footer
	        lv.setOnScrollListener(this);

	    	this.startSync();
	        
	        super.onViewCreated(view, savedInstanceState);
	   }

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id==android.R.id.home){
			Bundle b=new Bundle();
			b.putSerializable("values", new bundleParams(0,0,0,""));
			Globals.homeButtonHandler(getActivity());
		}else if (id == R.id.mnuLoginProfile) {
			PlaceholderAuthenticationFragment f2=new PlaceholderAuthenticationFragment();
			Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "authentication");
		}else if(id == R.id.mnuLogoutProfile){
			PlaceholderAuthenticationFragment f2=new PlaceholderAuthenticationFragment();
			Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"authentication");
			Authentication auth=new Authentication(getActivity().getApplicationContext());
			auth.clear();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();

		auth=new Authentication(getActivity().getApplicationContext());

		if(!auth.isLogged()){
			inflater.inflate(R.menu.profile, menu);
		}else{
			inflater.inflate(R.menu.profile_logout, menu);
		}
		//super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		
	}

	private void startSync(){
		Authentication auth=new Authentication(getActivity().getApplicationContext());
		String custId=auth.read().getCustId();
		String token=auth.read().getToke();
		new Thread(new CustomerMessageRunnable(custId,token, currentPage, messageInterface)).start();
	}
}
