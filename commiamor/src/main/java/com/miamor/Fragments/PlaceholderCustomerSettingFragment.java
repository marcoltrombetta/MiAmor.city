package com.miamor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.miamor.Obj.Globals;
import com.miamor.Obj.ProfileCustomer;
import com.miamor.Obj.bundleParams;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.miamor.Runnables.CustomerSubmitSettingRunnable;
import com.miamor.Runnables.ProfileRunnable;

public class PlaceholderCustomerSettingFragment extends Fragment{
	Authentication auth;
	TextView firstname;
	TextView lastname;
	TextView email;
	TextView birthday;

	public PlaceholderCustomerSettingFragment() {}

	ProfileRunnable.ProfileInterface profileInterface=new ProfileRunnable.ProfileInterface() {

		@Override
		public void onStart() {

		}

		@Override
		public void Completed(final ProfileCustomer response) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					fillListViewData(response);
				}
			});
		}
	};

	CustomerSubmitSettingRunnable.CustomerSubmitSettingInterface submitInterface=new CustomerSubmitSettingRunnable.CustomerSubmitSettingInterface(){

		@Override
		public void Completed() {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getActivity(), "Settings Saved", Toast.LENGTH_SHORT).show();
				}
			});
		}
	};

	private void fillListViewData(ProfileCustomer cust){
		firstname.setText(cust.getFirstName());
		lastname.setText(cust.getLastName());
		email.setText(cust.getEmail());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profilesetting, container, false);

		Button save=(Button)rootView.findViewById(R.id.saveCustSetting);
		firstname=(TextView)rootView.findViewById(R.id.firstNameCust);
		lastname=(TextView)rootView.findViewById(R.id.lastNameCust);
		email=(TextView)rootView.findViewById(R.id.emailCust);
		birthday=(TextView)rootView.findViewById(R.id.dayofBirthCust);

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProfileCustomer prof = new ProfileCustomer();
				prof.setFirstName(firstname.getText().toString());
				prof.setLastName(lastname.getText().toString());
				prof.setEmail(email.getText().toString());

				startSyncSave(prof);

				PlaceholderProfileFragment f2 = new PlaceholderProfileFragment();
				Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "profile");
			}
		});

		startSync();
		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();

		auth=new Authentication(getActivity().getApplicationContext());

		if(!auth.isLogged()){
			inflater.inflate(R.menu.profile, menu);
		}else{
			inflater.inflate(R.menu.profile_logout, menu);
		}
		//super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id==android.R.id.home){
			Bundle b=new Bundle();
			b.putSerializable("values", new bundleParams(0,0,0,""));
			Globals.homeButtonHandler(getActivity());
		}else if (id == R.id.mnuLoginProfile) {
			PlaceholderAuthenticationFragment f2=new PlaceholderAuthenticationFragment();
			Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"authentication");
		}else if(id == R.id.mnuLogoutProfile){
			PlaceholderAuthenticationFragment f2=new PlaceholderAuthenticationFragment();
			Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"authentication");
			Authentication auth=new Authentication(getActivity().getApplicationContext());
			auth.clear();
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
		auth=new Authentication(getActivity().getApplicationContext());
		new Thread(new ProfileRunnable(auth.read().getCustId(),auth.read().getToke(),profileInterface)).start();
	}

	private void startSyncSave(ProfileCustomer prof){
			ProfileCustomer profileCustomer=new ProfileCustomer();
			profileCustomer.setFirstName(firstname.getText().toString());
			profileCustomer.setFirstName(lastname.getText().toString());
			profileCustomer.setFirstName(email.getText().toString());
			new Thread(new CustomerSubmitSettingRunnable(auth.read().getCustId(),auth.read().getToke(),profileCustomer,submitInterface)).start();
	}
}
