package com.miamor.Fragments;

import com.miamor.R;
import com.miamor.Obj.*;
import com.miamor.Preferences.Authentication;
import com.miamor.Runnables.RegisterRunnable;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlaceholderRegisterFragment extends Fragment{
	EditText email;
	EditText password;
	EditText firstname;
	EditText lastname;
	Context ctx;
	
	public PlaceholderRegisterFragment() {}

	RegisterRunnable.RegisterInterface registerInterface = new RegisterRunnable.RegisterInterface(){
		@Override
		public void RegisterCompleted(final AuthenticationRequest response) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					if (response.getFirstname() == null) {
						Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getText(R.string.userexists),
						Toast.LENGTH_SHORT).show();
					} else {
						Authentication auth = new Authentication(ctx);
						auth.save(response);

						PlaceholderProfileFragment f2 = new PlaceholderProfileFragment();
						Globals.changePagerFragment(getActivity(), f2, getFragmentManager(), "profile");
					}
				}
			});
		}
	};
	
	public void Register(){
		if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".")){
			Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getText(R.string.invalidemail),
					Toast.LENGTH_SHORT).show();
		}else if(password.getText().toString().isEmpty() || firstname.getText().toString().isEmpty()
				|| lastname.getText().toString().isEmpty()){

			Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getText(R.string.invaliddata),
					Toast.LENGTH_SHORT).show();
		}else {
			RegisterCustomer registerCustomer = new RegisterCustomer(
					0, firstname.getText().toString(),
					lastname.getText().toString(),
					email.getText().toString(),
					password.getText().toString());

			new Thread(new RegisterRunnable(registerCustomer, registerInterface)).start();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		    setHasOptionsMenu(false);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_register, container, false);
		return rootView;
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
		 	firstname=(EditText)view.findViewById(R.id.firstnameRegister);
		 	lastname=(EditText)view.findViewById(R.id.lastnameRegister);
		   	email=(EditText)view.findViewById(R.id.emailRegister);
			password=(EditText)view.findViewById(R.id.passwordRegister);
			ctx=view.getContext().getApplicationContext();

		   Button login=(Button)view.findViewById(R.id.register);

		   login.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View view) {
				   Register();
			   }
		   });

		   super.onViewCreated(view, savedInstanceState);
	    }
}
