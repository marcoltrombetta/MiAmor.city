package com.miamor.Obj;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.miamor.Fragments.CustomPagerAdapter;
import com.miamor.Fragments.PlaceholderBlogDetailsFragment;
import com.miamor.Fragments.PlaceholderCategoryVendorFragment;
import com.miamor.Fragments.PlaceholderCouponDetailsFragment;
import com.miamor.Fragments.PlaceholderCouponFragment;
import com.miamor.Fragments.PlaceholderCustomerBookmarkFragment;
import com.miamor.Fragments.PlaceholderCustomerCouponFragment;
import com.miamor.Fragments.PlaceholderCustomerMessageFragment;
import com.miamor.Fragments.PlaceholderCustomerReviewFragment;
import com.miamor.Fragments.PlaceholderCustomerSettingFragment;
import com.miamor.Fragments.PlaceholderImagesFragment;
import com.miamor.Fragments.PlaceholderMainFrameFragment;
import com.miamor.Fragments.PlaceholderMapDirectionsFragment;
import com.miamor.Fragments.PlaceholderProductDetailsFragment;
import com.miamor.Fragments.PlaceholderProductFragment;
import com.miamor.Fragments.PlaceholderProfileFragment;
import com.miamor.Fragments.PlaceholderSearchFragment;
import com.miamor.Fragments.PlaceholderVendorDetailsFragment;
import com.miamor.Fragments.PlaceholderVendorDetailsMapFragment;
import com.miamor.Fragments.PlaceholderVendorDetailsWriteReviewFragment;
import com.miamor.Fragments.PlaceholderVendorReviewDetailsFragment;
import com.miamor.Fragments.PlaceholderVendorReviewsFragment;
import com.miamor.Location.CustomLocationListener;
import com.miamor.Preferences.Authentication;
import com.miamor.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class Globals {
	public static String AppSecret="MiAmor";
	public static String ServerUrl="";

	public static double getDistance(double lat1, double lng1, double lat2, double lng2){
		Location loc1 = new Location("");
		loc1.setLatitude(lat1);
		loc1.setLongitude(lng1);

		Location loc2 = new Location("");
		loc2.setLatitude(lat2);
		loc2.setLongitude(lng2);

		DecimalFormat df = new DecimalFormat("#");

		double distanceInMeters = Double.valueOf(df.format(loc1.distanceTo(loc2)));

		return distanceInMeters;
	}

	public static Location getLocation(FragmentActivity activity){
		Location location=null;

		try{
			LocationManager locationManager = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);

			List<String> providers = locationManager.getProviders(true);

			Criteria criteria = new Criteria();
			String best = locationManager.getBestProvider(criteria, true);
			location = locationManager.getLastKnownLocation(best);

			if (location == null) {
				for (int i=providers.size()-1; i>=0; i--) {
					if(location!=null){break;}
					location = locationManager.getLastKnownLocation(providers.get(i));
				}
			}

		}catch(Exception ex){
			int i;
			i=0;
		}

		return location;
	}

	public static void changePagerFragment(FragmentActivity activity, Fragment fragment, FragmentManager fg, String tag){
		ViewPager pager = (ViewPager) activity.findViewById(R.id.pager);
		pager.setCurrentItem(5, false);

		activity.getSupportFragmentManager().executePendingTransactions();
		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
		transaction.addToBackStack(tag);

		Fragment previousFragment = getActiveFragment(activity);

		transaction.add(R.id.FragmentMainFrame, fragment, tag); //replace
		transaction.commit();

		if(previousFragment!=null) {
			transaction.hide(previousFragment);
		}
	}

	public static void changePagerFragmentHide(FragmentActivity activity, FragmentManager fg,String tagtoshow,String tagtohide){

		activity.getSupportFragmentManager().executePendingTransactions();
		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

		Fragment firstFragment = activity.getSupportFragmentManager().findFragmentByTag(tagtoshow);
		Fragment secondFragment = activity.getSupportFragmentManager().findFragmentByTag(tagtohide);

		if(firstFragment==null){
			//ViewPager pager = (ViewPager) activity.findViewById(R.id.pager);
			//pager.setCurrentItem(0,false);
			//transaction.addToBackStack(tagtoshow);
			//transaction.add(R.id.FragmentMainFrame, new PlaceholderCategoryVendorFragment(), tagtoshow); //replace
			//transaction.commit();
		}else{
			transaction.addToBackStack(tagtoshow);
			transaction.hide(secondFragment);
			transaction.show(firstFragment);
			transaction.commit();
		}
	}

	public static void hideCurrentFragment(FragmentActivity activity){
		Fragment currentFragment = getActiveFragment(activity);

		activity.getSupportFragmentManager().executePendingTransactions();
		FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

		transaction.hide(currentFragment);
		transaction.commit();
	}

	private static Fragment getActiveFragment(FragmentActivity activity) {
		if (activity.getSupportFragmentManager().getBackStackEntryCount() == 0) {
			return null;
		}
		String tag = activity.getSupportFragmentManager().getBackStackEntryAt(activity.getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
		return (Fragment) activity.getSupportFragmentManager().findFragmentByTag(tag);
	}

	public static void homeButtonHandler(FragmentActivity activity){
		activity.getSupportFragmentManager().executePendingTransactions();
		ViewPager pager = (ViewPager) activity.findViewById(R.id.pager);
		int i=pager.getCurrentItem();

		Fragment currentFragment = getActiveFragment(activity);

		if(i==4){
			if(currentFragment instanceof PlaceholderSearchFragment){
				pager.setCurrentItem(0,false);
			}else if(currentFragment instanceof PlaceholderVendorDetailsFragment) {
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "search", "vendordetails");
			}else if(currentFragment instanceof PlaceholderVendorDetailsMapFragment){
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "vendordetails", "vendordetailsmap");
			}else if(currentFragment instanceof PlaceholderCouponFragment){
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "vendordetails", "vendorcoupons");
			}else if(currentFragment instanceof PlaceholderCustomerCouponFragment){
				pager.setCurrentItem(3,false);
			}else if(currentFragment instanceof PlaceholderCustomerReviewFragment){
				pager.setCurrentItem(3,false);
			}else if(currentFragment instanceof PlaceholderCustomerSettingFragment){
				pager.setCurrentItem(3,false);
			}else if(currentFragment instanceof PlaceholderCustomerBookmarkFragment){
				pager.setCurrentItem(3,false);
			}else if(currentFragment instanceof PlaceholderImagesFragment) {
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "vendordetails", "vendorimages");
			}else if(currentFragment instanceof PlaceholderVendorReviewDetailsFragment){
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "vendordetails", "vendorreviewsdetails");
			}else if(currentFragment instanceof PlaceholderProductDetailsFragment){
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "vendorproducts", "vendorproductdetails");
			}else if(currentFragment instanceof PlaceholderProductFragment){
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "vendordetails", "vendorproducts");
			}else if(currentFragment instanceof PlaceholderVendorDetailsWriteReviewFragment){
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "vendordetails", "vendorwritereviews");
			}else if(currentFragment instanceof PlaceholderVendorReviewsFragment){
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "vendordetails", "vendorreviews");
			}else if(currentFragment instanceof PlaceholderMapDirectionsFragment){
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "vendordetails", "mapdirections");
			}else if(currentFragment instanceof PlaceholderCouponDetailsFragment){
				changePagerFragmentHide(activity, activity.getSupportFragmentManager(), "vendorcoupons", "vendorcoupondetails");
			}else if(currentFragment instanceof PlaceholderCustomerMessageFragment) {
				/*PlaceholderProfileFragment f2=new PlaceholderProfileFragment();
				Bundle b=new Bundle();
				b.putSerializable("values", new bundleParams(0,0,0,""));

				f2.setArguments(b);
				changePagerFragment(activity, f2, activity.getSupportFragmentManager(), "profile");*/
				pager.setCurrentItem(3,false);
			}else if(currentFragment instanceof PlaceholderBlogDetailsFragment){
				pager.setCurrentItem(2,false);
			}
		}
	}

	public static void showBigPicture(String url, FragmentActivity activity){
		Dialog builder = new Dialog(activity);
		builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
		builder.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialogInterface) {
				//nothing;
			}
		});

		ImageView imageView = new ImageView(activity);
		Picasso.with(activity).load(url).centerCrop().resize(1024, 768).into(imageView);
		builder.addContentView(imageView, new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		builder.show();

	}
}

