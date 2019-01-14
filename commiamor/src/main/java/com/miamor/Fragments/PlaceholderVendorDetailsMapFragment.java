package com.miamor.Fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miamor.Obj.Addresses;
import com.miamor.Obj.Globals;
import com.miamor.Obj.Vendor;
import com.miamor.Obj.bundleParams;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.miamor.Runnables.VendorDetailsRunnable;

public class PlaceholderVendorDetailsMapFragment extends Fragment{
	MapView mMapView;
    private GoogleMap googleMap;
	SupportMapFragment myMAPF;
	bundleParams params;

	VendorDetailsRunnable.VendorInterface vendorDetailsInterface=new VendorDetailsRunnable.VendorInterface() {

			@Override
			public void Completed(final Vendor  v) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
							// create marker
							//for(Addresses a:v.getAddresses()){
								 MarkerOptions marker = new MarkerOptions().position(
										 new LatLng(v.getAddress().getLatitude(), v.getAddress().getLongitude())).title(v.getName()).snippet(Integer.toString(v.getId()));
									// adding marker
									googleMap.addMarker(marker);
							//}
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

	public PlaceholderVendorDetailsMapFragment() {}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_vendordetailsmaps, container, false);

		Bundle args = getArguments();
		Double lat=0.0;
		Double lng=0.0;

		if(args!=null) {
			params = (bundleParams) args.getSerializable("values");
			lat=(Double)args.getSerializable("lat");
			lng=(Double)args.getSerializable("lng");
		}else{
			params=new bundleParams(0,0,0,"");
		}

		startSync();

		mMapView = (MapView) rootView.findViewById(R.id.mapView);
		mMapView.onCreate(savedInstanceState);
		
		 try {
		        MapsInitializer.initialize(getActivity().getApplicationContext());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		 	googleMap = mMapView.getMap();
			Location location = Globals.getLocation(getActivity());
			googleMap.setMyLocationEnabled(true);

			//if(location!=null) {
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng),
			googleMap.getMaxZoomLevel()-9));
			//}

		 googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker arg0) {
				
				Bundle b=new Bundle();
				b.putSerializable("values",params);

		 		PlaceholderVendorDetailsFragment f2=new PlaceholderVendorDetailsFragment();
		 		f2.setArguments(b);

				Globals.changePagerFragment(getActivity(), f2,getFragmentManager(), "vendordetailsmap");
			}
		});
		 
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
	        super.onViewCreated(view, savedInstanceState);
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

	private void startSync(){
		Authentication auth=new Authentication(getActivity());
		new Thread(new VendorDetailsRunnable(params.getVendorId(), auth.read().getCustId(),vendorDetailsInterface)).start();
	}
}
