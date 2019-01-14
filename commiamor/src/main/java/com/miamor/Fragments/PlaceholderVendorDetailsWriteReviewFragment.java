package com.miamor.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import com.miamor.Obj.bundleParams;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.miamor.Runnables.VendorWriteReviewRunnable;

public class PlaceholderVendorDetailsWriteReviewFragment extends Fragment{
	EditText desc;
	RatingBar rating;
	Context ctx;
	int vendorId=0;
	bundleParams params;

	public PlaceholderVendorDetailsWriteReviewFragment() {}

	VendorWriteReviewRunnable.VendorWriteReviewInterface writeReviewInterface = new VendorWriteReviewRunnable.VendorWriteReviewInterface(){

		@Override
		public void SubmitCompleted() {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getActivity(), "The review has been send", Toast.LENGTH_SHORT).show();
				  }
				});
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	public void submitReview(){
		Authentication auth=new Authentication(ctx);
		if(auth.isLogged()){
			String custId=auth.read().getCustId();
			String token=auth.read().getToke();

			new Thread(new VendorWriteReviewRunnable(token,custId,params.getVendorId(), desc.getText().toString(), rating.getRating(),writeReviewInterface)).start();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_writereview, container, false);

		Bundle args = getArguments();
		params=(bundleParams)args.getSerializable("values");

		return rootView;
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
		   desc=(EditText)view.findViewById(R.id.writeReviewText);
		   rating=(RatingBar)view.findViewById(R.id.ratingBarWriteReview);

		   Button submit=(Button)view.findViewById(R.id.submitReview);

		   submit.setOnClickListener(new View.OnClickListener() {
			   @Override
			   public void onClick(View view) {
				   submitReview();
			   }
		   });

			ctx=view.getContext().getApplicationContext();
	        super.onViewCreated(view, savedInstanceState);
	    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id==android.R.id.home){

		}

		return super.onOptionsItemSelected(item);
	}

}
