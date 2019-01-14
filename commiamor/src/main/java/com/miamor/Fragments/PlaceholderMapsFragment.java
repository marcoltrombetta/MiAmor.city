package com.miamor.Fragments;

import java.util.Collection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miamor.Location.CustomLocationListener;
import com.miamor.Obj.Addresses;
import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.miamor.R;
import com.miamor.Obj.Vendor;
import com.miamor.Runnables.MapRunnable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class PlaceholderMapsFragment extends Fragment{
	MapView mMapView;
    private GoogleMap googleMap;
	SupportMapFragment myMAPF;
	bundleParams params;

	 MapRunnable.VendorMapInterface vendorMapInterface=new MapRunnable.VendorMapInterface() {
			
			@Override
			public void Completed(final Collection<Vendor>  response) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						for (Vendor v : response) {
							// create marker
							if (v.getAddresses() != null){
								for (Addresses a : v.getAddresses()) {
									MarkerOptions marker = new MarkerOptions().position(
											new LatLng(a.getLatitude(), a.getLongitude())).title(v.getName()).snippet(Integer.toString(v.getId()));
									// adding marker
									googleMap.addMarker(marker);
								}
							}
						}
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


	public PlaceholderMapsFragment() {}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
		
		 Bundle args = getArguments();
		 params=(bundleParams)args.getSerializable("values");

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
		   startSync();

		   mMapView = (MapView) view.findViewById(R.id.mapView);
		   mMapView.onCreate(savedInstanceState);

		   try {
			   MapsInitializer.initialize(getActivity().getApplicationContext());
		   } catch (Exception e) {
			   e.printStackTrace();
		   }

		   googleMap = mMapView.getMap();
		   googleMap.setMyLocationEnabled(true);

		   Location location = Globals.getLocation(getActivity());

		   if(location!=null) {
			   googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),
			   googleMap.getMaxZoomLevel()-9));
		   }

		   googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			   @Override
			   public boolean onMarkerClick(Marker marker) {
				   try {
					   Bundle b = new Bundle();
					   b.putSerializable("values", params);

					   PlaceholderVendorDetailsFragment f2 = new PlaceholderVendorDetailsFragment();
					   f2.setArguments(b);

					   Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"vendordetails");
				   }catch(Exception ex){
					   Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
				   }
				   return true;
			   }
		   });

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
			Location location = Globals.getLocation(getActivity());
			
			if(location==null){
				new Thread(new MapRunnable(params.getCategoryId(), 1,params.getSearchFor(),0, 0,vendorMapInterface)).start();
			}else{
				new Thread(new MapRunnable(params.getCategoryId(), 1,params.getSearchFor(),location.getLatitude(),location.getLongitude(),vendorMapInterface)).start();
			}
		}
}
