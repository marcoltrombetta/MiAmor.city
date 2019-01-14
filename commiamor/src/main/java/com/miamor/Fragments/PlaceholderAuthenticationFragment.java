package com.miamor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miamor.R;

public class PlaceholderAuthenticationFragment extends Fragment{
	private FragmentTabHost mTabHost;

	public PlaceholderAuthenticationFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_authentication, container, false);

		mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

		addTabs();

		return rootView;
	}

	private void addTabs(){
		mTabHost.addTab(mTabHost.newTabSpec("login").setIndicator(getResources().getString(R.string.login)),
				PlaceholderLoginFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("register").setIndicator(getResources().getString(R.string.register)),
				PlaceholderRegisterFragment.class, null);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(false);
		super.onCreate(savedInstanceState);
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
	        super.onViewCreated(view, savedInstanceState);
	    }
}
