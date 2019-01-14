package com.miamor.Fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.miamor.ListAdapter.VendorListAdapter;
import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.miamor.R;
import com.miamor.Obj.Vendor;
import com.miamor.Runnables.VendorRunnable;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class PlaceholderVendorFragment extends Fragment implements OnScrollListener{
	private List<Vendor> listData=new ArrayList<Vendor>();
	private  int currentPage=1;
	bundleParams params;
	Button btnLoadMore;
	ListView lv;
	ProgressDialog progress;

	public PlaceholderVendorFragment() {}

    VendorRunnable.VendorInterface vendorInterface=new VendorRunnable.VendorInterface() {
		
		@Override
		public void Completed(final Collection<Vendor>  response) {
			if(getActivity()!=null) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						fillListViewData(response);
						//progress.dismiss();
					}
				});
			}
		}
		
		@Override
		public void onStart() {
			if(getActivity()!=null) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						//progress.show();
					}
				});
			}
		}
	};
   
	private void fillListViewData(Collection<Vendor> data){
		if (data != null){
		//	listData.clear();
			listData.addAll(data);
		}
		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		//SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item_image, from, to);

			int pos=lv.getLastVisiblePosition();
			VendorListAdapter adapter = new VendorListAdapter(getActivity(), getActivity().getBaseContext(), R.layout.list_item_vendor, listData);
			lv.setAdapter(adapter);
			lv.setSelection(pos);
			adapter.notifyDataSetChanged();
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_vendor, container, false);


		return rootView;
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
		setHasOptionsMenu(true);
		
		super.onCreate(savedInstanceState);
		
	}

	  @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
		  Bundle args = getArguments();
		  listData.clear();

		  if(args!=null){
			  params=(bundleParams)args.getSerializable("values");
			  if(params!=null) {
				  this.startSync();
			  }
		  }

		  progress = new ProgressDialog(getActivity());
	         progress.setMessage(getResources().getString(R.string.loading));
		  
			btnLoadMore = new Button(getActivity());
		  // Creating a button - Load More
	        btnLoadMore.setText(getResources().getString(R.string.loadMore));
	         
	   	 // Getting listview from xml
	        lv =(ListView)view.findViewById(R.id.vendorList);

	        // Adding button to listview at footer
	        lv.setOnScrollListener(this);
	        
	        btnLoadMore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					currentPage++;
					startSync();
				}
			});

		  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
					  Bundle b=new Bundle();
					  params.setVendorId(listData.get(position).getId());
					  b.putSerializable("values", params);

					  PlaceholderVendorDetailsFragment f2=new PlaceholderVendorDetailsFragment();
					  f2.setArguments(b);
					  Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"vendordetails");
			  }
		  });
		  
	        super.onViewCreated(view, savedInstanceState);
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
		Location location = Globals.getLocation(getActivity());

		if(location==null){
			new Thread(new VendorRunnable(params.getCategoryId(), currentPage,params.getSearchFor(),0, 0,vendorInterface)).start();
		}else{
			new Thread(new VendorRunnable(params.getCategoryId(), currentPage,params.getSearchFor(),location.getLatitude(),location.getLongitude(),vendorInterface)).start();
		}
	}
}