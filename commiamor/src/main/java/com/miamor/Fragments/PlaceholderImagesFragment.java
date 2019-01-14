package com.miamor.Fragments;

import com.miamor.ListAdapter.MyImageListAdapter;
import com.miamor.Obj.Globals;
import com.miamor.Obj.Vendor;
import com.miamor.Obj.VendorPictures;
import com.miamor.Obj.bundleParams;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.miamor.Runnables.LoadImageRunnable;
import com.miamor.Runnables.VendorDetailsRunnable;
import com.squareup.picasso.Picasso;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;

public class PlaceholderImagesFragment extends Fragment implements AbsListView.OnScrollListener{
	private List<VendorPictures> listData=new ArrayList<VendorPictures>();

	ListView lv;
	GridView gridview;
	boolean flag=true;
	bundleParams params;

	public PlaceholderImagesFragment() {}

	VendorDetailsRunnable.VendorInterface vendorDetailsInterface=new VendorDetailsRunnable.VendorInterface() {

		@Override
		public void onStart() {
		}

		@Override
		public void Completed(final Vendor response) {
			try{
				if (getActivity()!=null) {
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
						if (response != null) {
							fillListViewData(response);
						}
						}
					});
				}
			}catch (Exception ex){
				int i;
				i=0;
			}
		}
	};

	private void fillListViewData(Vendor response){
		// Each row in the list stores country name, currency and flag
		final List<String> aList = new ArrayList<String>();
		final List<String> aListBig = new ArrayList<String>();

		for(VendorPictures pic:response.getVendorPictures()){
			if(pic.getPicture().getPictureSizeTypeId()==2) {
				aListBig.add(pic.getPicture().getBaseUrl());
			}else {
				aList.add(pic.getPicture().getBaseUrl());
			}
		}

		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		//SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item_image, from, to);
		MyImageListAdapter adapter = new MyImageListAdapter(getActivity(),getActivity().getBaseContext(),R.layout.list_item_image, aList);
		gridview.setAdapter(adapter);

		// Adding button to listview at footer
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
								int position, long id) {
			showBigPicture(aListBig.get(position));
			}
		});

		adapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_images, container, false);
		listData.clear();

		Bundle args = getArguments();
		params = (bundleParams)args.getSerializable("values");

		this.startSyncVendor();
		return rootView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id==android.R.id.home){

		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
		   // Getting listview from xml
		   gridview = (GridView) view.findViewById(R.id.gridview);

	        super.onViewCreated(view, savedInstanceState);
	    }

	@Override
	public void onScrollStateChanged(AbsListView absListView, int i) {

	}

	@Override
	public void onScroll(AbsListView absListView, int i, int i1, int i2) {

	}

	private void startSyncVendor(){
		Authentication auth=new Authentication(getActivity());
		new Thread(new VendorDetailsRunnable(params.getVendorId(),auth.read().getCustId(), vendorDetailsInterface)).start();
	}

	private void showBigPicture(String url){
		Globals.showBigPicture(url,getActivity());
	}
}
