package com.miamor.Fragments;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miamor.ImageSliderActivity;
import com.miamor.ListAdapter.MyImageListAdapter;
import com.miamor.ListAdapter.VendorReviewListAdapter;
import com.miamor.Obj.Addresses;
import com.miamor.Obj.Globals;
import com.miamor.Obj.VendorPictures;
import com.miamor.Obj.VendorReview;
import com.miamor.Obj.bundleParams;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.miamor.Obj.Vendor;
import com.miamor.Runnables.ImageUploadRunnable;
import com.miamor.Runnables.VendorBookmarkRunnable;
import com.miamor.Runnables.VendorDetailsRunnable;
import com.miamor.Runnables.VendorReviewsRunnable;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PlaceholderVendorDetailsFragment extends Fragment{
	ArrayList<String> aList = new ArrayList<String>();
	ArrayList<String> aListBig = new ArrayList<String>();
	static final int REQUEST_TAKE_PHOTO = 1;
	private FragmentTabHost mTabHost;
	MapView mMapView;
    private GoogleMap googleMap;
    TextView name;
    TextView distance;
    TextView address;
	TextView phone;
	TextView vendorOpen;
	RatingBar ratingBarVendorDetails;
	ListView lv;
	bundleParams params;
	Gallery gallery;
	String mCurrentPhotoPath;
	ListView lvreview;
	ProgressDialog progress;
	Button bookmark;
	Authentication auth;
	Button reviews;
	Button seeallImages;
	ScrollView sc;

	public PlaceholderVendorDetailsFragment() {}
	
	VendorDetailsRunnable.VendorInterface vendorDetailsInterface=new VendorDetailsRunnable.VendorInterface() {
		
		@Override
		public void onStart() {

		}
		
		@Override
		public void Completed(final Vendor response) {
			if(getActivity()!=null) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						loadDetails(response);
					}
				});
			}
		}
	};

	ImageUploadRunnable.ImageUploadInterface imageUploadInterface=new ImageUploadRunnable.ImageUploadInterface(){

		@Override
		public void onStart() {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					progress.show();
				}
			});
		}

		@Override
		public void onComplete() {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					progress.hide();
				}
			});
		}

		@Override
		public void onError(final Exception ex) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
				Toast.makeText(getActivity().getBaseContext(), ex.getMessage(),
						Toast.LENGTH_SHORT).show();
					progress.hide();
				}
			});
		}
	};

	VendorBookmarkRunnable.VendorBookmarkInterface bookmarkInterface=new VendorBookmarkRunnable.VendorBookmarkInterface(){

		@Override
		public void Completed(final String response) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					if(response.contains("Removed")){
						bookmark.setBackgroundResource(android.R.drawable.btn_default);
					}else{
						bookmark.setBackgroundColor(Color.GREEN);
					}
				}
			});
		}
	};

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
		final List<VendorReview> listData=new ArrayList<VendorReview>();

		List<VendorReview> vlist=new ArrayList<VendorReview>();
		vlist=(ArrayList)data;

		listData.clear();

		for(int i=0;i<vlist.size();i++){
			if(i<2) {
				listData.add(vlist.get(i));
			}else{break;}
		}

		if(data.size()==0){
			reviews.setVisibility(View.INVISIBLE);
		}else{
			reviews.setVisibility(View.VISIBLE);
		}

		int pos=lvreview.getLastVisiblePosition();
		VendorReviewListAdapter adapter = new VendorReviewListAdapter(getActivity(),getActivity().getBaseContext(),R.layout.list_item_review, listData);
		lvreview.setAdapter(adapter);
		lvreview.setSelection(pos);
		adapter.notifyDataSetChanged();

		lvreview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				Bundle b = new Bundle();
				params.setReviewId(listData.get(position).getId());
				b.putSerializable("values", params);

				PlaceholderVendorReviewDetailsFragment f2 = new PlaceholderVendorReviewDetailsFragment();
				f2.setArguments(b);
				Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "vendorreviewsdetails");
			}
		});

		sc.smoothScrollTo(0, 0);
	}

	private void loadDetails(Vendor vendor){

		name.setText(vendor.getName());

		vendorOpen.setText(vendor.getOpeningCurrentDate());

		if(vendor.isIsBookmarkedByCurrCustomer()){
			bookmark.setBackgroundColor(Color.GREEN);
		}

		ratingBarVendorDetails.setRating(vendor.getApprovedRatingSum());

		if(vendor.getAddresses()==null) {
			ArrayList<Addresses> address=new ArrayList<Addresses>();
			address.add(vendor.getAddress());
			vendor.setAddresses(address);
		}
			if (vendor.getAddresses().size() > 0) {
				ArrayList<Addresses> list = new ArrayList(vendor.getAddresses());
				Addresses ad = list.get(0);
				address.setText(ad.getAddress1());
				phone.setText(ad.getPhoneNumber());

				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ad.getLatitude(), ad.getLongitude()),
						googleMap.getMaxZoomLevel() - 5));

				Location location = Globals.getLocation(getActivity());
				if (location != null) {
					double dist = Globals.getDistance(ad.getLatitude(), ad.getLongitude(), location.getLatitude(), location.getLongitude());
					distance.setText(Double.toString(dist) + " m");
				} else {
					distance.setText("0.0 m");
				}
			}

			for (Addresses va : vendor.getAddresses()) {
				MarkerOptions marker = new MarkerOptions().position(
						new LatLng(va.getLatitude(), va.getLongitude())).title(vendor.getName()).snippet(Integer.toString(vendor.getId()));
				// adding marker
				googleMap.addMarker(marker);
			}

		fillGalleryData(vendor);
	}

	private void fillGalleryData(Vendor response){
		// Each row in the list stores country name, currency and flag

		if(response.getVendorPictures()==null || response.getVendorPictures().size()==0){
			seeallImages.setVisibility(View.INVISIBLE);
		}else{
			seeallImages.setVisibility(View.VISIBLE);
		}

		for(VendorPictures pic:response.getVendorPictures()){
			if(pic.getPicture().getPictureSizeTypeId()==2) {
				aListBig.add(pic.getPicture().getBaseUrl());
			}else{
				aList.add(pic.getPicture().getBaseUrl());
			}
		}
		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		//SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item_image, from, to);
		MyImageListAdapter adapter = new MyImageListAdapter(getActivity(),getActivity().getBaseContext(),R.layout.list_item_image, aList);
		gallery.setAdapter(adapter);

		adapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_vendordetails, container, false);

		auth=new Authentication(getActivity());

		try{
			progress = new ProgressDialog(getActivity());
			progress.setMessage(getResources().getString(R.string.uploading));

			ratingBarVendorDetails=(RatingBar)rootView.findViewById(R.id.ratingBarVendorDetails);
			vendorOpen=(TextView)rootView.findViewById(R.id.vendorOpen);
			name=(TextView)rootView.findViewById(R.id.vendorName);
			distance=(TextView)rootView.findViewById(R.id.vendorDistance);
			address=(TextView)rootView.findViewById(R.id.vendorAddress);
			phone=(TextView)rootView.findViewById(R.id.vendorPhone);
			Button takefoto=(Button)rootView.findViewById(R.id.takefoto);
			seeallImages=(Button)rootView.findViewById(R.id.vendorimageSeeAll);
			reviews=(Button)rootView.findViewById(R.id.vendorreviewsSeeAll);
			Button writereview=(Button)rootView.findViewById(R.id.vendorWriteReviews);
			gallery=(Gallery)rootView.findViewById(R.id.vendordetailsgallery);
			bookmark=(Button)rootView.findViewById(R.id.bookmark);
			//Button checkin=(Button)rootView.findViewById(R.id.vendorCheckin);
			lv=(ListView)rootView.findViewById(R.id.btnlist);

			sc=(ScrollView)rootView.findViewById(R.id.scrolviewVendorDetails);


			// Getting listview from xml
			lvreview = (ListView)rootView.findViewById(R.id.listVendorReviewPartial);

			Bundle args = getArguments();

			if(args!=null) {
				params = (bundleParams) args.getSerializable("values");
			}else{
				params=new bundleParams(0,0,0,"");
			}

			seeallImages.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					vendorImages();
				}
			});

			takefoto.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {

					if(auth.isLogged()) {
						dispatchTakePictureIntent();
					}else{
						Toast.makeText(getActivity().getBaseContext(),getActivity().getResources().getText(R.string.loginfirst),
								Toast.LENGTH_SHORT).show();
					}
				}

			});

			bookmark.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					if(auth.isLogged()) {
						bookmark();
					}else{
						Toast.makeText(getActivity().getBaseContext(),getActivity().getResources().getText(R.string.loginfirst),
								Toast.LENGTH_SHORT).show();
					}
				}
			});

			reviews.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					vendorReviews();
				}
			});

			writereview.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					if (auth.isLogged()) {
						vendorWriteReviews();
					} else {
						Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getText(R.string.loginfirst),
								Toast.LENGTH_SHORT).show();
					}
				}
			});

			gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
					ImageSliderActivity slider = new ImageSliderActivity();
					Intent intent = new Intent(getActivity(), slider.getClass());

					if (aListBig.size() > 0) {
						intent.putExtra("photos", aListBig);
					} else {
						intent.putExtra("photos", aList);
					}

					getActivity().startActivity(intent);
					//showBigPicture(aListBig.get(position));
				}
			});

			fillListViewData();

		}catch(Exception ex){
			Toast.makeText(getActivity().getBaseContext(), ex.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

		return rootView;
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mMapView.onLowMemory();
	}

	/*--Write Reviews*/

	private void vendorWriteReviews(){
		Bundle b=new Bundle();
		b.putSerializable("values", params);

		PlaceholderVendorDetailsWriteReviewFragment f2=new PlaceholderVendorDetailsWriteReviewFragment();
		f2.setArguments(b);
		Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"vendorwritereviews");
	}

	/*--Write Reviews*/


	/*--Reviews*/

	private void vendorReviews(){
		Bundle b=new Bundle();
		b.putSerializable("values", params);

		PlaceholderVendorReviewsFragment f2=new PlaceholderVendorReviewsFragment();
		f2.setArguments(b);
		Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"vendorreviews");
	}

	/*Reviews--*/

	/*Products*/
	private void vendorProducts(){
		Bundle b=new Bundle();
		b.putSerializable("values", params);

		PlaceholderProductFragment f2=new PlaceholderProductFragment();
		f2.setArguments(b);
		Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"vendorproducts");
	}
	/*Products*/

	/*Images*/
	private void vendorImages(){
		Bundle b=new Bundle();
		b.putSerializable("values", params);

		PlaceholderImagesFragment f2=new PlaceholderImagesFragment();
		f2.setArguments(b);
		Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"vendorimages");
	}
	/*Images*/

	/*Coupons*/
	private void coupons(){
		Bundle b=new Bundle();
		b.putSerializable("values", params);

		PlaceholderCouponFragment f2=new PlaceholderCouponFragment();
		f2.setArguments(b);
		Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"vendorcoupons");
	}
	/*Coupons*/

	/*directions*/
	private void directions(){
		Bundle b=new Bundle();
		b.putSerializable("values", params);

		PlaceholderMapDirectionsFragment f2=new PlaceholderMapDirectionsFragment();
		f2.setArguments(b);
		Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"mapdirections");
	}
	/*directions*/

	/*--Foto*/

	private File createImageFile() throws IOException {

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";

		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);

		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				ex.getStackTrace();
								// Error occurred while creating the File
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				Uri outFileUri = Uri.fromFile(photoFile);
				mCurrentPhotoPath=Uri.fromFile(photoFile).getPath();
				Bundle b=new Bundle();
				b.putParcelable(MediaStore.EXTRA_OUTPUT,outFileUri);
				takePictureIntent.putExtras(b);
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
			new Thread(new ImageUploadRunnable(getActivity(),imageUploadInterface,params.getVendorId(),mCurrentPhotoPath)).start();
		}
	}

	/*Foto--*/

	  @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id==android.R.id.home){	}

		return super.onOptionsItemSelected(item);
	}

	private void searchFragment(){
		Bundle b=new Bundle();
		b.putSerializable("values", params);

		PlaceholderSearchFragment f2=new PlaceholderSearchFragment();
		f2.setArguments(b);
		Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "search");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
	}

   @Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	   super.onViewCreated(view, savedInstanceState);

	   try{
		   startSync();

		   //Google Maps
		   mMapView = (MapView) view.findViewById(R.id.mapViewVendorDetails);
		   mMapView.onCreate(savedInstanceState);

		   try {
			   MapsInitializer.initialize(getActivity().getApplicationContext());
		   } catch (Exception e) {
			   e.printStackTrace();
		   }

		   googleMap = mMapView.getMap();

		   googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			   @Override
			   public boolean onMarkerClick(Marker marker) {
				   Bundle b = new Bundle();
				   b.putSerializable("values", params);
				   b.putSerializable("lat", marker.getPosition().latitude);
				   b.putSerializable("lng", marker.getPosition().longitude);

				   PlaceholderVendorDetailsMapFragment f2 = new PlaceholderVendorDetailsMapFragment();
				   f2.setArguments(b);
				   Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "vendordetailsmap");
				   return true;
			   }
		   });

		   this.startSyncReview();



	   }catch(Exception ex){
		   Toast.makeText(getActivity().getBaseContext(), ex.getMessage(),
				   Toast.LENGTH_SHORT).show();
	   }

   }

	private void startSync(){
		Authentication auth=new Authentication(getActivity());
		new Thread(new VendorDetailsRunnable(params.getVendorId(),auth.read().getCustId() , vendorDetailsInterface)).start();
	}

	private void fillListViewData(){

		// Each row in the list stores country name, currency and flag
		List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

		HashMap<String, String> hm = new HashMap<String,String>();
		hm.put("name", "Coupons");
		hm.put("value", ">");
		aList.add(hm);

		hm = new HashMap<String,String>();
		hm.put("name","Call");
		hm.put("value", ">");
		aList.add(hm);

		hm = new HashMap<String, String>();
		hm.put("name", "Products");
		hm.put("value", ">");
		aList.add(hm);

		// Keys used in Hashmap
		String[] from = { "name","value" };

		// Ids of views in listview_layout
		int[] to = { R.id.Itemdescription, R.id.itemicon};

		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item, from, to);

		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				switch (position) {
					case 0:
						coupons();
						break;
					case 1:
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone.getText()));
						startActivity(intent);
						break;
					case 2:
						vendorProducts();
						break;
				}
			}
		});

		adapter.notifyDataSetChanged();
	}

	public void bookmark(){
		Authentication auth=new Authentication(getActivity());
		if(auth.isLogged()){
			String custId=auth.read().getCustId();
			String token=auth.read().getToke();

			new Thread(new VendorBookmarkRunnable(token,custId,params.getVendorId(),bookmarkInterface)).start();
		}
	}

	private void startSyncReview(){
		new Thread(new VendorReviewsRunnable(params.getVendorId(), 1, vendorReviewsInterface)).start();
	}

	private void showBigPicture(String url){
		Globals.showBigPicture(url,getActivity());
	}

}
