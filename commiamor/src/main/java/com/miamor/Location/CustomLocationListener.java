package com.miamor.Location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class CustomLocationListener implements LocationListener{
	public static interface LocationInterface{
		void onLocationChanged(Location location);
	}
	
	private LocationInterface locationInterface;
	
	public CustomLocationListener(LocationInterface locationInterface) {
		this.locationInterface=locationInterface;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		locationInterface.onLocationChanged(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	
}
