package com.miamor.Fragments;

import com.miamor.Obj.Globals;
import com.miamor.R;
import com.miamor.Obj.AuthenticationRequest;
import com.miamor.Obj.LoginCustomer;
import com.miamor.Preferences.Authentication;
import com.miamor.Runnables.LoginRunnable;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlaceholderLoginFragment extends Fragment{
	EditText email;
	EditText password;
	Context ctx;
	
	public PlaceholderLoginFragment() {}

	LoginRunnable.LoginInterface loginInterface = new LoginRunnable.LoginInterface(){

		@Override
		public void LoginCompleted(final AuthenticationRequest response) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {

					if (!response.isNull()) {
						Authentication auth = new Authentication(ctx);
						auth.save(response);

						PlaceholderProfileFragment f2 = new PlaceholderProfileFragment();
						Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "profile");
					} else {
						Toast.makeText(getActivity(), "Login Fail", Toast.LENGTH_SHORT).show();
				}

				}
			});
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(false);
		super.onCreate(savedInstanceState);
	}

	public void Login(){
		LoginCustomer loginCustomer=new LoginCustomer(email.getText().toString(),password.getText().toString(),false);
		new Thread(new LoginRunnable(loginCustomer,loginInterface)).start();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_login, container, false);
		return rootView;
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
		   email=(EditText)view.findViewById(R.id.emailLogin);
			password=(EditText)view.findViewById(R.id.passwordLogin);

		   Button login=(Button)view.findViewById(R.id.login);

		   login.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View view) {
				   Login();
			   }
		   });

			ctx=view.getContext().getApplicationContext();
	        super.onViewCreated(view, savedInstanceState);
	    }
}
