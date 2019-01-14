package com.miamor.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miamor.ImageSliderActivity;
import com.miamor.ListAdapter.MyImageListAdapter;
import com.miamor.Obj.Blog;
import com.miamor.Obj.BlogPicture;
import com.miamor.Obj.BlogPostPicture;
import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.miamor.R;
import com.miamor.Runnables.BlogDetailsRunnable;
import com.miamor.Runnables.LoadImageRunnable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderBlogDetailsFragment extends Fragment{

	TextView name;
	TextView desc;
	ImageView img;
	ImageView headerImage;

	bundleParams params;
	//gallery object
	private Gallery picGallery;

	BlogDetailsRunnable.BlogInterface blogInterface = new BlogDetailsRunnable.BlogInterface() {
		@Override
		public void onStart() {

		}

		@Override
		public void Completed(final Blog response) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
				if(response!=null){
					loadDetails(response);
				}
				}
			});
		}
	};

	LoadImageRunnable.ImageInteface imageInterface=new LoadImageRunnable.ImageInteface(){
		@Override
		public void loadComplete(final Bitmap bmp, final ImageView v) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					v.setImageBitmap(bmp);
				}
			});
		}
	};

	public PlaceholderBlogDetailsFragment() {}

	public void loadDetails(Blog blog){

		name.setText(blog.getTitle());
		desc.setText(Html.fromHtml(blog.getBody()));

		if(blog.getBlogPostPictures().iterator().hasNext()) {
			Picasso.with(getActivity()).load(blog.getBlogPostPictures().iterator().next().getMediumBaseUrl()).into(headerImage);
		}

		fillGalleryData(blog);
	}

	private void fillGalleryData(Blog response){
		// Each row in the list stores country name, currency and flag
		final ArrayList<String> aList = new ArrayList<String>();
		//final List<String> aListBig = new ArrayList<String>();

		for(BlogPicture pic:response.getBlogPostPictures()){
			aList.add(pic.getMediumBaseUrl());
		}

		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		//SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item_image, from, to);
		MyImageListAdapter adapter = new MyImageListAdapter(getActivity(),getActivity().getBaseContext(),R.layout.list_item_image, aList);
		picGallery.setAdapter(adapter);
		//setListAdapter(adapter);

		//set the click listener for each item in the thumbnail gallery
		picGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			//handle clicks
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//set the larger image view to display the chosen bitmap calling method of adapter class
				ImageSliderActivity slider=new ImageSliderActivity();
				Intent intent = new Intent(getActivity(), slider.getClass());
				intent.putExtra("photos", aList);

				getActivity().startActivity(intent);

				//showBigPicture(aList.get(position));
			}
		});

		adapter.notifyDataSetChanged();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_blogdetails, container, false);

		name=(TextView)rootView.findViewById(R.id.productname);
		desc=(TextView)rootView.findViewById(R.id.productdesc);
		img=(ImageView)rootView.findViewById(R.id.imageViewProduct);
		headerImage=(ImageView)rootView.findViewById(R.id.imageViewDefaultProduct);

		//get the gallery view
		picGallery = (Gallery) rootView.findViewById(R.id.gallery);

		Bundle args = getArguments();
		params=(bundleParams)args.getSerializable("values");

		startSync();
		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		//inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id==android.R.id.home){

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
		new Thread(new BlogDetailsRunnable(params.getBlogId(), blogInterface)).start();
	}

	private void showPicture(String url){
		new Thread(new LoadImageRunnable(imageInterface,url,img)).start();
	}

	private void showBigPicture(String url){
		Globals.showBigPicture(url,getActivity());
	}
}
