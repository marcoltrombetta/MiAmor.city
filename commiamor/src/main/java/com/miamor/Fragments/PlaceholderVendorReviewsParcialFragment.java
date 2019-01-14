package com.miamor.Fragments;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.ListView;
import com.miamor.ListAdapter.VendorReviewListAdapter;
import com.miamor.Obj.VendorReview;
import com.miamor.Obj.bundleParams;
import com.miamor.R;
import com.miamor.Runnables.VendorReviewsRunnable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlaceholderVendorReviewsParcialFragment extends Fragment {

	private List<VendorReview> listData=new ArrayList<VendorReview>();
	private  int currentPage=1;
	Button btnLoadMore;
	ListView lv;
	ProgressDialog progress;
	bundleParams params;

	public PlaceholderVendorReviewsParcialFragment() {}

	VendorReviewsRunnable.VendorReviewsInterface vendorReviewsInterface= new VendorReviewsRunnable.VendorReviewsInterface(){

		@Override
		public void onStart() {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {

				}
			});
		}

		@Override
		public void Completed(final Collection<VendorReview> response) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
						fillListViewData(response);
				}
			});
		}
	};

	private void fillListViewData(Collection<VendorReview> data){
		listData.clear();
		listData.addAll(data);

		int pos=lv.getLastVisiblePosition();
		VendorReviewListAdapter adapter = new VendorReviewListAdapter(getActivity(),getActivity().getBaseContext(),R.layout.list_item_review, listData);
		lv.setAdapter(adapter);
		lv.setSelection(pos);
		adapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_vendorreviewpartial, container, false);
		listData.clear();

		Bundle args = getArguments();
		params=(bundleParams)args.getSerializable("values");

		progress = new ProgressDialog(getActivity());
		progress.setMessage(getResources().getString(R.string.loading));

		btnLoadMore = new Button(getActivity());
		// Creating a button - Load More
		btnLoadMore.setText(getResources().getString(R.string.loadMore));

		// Getting listview from xml
		lv = (ListView)rootView.findViewById(R.id.listVendorReviewPartial);
		// Adding button to listview at footer
		lv.addFooterView(btnLoadMore);

		btnLoadMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				currentPage++;
				startSync();
			}
		});

		this.startSync();
		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
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
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {

		   super.onViewCreated(view, savedInstanceState);
	    }

	private void startSync(){
		new Thread(new VendorReviewsRunnable(params.getVendorId(), currentPage, vendorReviewsInterface)).start();
	}
}
