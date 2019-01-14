package com.miamor.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.miamor.ListAdapter.MyImageListAdapter;
import com.miamor.Obj.CampaignPictures;
import com.miamor.Obj.Campaigns;
import com.miamor.Obj.Globals;
import com.miamor.Obj.VendorReview;
import com.miamor.Obj.bundleParams;
import com.miamor.R;
import com.miamor.Runnables.CampaignDetailsRunnable;
import com.miamor.Runnables.LoadImageRunnable;
import com.miamor.Runnables.VendorReviewsDetailsRunnable;
import com.miamor.Runnables.VendorReviewsRunnable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlaceholderVendorReviewDetailsFragment extends Fragment{

	TextView name;
	TextView desc;
	TextView date;
	RatingBar ratingbar;
	List<String> aListBig = new ArrayList<String>();
	TextView countrev;
	bundleParams params;
	ImageView img;
	//gallery object
	private Gallery picGallery;

	VendorReviewsDetailsRunnable.VendorReviewsDetailsInterface vendorReviewsInterface= new VendorReviewsDetailsRunnable.VendorReviewsDetailsInterface(){

		@Override
		public void onStart() {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {

				}
			});
		}

		@Override
		public void Completed(final VendorReview response) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					loadDetails(response);
				}
			});
		}
	};

	public PlaceholderVendorReviewDetailsFragment() {}

	public void loadDetails(VendorReview cam){
		name.setText(cam.getCustomer().getFirstName());
		desc.setText(cam.getReviewText());
		ratingbar.setRating(cam.getRating());
		countrev.setText("Reviews: "+Integer.toString(cam.getReviewsCount()));
		date.setText(cam.getCreatedOnUtc().toString());
		Picasso.with(getActivity()).load(cam.getCustomer().getPicture().getBaseUrl()).into(img);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_reviewdetails, container, false);

		name=(TextView)rootView.findViewById(R.id.CustomerNameReviewDetails);
		desc=(TextView)rootView.findViewById(R.id.ItemdescriptionReviewDetails);
		ratingbar=(RatingBar)rootView.findViewById(R.id.ratingBarReviewDetails);
		img=(ImageView)rootView.findViewById(R.id.imageViewReviewDetails);
		countrev=(TextView)rootView.findViewById(R.id.ReviewCountDetails);
		date=(TextView)rootView.findViewById(R.id.CustomerDateReviewDetails);

		Bundle args = getArguments();
		params=(bundleParams)args.getSerializable("values");

		startSync();
		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		super.onCreateOptionsMenu(menu, inflater);
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
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

   @Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	private void startSync(){
		new Thread(new VendorReviewsDetailsRunnable(params.getReviewId(), vendorReviewsInterface)).start();
	}
}
