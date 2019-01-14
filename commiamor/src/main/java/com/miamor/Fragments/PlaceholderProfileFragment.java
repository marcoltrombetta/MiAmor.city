package com.miamor.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.miamor.Obj.Globals;
import com.miamor.Obj.ProfileCustomer;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.miamor.Obj.Category;
import com.miamor.Runnables.ProfileRunnable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class PlaceholderProfileFragment extends ListFragment{
	private List<Category> listData=new ArrayList<Category>();
	ListView lv;
	Authentication auth;
	TextView userlocation;
	
	public PlaceholderProfileFragment() {}

	public static PlaceholderProfileFragment newInstance() {
		PlaceholderProfileFragment f = new PlaceholderProfileFragment();
		return f;
	}

	ProfileRunnable.ProfileInterface profileInterface=new ProfileRunnable.ProfileInterface() {
		
		@Override
		public void onStart() {
			
		}
		
		@Override
		public void Completed(final ProfileCustomer response) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					fillListViewData(response);
				}
			});
		}
	};

	private void fillListViewData(ProfileCustomer cust){

		 // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

		HashMap<String, String> hm = new HashMap<String,String>();
		hm.put("name", "Coupons");
		hm.put("value", ">");
		aList.add(hm);

		hm = new HashMap<String,String>();
		hm.put("name","Reviews");
		hm.put("value", ">");
		aList.add(hm);

		hm = new HashMap<String,String>();
		hm.put("name","Bookmarks");
		hm.put("value", ">");
		aList.add(hm);

		if(cust!=null) {
			hm = new HashMap<String, String>();
			hm.put("name", "Points Scored");
			hm.put("value", Double.toString(cust.getPointsScored()));
			aList.add(hm);

			userlocation.setText(cust.getFirstName()+" "+ cust.getLastName());

		}else{
			hm = new HashMap<String, String>();
			hm.put("name", "Points Scored");
			hm.put("value", "0");
			aList.add(hm);
		}

        // Keys used in Hashmap
        String[] from = { "name","value" };
 
        // Ids of views in listview_layout
        int[] to = { R.id.Itemdescription, R.id.itemicon};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item, from, to);

        setListAdapter(adapter);

		adapter.notifyDataSetChanged();

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
		 listData.clear();

		auth=new Authentication(getActivity().getApplicationContext());

		Button messages=(Button)rootView.findViewById(R.id.messages);
		Button settings=(Button)rootView.findViewById(R.id.settings);

		userlocation=(TextView)rootView.findViewById(R.id.userLocation);

		settings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (auth.isLogged()) {
					PlaceholderCustomerSettingFragment f2 = new PlaceholderCustomerSettingFragment();
					Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"customersettings");
				}else{
					Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getText(R.string.loginfirst),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		messages.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (auth.isLogged()) {
					PlaceholderCustomerMessageFragment f2 = new PlaceholderCustomerMessageFragment();
					Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "customermessages");
				}else{
					Toast.makeText(getActivity().getBaseContext(),getActivity().getResources().getText(R.string.loginfirst),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		startSync();
		return rootView;
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
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		getActivity().getActionBar().setHomeButtonEnabled(true);
		super.onCreate(savedInstanceState);
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
	   	 // Getting listview from xml
	        lv = getListView();
	        super.onViewCreated(view, savedInstanceState);
	    }

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if(auth.isLogged()){
			switch (position){
				case 0:
					PlaceholderCustomerCouponFragment f2 = new PlaceholderCustomerCouponFragment();
					Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"customercoupons");
					break;
				case 1:
					PlaceholderCustomerReviewFragment fr = new PlaceholderCustomerReviewFragment();
					Globals.changePagerFragment(getActivity(), fr,getFragmentManager(),"customerreviews");
					break;
				case 2:
					PlaceholderCustomerBookmarkFragment frb = new PlaceholderCustomerBookmarkFragment();
					Globals.changePagerFragment(getActivity(), frb,getFragmentManager(),"customerbookmarks");
					break;
			}
		}
	}

	private void startSync(){
		new Thread(new ProfileRunnable(auth.read().getCustId(),auth.read().getToke(),profileInterface)).start();
	}
}
