package com.miamor.Fragments;

import android.graphics.Color;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.miamor.Maps.GMapV2Direction;
import com.miamor.Maps.GetDirectionsAsyncTask;
import com.miamor.Obj.Addresses;
import com.miamor.Obj.Globals;
import com.miamor.Obj.Vendor;
import com.miamor.Obj.bundleParams;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.miamor.Runnables.VendorDetailsRunnable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaceholderMapDirectionsFragment extends Fragment{
	MapView mMapView;
    private GoogleMap googleMap;
	SupportMapFragment myMAPF;
	bundleParams params;

	public PlaceholderMapDirectionsFragment() {}

	GetDirectionsAsyncTask.MapDirections mapDirectionsInterface=new GetDirectionsAsyncTask.MapDirections(){
		@Override
		public void onComplete(final ArrayList directionPoints) {
			if(getActivity()!=null) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						PolylineOptions rectLine = new PolylineOptions();
						rectLine.width(5).color(Color.BLUE).visible(true).zIndex(30);
						rectLine.addAll(directionPoints);
						googleMap.addPolyline(rectLine);
					}
				});
			}
		}
	};

	VendorDetailsRunnable.VendorInterface vendorDetailsInterface=new VendorDetailsRunnable.VendorInterface() {

		@Override
		public void Completed(final Vendor  v) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					Addresses a = v.getAddresses().iterator().next();
					Location location = Globals.getLocation(getActivity());
					if (location != null) {
						findDirections(a.getLatitude(),
								a.getLongitude(),
								location.getLatitude(), location.getLongitude(), GMapV2Direction.MODE_DRIVING);
					} else {
						findDirections(a.getLatitude(),
								a.getLongitude(),
								-34.5465645, -54.645646, GMapV2Direction.MODE_DRIVING);
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_vendordetailsmaps, container, false);

		 Bundle args = getArguments();
		 params=(bundleParams)args.getSerializable("values");
			startSync();

		mMapView = (MapView) rootView.findViewById(R.id.mapView);
		mMapView.onCreate(savedInstanceState);
		
		 try {
		        MapsInitializer.initialize(getActivity().getApplicationContext());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		 googleMap = mMapView.getMap();
		 googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

		 googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			 @Override
			 public void onInfoWindowClick(Marker arg0) {

				 Bundle b = new Bundle();
				 b.putSerializable("values", params);

				 PlaceholderVendorDetailsFragment f2 = new PlaceholderVendorDetailsFragment();
				 f2.setArguments(b);

				 Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "vendordetailsmap");
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

		}else if(id==android.R.id.home){

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

	public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
		map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
		map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
		map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
		map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

		GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(mapDirectionsInterface);
		asyncTask.execute(map);
	}

	private void startSync(){
		Authentication auth=new Authentication(getActivity());
		new Thread(new VendorDetailsRunnable(params.getVendorId(),auth.read().getCustId(), vendorDetailsInterface)).start();
	}
}
