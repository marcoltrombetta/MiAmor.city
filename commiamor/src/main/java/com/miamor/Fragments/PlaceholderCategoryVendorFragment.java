package com.miamor.Fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.miamor.Obj.bundleParams;
import com.miamor.R;

public class PlaceholderCategoryVendorFragment extends Fragment {

	public PlaceholderCategoryVendorFragment() {}

	public static PlaceholderCategoryVendorFragment newInstance() {
		PlaceholderCategoryVendorFragment f = new PlaceholderCategoryVendorFragment();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_categoryvendor, container, false);

		PlaceholderCategoryFragment myf = new PlaceholderCategoryFragment();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.add(R.id.FragmentCategory, myf);
		transaction.commit();

		PlaceholderVendorFragment vendor = new PlaceholderVendorFragment();

		Bundle b=new Bundle();
		b.putSerializable("values", new bundleParams(0,0,0,""));

		vendor.setArguments(b);
		FragmentTransaction transactionV = getFragmentManager().beginTransaction();
		transactionV.add(R.id.FragmentVendor, vendor);
		transactionV.commit();

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.main, menu);	
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

		ActionBar ab = getActivity().getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayShowHomeEnabled(true);

		super.onCreate(savedInstanceState);
	}

   @Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

   }

}
