package com.miamor.Fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.miamor.ListAdapter.CouponListAdapter;
import com.miamor.Obj.Campaigns;
import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.miamor.R;
import com.miamor.Runnables.CampaignRunnable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class PlaceholderCouponFragment extends ListFragment implements OnScrollListener{
	private List<Campaigns> listData=new ArrayList<Campaigns>();
	ListView lv;
	private int currentPage=1;
	bundleParams params;

	public PlaceholderCouponFragment() {}
		
	 CampaignRunnable.CampaignInterface campaignInterface=new CampaignRunnable.CampaignInterface() {
			
			@Override
			public void Completed(final Collection<Campaigns>  response) {
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

	private void fillListViewData(Collection<Campaigns>  response) {
		if (response != null){
			listData.addAll(response);
		}
		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		//SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item_image, from, to);
		int pos=lv.getLastVisiblePosition();
		CouponListAdapter adapter = new CouponListAdapter(getActivity(),getActivity().getBaseContext(),R.layout.list_item_coupon, listData);
		setListAdapter(adapter);
		lv.setSelection(pos);
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
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
		   
		   Bundle args = getArguments();
		   params=(bundleParams)args.getSerializable("values");

	   	 // Getting listview from xml
	        lv = getListView();

		   // Adding button to listview at footer
		   lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				   Bundle b = new Bundle();
				   params.setCouponId(listData.get(position).getId());
				   b.putSerializable("values", params);

				   PlaceholderCouponDetailsFragment f2 = new PlaceholderCouponDetailsFragment();
				   f2.setArguments(b);

				   Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "vendorcoupondetails");
			   }
		   });

	        // Adding button to listview at footer
	        lv.setOnScrollListener(this);

	    	this.startSync();
	        
	        super.onViewCreated(view, savedInstanceState);
	   }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		if (scrollState == SCROLL_STATE_IDLE) {
			if (lv.getLastVisiblePosition() >= lv.getCount() - 1 ) {
				currentPage++;
				this.startSync();
			}
		}
	}

	private void startSync(){
		new Thread(new CampaignRunnable(params.getVendorId(), currentPage, campaignInterface)).start();
	}
}
