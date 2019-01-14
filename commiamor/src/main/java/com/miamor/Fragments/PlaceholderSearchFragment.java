package com.miamor.Fragments;

import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.miamor.R;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class PlaceholderSearchFragment extends Fragment {
	private FragmentTabHost mTabHost;
	bundleParams params;

	public PlaceholderSearchFragment() {}

	public static PlaceholderSearchFragment newInstance() {
		PlaceholderSearchFragment f = new PlaceholderSearchFragment();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

		Bundle args = getArguments();
		Bundle b=new Bundle();

		if(args==null){
			b.putSerializable("values", new bundleParams(0, 0, 0, ""));
		}else{
			params=(bundleParams)args.getSerializable("values");
			b.putSerializable("values", params);
		}

		addTabs(b);

		return rootView;
	}
	
	private void addTabs(Bundle args){
		mTabHost.clearAllTabs();
        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator(getResources().getString(R.string.list)),
				PlaceholderVendorFragment.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator(getResources().getString(R.string.map)),
				PlaceholderMapsFragment.class, args);
	}

	@Override
	public void onPrepareOptionsMenu (Menu menu) {
		try {
			SearchView searchView = (SearchView) menu.findItem(R.id.searchc).getActionView();
			searchView.setQuery("", false);
		}catch(Exception ex){}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		//menu.clear();
		inflater.inflate(R.menu.search, menu);

	  // Associate searchable configuration with the SearchView
	  SearchManager searchManager =
	           (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
	    final SearchView searchView =
	            (SearchView) menu.findItem(R.id.searchc).getActionView();

	  	searchView.clearFocus();

	    searchView.setSearchableInfo(
				searchManager.getSearchableInfo(getActivity().getComponentName()));
		
	    searchView.setIconifiedByDefault(false);
	    
	    searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String arg0) {
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				Bundle b = new Bundle();
				params.setSearchFor(query);
				b.putSerializable("values", params);

				PlaceholderSearchFragment f2 = new PlaceholderSearchFragment();
				f2.setArguments(b);

				Globals.hideCurrentFragment(getActivity());
				Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "search");

				addTabs(b);


				return true;
			}
		});

		super.onCreateOptionsMenu(menu, inflater);
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
		getActivity().getActionBar().setHomeButtonEnabled(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
	        super.onViewCreated(view, savedInstanceState);

	}
}
